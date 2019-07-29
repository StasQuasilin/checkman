package entity.chat;

import entity.Worker;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 26.07.2019.
 */
@Entity
@Table(name = "chat_members")
public class ChatMember {
    private int id;
    private Chat chat;
    private Worker member;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    @JoinColumn(name = "member")
    public Worker getMember() {
        return member;
    }
    public void setMember(Worker member) {
        this.member = member;
    }
}
