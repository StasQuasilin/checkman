package entity;

import entity.organisations.Counterparty;
import entity.transport.TransportCustomer;

import javax.persistence.*;

/**
 * Created by quasilin on 18.03.2019.
 */
@Entity
@Table(name = "app_settings")
public class ApplicationSettings {
    private int id;
    private Counterparty organisation;
    private TransportCustomer customer;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "organisation")
    public Counterparty getOrganisation() {
        return organisation;
    }
    public void setOrganisation(Counterparty organisation) {
        this.organisation = organisation;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "customer")
    public TransportCustomer getCustomer() {
        return customer;
    }
    public void setCustomer(TransportCustomer customer) {
        this.customer = customer;
    }
}
