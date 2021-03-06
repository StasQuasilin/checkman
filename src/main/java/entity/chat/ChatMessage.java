package entity.chat;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 26.07.2019.
 */
@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    private int id;
    private Timestamp timestamp;
    private Chat chat;
    private ChatMember sender;
    private String message;
    private Timestamp delivered;
    private Timestamp read;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @ManyToOne
    @JoinColumn(name = "chat")
    public Chat getChat() {
        return chat;
    }
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @OneToOne
    @JoinColumn(name = "sender")
    public ChatMember getSender() {
        return sender;
    }
    public void setSender(ChatMember sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "delivered")
    public Timestamp getDelivered() {
        return delivered;
    }
    public void setDelivered(Timestamp delivered) {
        this.delivered = delivered;
    }

    @Basic
    @Column(name = "read")
    public Timestamp getRead() {
        return read;
    }

    public void setRead(Timestamp read) {
        this.read = read;
    }
}
