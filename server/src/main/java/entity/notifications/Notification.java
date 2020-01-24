package entity.notifications;

import entity.JsonAble;
import entity.Worker;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 24.01.2020.
 */

@Entity
@Table(name = "_notifications")
public class Notification extends JsonAble{
    private int id;
    private Timestamp time;
    private Worker sender;
    private String text;
    private boolean waitAnswer;

    public Notification() {
        time = Timestamp.valueOf(LocalDateTime.now());
    }

    public Notification(String text) {
        this.text = text;
    }

    public Notification(String text, boolean waitAnswer){
        this(text);
        this.waitAnswer = waitAnswer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @OneToOne
    @JoinColumn(name = "sender")
    public Worker getSender() {
        return sender;
    }
    public void setSender(Worker sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        if (time != null) {
            object.put(TIME, time.toString());
        }
        if (sender != null){
            object.put(SENDER, sender.toJson());
        }
        object.put(TEXT, text);
        object.put(WAIT_ANSWER, waitAnswer);
        return object;
    }
}
