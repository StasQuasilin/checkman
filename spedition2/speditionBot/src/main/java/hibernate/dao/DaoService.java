package hibernate.dao;

public class DaoService {
    private static final MemberDao memberDao = new MemberDao();
    private static final RoomDao roomDao = new RoomDao();

    public static MemberDao getMemberDao() {
        return memberDao;
    }

    public static RoomDao getRoomDao() {
        return roomDao;
    }
}
