package entity.border;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "board_items")
public class BoardItem extends JsonAble {
    private int id;
    private String title;
    private String text;
    private ActionTime created;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "text")
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @OneToOne
    @JoinColumn(name = "created")
    public ActionTime getCreated() {
        return created;
    }
    public void setCreated(ActionTime created) {
        this.created = created;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(TITLE, title);
        object.put(TEXT, text);
        object.put(CREATED, created.toShortJson());

        return object;
    }
}
