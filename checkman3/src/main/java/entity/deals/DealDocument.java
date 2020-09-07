package entity.deals;

import entity.references.Address;

import javax.persistence.*;

@Entity
@Table(name = "deal_documents")
public class DealDocument {
    private int id;
    private Deal deal;
    private Address address;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "_deal")
    public Deal getDeal() {
        return deal;
    }
    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @Transient
//    @OneToOne
//    @JoinColumn(name = "_address")
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
}
