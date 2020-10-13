import commands.*;
import entity.Room;
import entity.RoomMember;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import hibernate.dao.DaoService;
import hibernate.dao.MemberDao;
import hibernate.dao.RoomDao;

import java.util.List;

public class BotLPC extends TelegramLongPollingCommandBot {

    private final Logger logger = Logger.getLogger(BotLPC.class);

    private static final String TOKEN = "1193523224:AAH-j74c6pSPkCoUJ7XOhlN1Hij7zeeggPg";
    private static final String BOT_NAME = "";

    private final CommandRegistry commandRegistry;

    public BotLPC(DefaultBotOptions options, boolean a) {
        super(options);
        commandRegistry = new CommandRegistry(a, BOT_NAME);

        logger.info("Start bot");
        final HelpCommand helpCommand = new HelpCommand(commandRegistry);

        registerCommand(new StartCommand(helpCommand));
        registerCommand(new StopCommand());
        registerCommand(new ListCommand());
        registerCommand(new MyNameCommand());
        registerCommand(new SetNameCommand());
        registerCommand(new RemoveRoomCommand());
        registerCommand(new StatusCommand());


        registerCommand(helpCommand);
        registerDefaultAction(((absSender, message) -> {
            SendMessage text = new SendMessage();
            text.setChatId(message.getChatId());
            text.setText(message.getText() + " command not found!");

            try {
                absSender.execute(text);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        }));
    }

    private void registerCommand(Command command) {
        logger.info("Register command /" + command.getCommandIdentifier());
        commandRegistry.register(command);

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates){
            updateReceived(update);
        }
    }

    private void updateReceived(Update update) {
        final Message message = update.getMessage();
        if (message != null) {
            handleMessage(message, update);
        } else {
            final Message editedMessage = update.getEditedMessage();
            handleMessage(editedMessage, update);
        }
    }

    private void handleMessage(Message message, Update update) {
        logger.info(message.getFrom().getUserName() + " send " + message.getText());
        if (message.isCommand()) {
            if (!commandRegistry.executeCommand(this, message)) {
                sendMessage(message);
            }
        } else {
            processNonCommandUpdate(update);
        }
    }

    private void sendMessage(Message updateMessage) {
        final User user = updateMessage.getFrom();

        final RoomMember member = memberDao.getMember(user.getId());

        SendMessage message = new SendMessage();
        String name = member.getName();
        if (member.getName().isEmpty()){
            name = user.getUserName();
        }
        if (name.isEmpty()){
            name = user.getId().toString();
        }
        message.setText("<b>" + name + ":</b>\n" + updateMessage.getText());
        message.enableHtml(true);
        final Room room = roomDao.getRoom();
        if (room.isOwner(user)){
            if (!room.isActive()){
                room.setActive(true);
                roomDao.saveRoom(room);
            }
            for (RoomMember member1 : memberDao.getMembers(room)){
                message.setChatId(member1.getChatId());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (room.isActive()) {
                message.setChatId(room.getOwner().getChatId());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendMessage m = new SendMessage();
                m.setChatId(member.getChatId());
                m.setText("Робота конференції призупинена");

                try {
                    execute(m);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBotUsername() {
        return BOT_NAME;
    }

    private final MemberDao memberDao = DaoService.getMemberDao();
    private final RoomDao roomDao = DaoService.getRoomDao();

    @Override
    public void processNonCommandUpdate(Update update) {
        sendMessage(update.getMessage());
    }

    public String getBotToken() {
        return TOKEN;
    }
}
