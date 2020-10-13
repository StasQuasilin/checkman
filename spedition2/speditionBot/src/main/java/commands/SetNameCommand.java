package commands;

import entity.RoomMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import hibernate.dao.DaoService;
import hibernate.dao.MemberDao;

public class SetNameCommand extends Command {

    private final MemberDao memberDao = DaoService.getMemberDao();

    public SetNameCommand() {
        super(Commands.SET_NAME, "Встановити ім’я");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (strings.length > 0) {
            final RoomMember member = memberDao.getMember(user.getId());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < strings.length; i++) {
                builder.append(strings[i]);
                if (i < strings.length - 1) {
                    builder.append(" ");
                }
            }
            final String name = builder.toString();
            final String oldName = member.getName();
            member.setName(name);
            memberDao.saveMember(member);
            sendMessage(absSender, chat, user, "Ваше ім’я тепер " + name);

            if (!oldName.isEmpty()){
                SendMessage message = new SendMessage();
                message.setChatId(member.getRoom().getOwner().getChatId());
                message.setText(oldName + " змінив ім’я на " + name);
                execute(absSender, message, user);
            }

        } else {
            sendMessage(absSender, chat, user, "Необхідно ввести ім’я командою /set_name [прізвище] [ім’я]");
        }
    }
}
