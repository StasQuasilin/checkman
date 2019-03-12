package entity.documents;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@Entity
@Table(name = "uid")
public class DocumentUID {
    private String uid;

    public DocumentUID() {}

    public DocumentUID(String uid) {
        this.uid = uid;
    }

    @Id
    @Basic
    @Column(name = "id")
    public String getUid() {
        return uid;
    }

    public void setUid(String UID) {
        this.uid = UID;
    }
}
