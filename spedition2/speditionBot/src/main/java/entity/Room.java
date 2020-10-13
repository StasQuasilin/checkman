package entity;

import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    private int id;
    private RoomMember owner;
    private boolean active;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
    public RoomMember getOwner() {
        return owner;
    }
    public void setOwner(RoomMember owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "_active")
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isOwner(User user) {
        return user.getId() == owner.getTelegramId();
    }
}
