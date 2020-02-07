package entity.bot;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 20.04.2019.
 */
@Entity
@Table(name = "bot_settings")
public class BotSettings {
    private int id;
    private String token;
    private String name;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
