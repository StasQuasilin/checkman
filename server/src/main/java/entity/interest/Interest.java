package entity.interest;

import entity.Worker;

import javax.persistence.*;

@Entity
@Table(name = "user_interest")
public class Interest {
    private int id;
    private Worker worker;
    private InterestType interestType;
    private int interestId; //-1=All, -2=interest action
    private boolean i, w, a, o;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_user")
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_interest_type")
    public InterestType getInterestType() {
        return interestType;
    }
    public void setInterestType(InterestType interestType) {
        this.interestType = interestType;
    }

    @Basic
    @Column(name = "_interest_id")
    public int getInterestId() {
        return interestId;
    }
    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    @Basic
    @Column(name = "_i")
    public boolean isI() {
        return i;
    }
    public void setI(boolean i) {
        this.i = i;
    }

    @Basic
    @Column(name = "_w")
    public boolean isW() {
        return w;
    }
    public void setW(boolean w) {
        this.w = w;
    }

    @Basic
    @Column(name = "_a")
    public boolean isA() {
        return a;
    }
    public void setA(boolean a) {
        this.a = a;
    }
    @Basic
    @Column(name = "_o")
    public boolean isO() {
        return o;
    }
    public void setO(boolean o) {
        this.o = o;
    }
}
