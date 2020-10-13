package commands;

import entity.Room;
import entity.RoomMember;
import hibernate.dao.DaoService;
import hibernate.dao.MemberDao;
import hibernate.dao.RoomDao;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

public class RemoveRoomCommand extends Command{

    private final RoomDao roomDao = DaoService.getRoomDao();
    private final MemberDao memberDao = DaoService.getMemberDao();

    public RemoveRoomCommand() {
        super(Commands.REMOVE_ROOM, "Видалити теперішню конференцію");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        final Room room = roomDao.getRoom();
        if (room.isOwner(user)){
            roomDao.remove(room);
            sendMessage(absSender, chat, user, "Конференція видалена");
            SendMessage memberMessage = new SendMessage();
            memberMessage.setText("Конференція видалена власником");
            final List<RoomMember> members = memberDao.getMembers(room);
            for (RoomMember member : members){
                memberMessage.setChatId(member.getChatId());
                execute(absSender, memberMessage, user);
            }

        } else {
            sendMessage(absSender, chat, user, "Ви не можете видаляти конференцію, в якої не являєтесь власником");
        }
    }
}
