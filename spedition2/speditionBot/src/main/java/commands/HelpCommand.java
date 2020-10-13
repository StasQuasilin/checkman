package commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends Command {

    private final ICommandRegistry registry;

    public HelpCommand(ICommandRegistry registry) {
        super(Commands.HELP, "Список доступних команд");
        this.registry = registry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder helpMessageBuilder = new StringBuilder("<b>Список доступних команд:</b>\n\n");
        registry.getRegisteredCommands().forEach(cmd -> helpMessageBuilder.append(cmd.toString()).append("\n"));

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        execute(absSender, helpMessage, user);
    }
}
