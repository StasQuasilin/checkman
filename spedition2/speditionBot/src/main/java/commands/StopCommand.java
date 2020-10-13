package commands;

import entity.Room;
import entity.RoomMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import hibernate.dao.DaoService;
import hibernate.dao.MemberDao;
import hibernate.dao.RoomDao;

public class StopCommand extends Command {

    private final RoomDao roomDao = DaoService.getRoomDao();
    private final MemberDao memberDao = DaoService.getMemberDao();

    public StopCommand() {
        super(Commands.STOP, "Призупинити роботу");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        final Room room = roomDao.getRoom();
        if (room != null) {
            if (room.isOwner(user)) {
                room.setActive(false);
                roomDao.saveRoom(room);
                SendMessage message = new SendMessage();
                message.setText("Роботу конференції призупинено");
                for (RoomMember member : memberDao.getMembers(room)) {
                    message.setChatId(member.getChatId());
                    execute(absSender, message, user);
                }
            } else {
                final RoomMember member = memberDao.getMember(user.getId());
                member.setRoom(null);
                memberDao.saveMember(member);
                SendMessage message = new SendMessage();
                message.setText("Користувач " + member.getName() + " покинува конференцію");
                message.setChatId(room.getOwner().getChatId());
                execute(absSender, message, user);
            }
        } else {
            sendMessage(absSender, chat, user, "OK");
        }
    }
}
