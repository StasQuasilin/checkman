package commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StatusCommand extends Command {
    public StatusCommand() {
        super(Commands.STATUS, "Статус роботи програми");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendMessage(absSender, chat, user, "\uD83D\uDC4D");
    }
}
