package entity;

import javax.persistence.*;

@Entity
@Table(name = "room_members")
public class RoomMember {
    private int id;
    private long telegramId;
    private long chatId;
    private String name;
    private Room room;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_telegram_id")
    public long getTelegramId() {
        return telegramId;
    }
    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    @Basic
    @Column(name = "_chat_id")
    public long getChatId() {
        return chatId;
    }
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Basic
    @Column(name = "_name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToOne
    @JoinColumn(name = "_room")
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
}
