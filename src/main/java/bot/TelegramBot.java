package bot;

import entity.Admin;
import entity.Worker;
import entity.bot.Command;
import entity.bot.UserNotificationSetting;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.LanguageBase;
import utils.U;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.notifications.INotifier;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class TelegramBot extends ILongPollingBot implements INotifier {

    private static final String OLD_TOKEN = "bot.old.token";
    private static final String NO_TOKEN = "bot.no.token";
    private static final String WELCOME = "bot.welcome";
    private static final String LOG_IN = "bot.log.in";
    private static final String HELP = "bot.help";
    private static final String STATUS = "bot.status";
    private static final String ON = "on";
    private static final String OFF = "off";
    private static final String DOESNT_NEED = "bot.token.doesnt.need";

    private final Logger log = Logger.getLogger(TelegramBot.class);
    private final LanguageBase lb = LanguageBase.getBase();
    private final BotUIDs botUIDs = BotUIDs.getBox();
    private final BotSettings botSettings = BotSettings.getInstance();

    private final String token;
    private final String name;

    dbDAO dao = dbDAOService.getDAO();

    public TelegramBot(String token, String name) {
        this.token = token;
        this.name = new String(name.getBytes(), StandardCharsets.UTF_8);
    }

    void updateProcessing(Update update){
        long id = update.getMessage().getChatId();

        if (update.getMessage().getText() == null){
            Chat chat = update.getMessage().getChat();
            UserNotificationSetting setting = dao.getUserBotSettingsByChat(id);

            if (setting != null) {
                boolean save = false;
                String title = chat.getTitle();
                if (!U.exist(setting.getTitle()) || !setting.getTitle().equals(title)){
                    setting.setTitle(title);
                    save = true;
                }
                if (save){
                    dao.save(setting);
                }
            }
        }
        if (update.hasCallbackQuery()){
            System.out.println(update.getCallbackQuery().getData());
        } else if (update.hasMessage()){
            if (update.getMessage().isCommand()){
                parseCommand(id, update.getMessage().getText(), update.getMessage().getChat().getTitle());
            }
        }
    }

    final Pattern commandPattern = Pattern.compile("^/\\w{2,}");
    final String unknownCommandFormat = "_Command '%s' not found_";
    private void parseCommand(long id, String text, String title) {

        Matcher matcher = commandPattern.matcher(text);
        if (matcher.find()) {
            String group = matcher.group();
            try {
                Command command = Command.valueOf(group.substring(1));
                text = text.replace(group, "").trim();

                switch (command) {
                    case help:
                        showHelp(id);
                        break;
                    case token:
                        signIn(id, text, title);
                        break;
                    case status:
                        status(id);
                        break;
                    case start:
                        start(id);
                        break;
                    case stop:
                        stop(id);
                        break;
                }
            } catch (IllegalArgumentException e){
                sendMessage(id, String.format(unknownCommandFormat, group));
            }
        }

    }

    private void status(long id) {
        if(signed(id)) {
            UserNotificationSetting settings = botSettings.getSettings(id);
            sendMessage(id, String.format(
                    lb.get(STATUS),
                    lb.get(settings.getTransport().toString()),
                    lb.get(settings.getWeight().toString()),
                    lb.get(settings.getAnalyses().toString()),
                    lb.get(settings.isExtraction() ? ON : OFF),
                    lb.get(settings.isVro() ? ON : OFF),
                    lb.get(settings.isKpo() ? ON : OFF),
                    lb.get(settings.isShow() ? ON : OFF)
            ));

        }
    }

    private boolean signed(long id){
        boolean contain = botSettings.contain(id);
        if (!contain){
            sendMessage(id, lb.get(LOG_IN));
        }
        return contain;

    }

    private void start(long id) {
        if(signed(id)) {
            UserNotificationSetting settings = botSettings.getSettings(id);
            settings.setShow(true);
            botSettings.addSettings(settings);
        }
    }

    private void stop(long id) {
        if(signed(id)) {
            UserNotificationSetting settings = botSettings.getSettings(id);
            settings.setShow(false);
            botSettings.addSettings(settings);
        }
    }

    private void signIn(long id, String text, String title) {
        if (botSettings.contain(id)){
            sendMessage(id, DOESNT_NEED);
        } else {
            BotUID uid = botUIDs.getUID(text);
            String answer;
            if (uid == null) {
                answer = lb.get(NO_TOKEN);
            } else if (uid.isOld()){
                answer = lb.get(OLD_TOKEN);
            } else {
                Worker worker = uid.getWorker();
                UserNotificationSetting settings = new UserNotificationSetting();

                settings.setTelegramId(id);
                settings.setWorker(worker);
                settings.setTitle(title);
                settings.setLanguage(LanguageBase.DEFAULT_LANGUAGE);
                answer = lb.get(WELCOME);
                botSettings.save(settings);
            }
            sendMessage(id, answer);
        }
    }


    private void showHelp(long id) {
        sendMessage(id, lb.get(HELP));

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::updateProcessing);
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateProcessing(update);
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }


    @Override
    public synchronized void sendMessage(long chatId, String s) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(s);
        send(message, null);
    }

    private void send(SendMessage message, ReplyKeyboard keyboard){
        if (keyboard != null){
            message.setReplyMarkup(keyboard);
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public BotSettings getBotSettings() {
        return botSettings;
    }

    public Admin getAdmin() {
        return botSettings.getAdmin();
    }

    public void remove(long chatId, int messageId) throws TelegramApiException {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        execute(deleteMessage);
    }


}
