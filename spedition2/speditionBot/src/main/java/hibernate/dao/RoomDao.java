package hibernate.dao;

import entity.Room;

public class RoomDao {

    private final hibernate.Hibernator hibernator = hibernate.Hibernator.getInstance();


    public Room getRoom() {
        return hibernator.get(Room.class, null);
    }

    public void saveRoom(Room room) {
        hibernator.save(room);
    }

    public void remove(Room room) {
        hibernator.remove(room);
    }
}
