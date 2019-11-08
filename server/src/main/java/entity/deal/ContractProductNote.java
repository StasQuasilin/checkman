package entity.deal;

import entity.transport.ActionTime;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "_contract_product_notes")
public class ContractProductNote {
    private int id;
    private ContractProduct product;
    private String note;
    private ActionTime createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "product")
    public ContractProduct getProduct() {
        return product;
    }
    public void setProduct(ContractProduct product) {
        this.product = product;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }
}
