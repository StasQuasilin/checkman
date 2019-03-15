package entity.documents;

import entity.DealType;
import entity.Product;
import entity.Worker;
import entity.organisations.Organisation;
import org.glassfish.jersey.server.BackgroundScheduler;
import utils.DocumentUIDGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Set;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "deals")
public class Deal extends IDocument{
//    private int id;
//    private Date date;

    private Date dateTo;
    private DealType type;
    private DocumentOrganisation documentOrganisation;
    private Organisation organisation;
    private Product product;
    private float quantity;
    private float price;
    private float done;
    private Worker creator;
    private String uid;
    private boolean archive;



    @Override
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "date_to")
    public Date getDateTo() {
        return dateTo;
    }
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = "visibility")
    public DocumentOrganisation getDocumentOrganisation() {
        return documentOrganisation;
    }

    public void setDocumentOrganisation(DocumentOrganisation documentOrganisation) {
        this.documentOrganisation = documentOrganisation;
    }

    @OneToOne
    @JoinColumn(name = "organisation")
    public Organisation getOrganisation() {
        return organisation;
    }
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "quantity")
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @Column(name = "done")
    public float getDone() {
        return done;
    }
    public void setDone(float done) {
        this.done = done;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * date.hashCode() + hash;
        hash = 31 * dateTo.hashCode() + hash;
        hash = 31 * organisation.getId() + hash;
        hash = 31 * product.getId() + hash;
        hash = 31 * Float.hashCode(quantity) + hash;
        hash = 31 * Float.hashCode(price) + hash;
        hash = 31 * Float.hashCode(done) + hash;
        hash = 31 * type.hashCode() + hash;
        hash = 31 * Boolean.hashCode(archive) + hash;

        return hash;
    }

    @Override
    public String toString() {
        return "Deal{\n" +
                "\tdateTo=" + dateTo + ",\n" +
                "\ttype=" + type + ",\n" +
                "\torganisation=" + organisation + ",\n" +
                "\tcreator=" + creator + ",\n" +
                "\tuid='" + uid + '\'' + ",\n" +
                '}';
    }
}
