package entity.rails;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "trains")
public class Train {
    private int id;
    private Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
