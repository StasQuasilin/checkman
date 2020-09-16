package utils.db.dao.transportations;

import entity.transportations.Transportation;
import entity.transportations.TransportationDocument;
import entity.transportations.TransportationProduct;

import java.util.List;

public interface TransportationDAO {
    Transportation getTransportationById(Object id);

    void save(Transportation transportation);

    void save(TransportationDocument document);

    void save(TransportationProduct product);

    List<Transportation> getTransportationsByDealProduct(Object product);
}
