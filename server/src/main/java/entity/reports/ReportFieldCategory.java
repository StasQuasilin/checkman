package entity.reports;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@Entity
@Table(name = "report_field_category")
public class ReportFieldCategory implements Comparable<ReportFieldCategory>{
    private int id;
    private String title;
    private int number;

    @Id
    @GeneratedValue
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
    @Column(name = "number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(ReportFieldCategory o) {
        if (o != null){
            return o.number - number;
        }
        return -1;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.hashCode() != hashCode();
    }
}
