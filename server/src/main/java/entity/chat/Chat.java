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
    private boolean isGroup = false;
    private Set<ChatMember> members = new HashSet<>();

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
    @Column(name = "is_group")
    public boolean getIsGroup() {
        return isGroup;
    }
    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chat", cascade = CascadeType.ALL)
    public Set<ChatMember> getMembers() {
        return members;
    }
    public void setMembers(Set<ChatMember> members) {
        this.members = members;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
