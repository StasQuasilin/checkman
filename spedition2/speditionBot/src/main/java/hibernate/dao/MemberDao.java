package hibernate.dao;

import entity.Room;
import entity.RoomMember;

import java.util.List;

public class MemberDao {

    private final hibernate.Hibernator hibernator = hibernate.Hibernator.getInstance();

    public RoomMember getMember(long telegramId){
        return hibernator.get(RoomMember.class, "telegramId", telegramId);
    }

    public void saveMember(RoomMember member) {
        hibernator.save(member);
    }

    public List<RoomMember> getMembers(Room room) {
        return hibernator.query(RoomMember.class, "room", room);
    }
}
