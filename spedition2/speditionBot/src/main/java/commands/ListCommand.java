package commands;

import entity.Room;
import entity.RoomMember;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import hibernate.dao.DaoService;
import hibernate.dao.MemberDao;
import hibernate.dao.RoomDao;

import java.util.List;

public class ListCommand extends Command {

    private final RoomDao roomDao = DaoService.getRoomDao();
    private final MemberDao memberDao = DaoService.getMemberDao();

    public ListCommand() {
        super(Commands.LIST, "Список учасників");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        StringBuilder builder = new StringBuilder();

        final Room room = roomDao.getRoom();
        if (room != null) {
            builder.append("Власник ").append(room.getOwner().getName()).append("\n");
            final List<RoomMember> members = memberDao.getMembers(room);
            if (members.size() > 0) {
                builder.append("Учасники: ").append(members.size()).append("\n");
                for (RoomMember member : members) {
                    builder.append("\t").append(member.getName()).append("\n");
                }
            }
            sendMessage(absSender, chat, user, builder.toString());
        } else {
            sendMessage(absSender, chat, user, "Конференція ще не створена");
        }
    }
}
