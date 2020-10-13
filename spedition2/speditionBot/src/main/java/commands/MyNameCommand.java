package commands;

import entity.RoomMember;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import hibernate.dao.DaoService;
import hibernate.dao.MemberDao;

public class MyNameCommand extends Command {

    private final MemberDao memberDao = DaoService.getMemberDao();

    public MyNameCommand() {
        super(Commands.MY_NAME, "Показати моє їм’я");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        final RoomMember member = memberDao.getMember(user.getId());
        if  (member != null) {
            sendMessage(absSender, chat, user, "Ім’я Вам " + member.getName());
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("ID: ").append(user.getId()).append("\n");
            builder.append("Ім’я користувача: ").append(user.getUserName()).append("\n");
            if (!user.getFirstName().isEmpty()){
                builder.append("Ім’я: ").append(user.getFirstName()).append("\n");
            }
            if (!user.getLastName().isEmpty()){
                builder.append("Прізвище: ").append(user.getLastName()).append("\n");
            }
            builder.append("\n");
            builder.append("P.S. Для початку роботи введіть команду /start");

            sendMessage(absSender, chat, user, builder.toString());
        }
    }
}
