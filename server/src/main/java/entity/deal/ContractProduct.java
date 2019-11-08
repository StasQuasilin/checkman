package entity.deal;

import entity.DealType;
import entity.documents.Shipper;
import entity.products.Product;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "_contract_products")
public class ContractProduct {
    private int id;
    private Contract contract;
    private DealType type;
    private Product product;
    private Shipper shipper;
    private float done;
    private float amount;
    private Set<ContractProductNote> notes = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "contract")
    public Contract getContract() {
        return contract;
    }
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToOne
    @JoinColumn(name = "shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @Basic
    @Column(name = "done")
    public float getDone() {
        return done;
    }
    public void setDone(float done) {
        this.done = done;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
    public Set<ContractProductNote> getNotes() {
        return notes;
    }
    public void setNotes(Set<ContractProductNote> notes) {
        this.notes = notes;
    }
}
