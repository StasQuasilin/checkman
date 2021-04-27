package entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "generated_files")
public class GeneratedFile {
    private int id;
    private String file;
    private Timestamp time;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_file")
    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }

    @Basic
    @Column(name = "_time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }
}
