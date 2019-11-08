package entity.transport;

import entity.deal.ContractProduct;
import entity.weight.Weight;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "_transportation_products")
public class TransportationProduct {
    private int id;
    private Transportation transportation;
    private ContractProduct contractProduct;
    private float plan;
    private Weight weight;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @ManyToOne
    @JoinColumn(name = "product")
    public ContractProduct getContractProduct() {
        return contractProduct;
    }
    public void setContractProduct(ContractProduct contractProduct) {
        this.contractProduct = contractProduct;
    }

    @Basic
    @Column(name = "plan")
    public float getPlan() {
        return plan;
    }
    public void setPlan(float plan) {
        this.plan = plan;
    }

    @OneToOne
    @JoinColumn(name = "weight")
    public Weight getWeight() {
        return weight;
    }
    public void setWeight(Weight weight) {
        this.weight = weight;
    }
}
