package commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class Command extends BotCommand{


    public Command(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public void sendMessage(AbsSender absSender, Chat chat, User user, String text){

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        message.setText(text);
        execute(absSender, message, user);
    }

    public void execute(AbsSender absSender, SendMessage message, User user) {
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
