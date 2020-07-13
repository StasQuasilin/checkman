package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "users")
public class User extends JsonAble{
    private int id;
    private String uid;
    private String email;
    private String password;
    private Role role;
    private Worker worker;
    private Worker registrator;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    @OneToOne
    @JoinColumn(name = "worker")
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @OneToOne
    @JoinColumn(name = "registrator")
    public Worker getRegistrator() {
        return registrator;
    }
    public void setRegistrator(Worker registrator) {
        this.registrator = registrator;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = worker.toJson();
        jsonObject.put(UID, uid);
        return jsonObject;
    }
}
