package bot;

import entity.Worker;
import entity.bot.Command;
import entity.bot.UserBotSetting;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.LanguageBase;

import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class TelegramBot extends IBot {

    private static final String OLD_TOKEN = "bot.old.token";
    private static final String NO_TOKEN = "bot.no.token";
    private static final String WELCOME = "bot.welcome";
    private final Logger log = Logger.getLogger(TelegramBot.class);
    private final LanguageBase lb = LanguageBase.getBase();
    private final BotUIDs botUIDs = BotUIDs.getBox();
    private final BotSettings botSettings = BotSettings.getInstance();
    private final String token;
    private final String name;

    public TelegramBot(String token, String name) {
        this.token = token;
        this.name = new String(name.getBytes(), Charset.forName("UTF-8"));
        log.info("Bot " + name +" started successfully");
    }

    void updateProcessing(Update update){

        System.out.println(update.getUpdateId());
        System.out.println(update.getMessage().getText());
        long id = update.getMessage().getChatId();
        if (update.getMessage().isCommand()){
            parseCommand(id, update.getMessage().getText());
        }
    }
    final Pattern commandPattern = Pattern.compile("^\\/\\w{2,}");
    private void parseCommand(long id, String text) {
        Matcher matcher = commandPattern.matcher(text);
        if (matcher.find()){
            String group = matcher.group();
            Command command = Command.valueOf(group.substring(1));
            text = text.replace(group, "").trim();

            switch (command){
                case help:
                    showHelp(id);
                    break;
                case token:
                    signin(id, text);
                    break;
                case start:
                    start(id);
                    break;
                case stop:
                    stop(id);
                    break;
            }
        }
    }

    private void start(long id) {

    }

    private void stop(long id) {

    }

    private void signin(long id, String text) {
        BotUID uid = botUIDs.getUID(text);
        String answer;
        if (uid == null) {
            answer = lb.get(NO_TOKEN);
        } else if (uid.isOld()){
            answer = lb.get(OLD_TOKEN);
        } else {
            UserBotSetting settings = new UserBotSetting();
            settings.setTelegramId(id);
            Worker worker = uid.getWorker();
            settings.setWorker(worker);
            answer = String.format(lb.get(WELCOME), worker.getPerson().getAccost());
            botSettings.save(settings);
        }
        sendMsg(id, answer);
    }

    private void showHelp(long id) {


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
    public synchronized void sendMsg(long chatId, String msg){
        sendMsg(chatId, msg, null);
    }

    public synchronized void sendMsg(long chatId, String s, ReplyKeyboard keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        if (keyboard != null) {
            sendMessage.setReplyMarkup(keyboard);
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Can\'t send message cause " + e.getMessage());
        }
    }
}
