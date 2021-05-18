package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@Entity
@Table(name = "sessions")
public class UserInfo extends JsonAble{
    private int id;
    private Client client;
    private User user;
    private String ip;
    private String sessionId;
    
    public UserInfo(){};
    
    public UserInfo(Client client, User user, String ip, String sessionId) {
        this();
        this.client = client;
        this.user = user;
        this.ip = ip;
        this.sessionId = sessionId;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_client")
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    @OneToOne
    @JoinColumn(name = "_user")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "_ip")
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Transient
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public JSONObject toJson(int level) {
        final JSONObject json = user.toJson();
        json.put(IP, ip);
        json.put(CLIENT, client.toString());
        return json;
    }
}
