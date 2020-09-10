package entity.deals;

import constants.Keys;
import entity.ActionTime;
import entity.references.Organisation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static constants.Keys.*;

@Entity
@Table(name = "deals")
public class Deal extends JsonAble {
    private int id;
    private DealType type;
    private String number;
    private Date date;
    private Date from;
    private Date to;
    private ActionTime created;
    private boolean archive;
    private Set<DealDocument> documents = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_type")
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "_number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "_from")
    public Date getFrom() {
        return from;
    }
    public void setFrom(Date from) {
        this.from = from;
    }

    @Basic
    @Column(name = "_to")
    public Date getTo() {
        return to;
    }
    public void setTo(Date to) {
        this.to = to;
    }

    @OneToOne
    @JoinColumn(name = "_created")
    public ActionTime getCreated() {
        return created;
    }
    public void setCreated(ActionTime created) {
        this.created = created;
    }

    @Basic
    @Column(name = "_archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "deal", cascade = CascadeType.ALL)
    public Set<DealDocument> getDocuments() {
        return documents;
    }
    public void setDocuments(Set<DealDocument> documents) {
        this.documents = documents;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NUMBER, number);
        jsonObject.put(TYPE, type.toString());
        jsonObject.put(Keys.DATE, date.toString());
        jsonObject.put(Keys.FROM, from.toString());
        jsonObject.put(Keys.TO, to.toString());

        if (created != null) {
            jsonObject.put(Keys.CREATED, created.toJson());
        }
        jsonObject.put(Keys.ARCHIVE, archive);
        jsonObject.put(DOCUMENTS, documents());
        return jsonObject;
    }

    private JSONArray documents() {
        JSONArray array = new JSONArray();
        for(DealDocument dealDocument : documents){
            array.add(dealDocument.toJson());
        }
        return array;
    }
}
