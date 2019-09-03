package entity.chat;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by szpt_user045 on 26.07.2019.
 */
@Entity
@Table(name="chats")
public class Chat {
    private int id;
    private String title;
    private boolean groupChat = false;
    private Set<ChatMember> members = new HashSet<>();
    private ChatMessage lastMessage = null;
    private boolean archive = false;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "group_chat")
    public boolean getGroupChat() {
        return groupChat;
    }
    public void setGroupChat(boolean isGroup) {
        this.groupChat = isGroup;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chat", cascade = CascadeType.ALL)
    public Set<ChatMember> getMembers() {
        return members;
    }
    public void setMembers(Set<ChatMember> members) {
        this.members = members;
    }

    @Basic
    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Transient
    public ChatMessage getLastMessage() {
        return lastMessage;
    }
    public void setLastMessage(ChatMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
