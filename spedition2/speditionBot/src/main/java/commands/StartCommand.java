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

public class StartCommand extends Command {

    private MemberDao memberDao = DaoService.getMemberDao();
    private RoomDao roomDao = DaoService.getRoomDao();
    private final HelpCommand helpCommand;

    public StartCommand(HelpCommand helpCommand) {
        super(Commands.START, "Почати роботу");
        this.helpCommand = helpCommand;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        RoomMember member = memberDao.getMember(user.getId());
        if (member == null){
            member = new RoomMember();
            member.setTelegramId(user.getId());
            member.setChatId(chat.getId());
            int length = 0;
            length += user.getLastName().length();
            length += user.getFirstName().length();
            if (length > 0){
                final String name = user.getLastName() + " " + user.getFirstName();
                member.setName(name);
            } else {
                sendMessage(absSender, chat, user, "Необхідно вказати ім’я в командою /set_name [прізвище] [ім’я]");
            }

            memberDao.saveMember(member);
            sendMessage(absSender, chat, user, "New user create");
        }

        Room room = roomDao.getRoom();
        if (room == null){
            room = new Room();
            room.setOwner(member);
            sendMessage(absSender, chat, user, "New room create");
        } else if (member.getRoom() == null){
            member.setRoom(room);
            memberDao.saveMember(member);
            SendMessage message = new SendMessage();
            message.setChatId(room.getOwner().getChatId());
            message.setText(member.getName() + " приєднався до конференції");
            execute(absSender, message, user);
        }

        room.setActive(true);
        roomDao.saveRoom(room);
        helpCommand.execute(absSender, user, chat, strings);
    }
}
