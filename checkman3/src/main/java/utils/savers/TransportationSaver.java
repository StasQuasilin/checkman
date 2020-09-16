package utils.savers;

import entity.transportations.Transportation;
import entity.transportations.TransportationDocument;
import entity.transportations.TransportationProduct;
import utils.db.dao.DaoService;
import utils.db.dao.transportations.TransportationDAO;

public class TransportationSaver {

    private final TransportationDAO dao = DaoService.getTransportationDAO();

    public void save(TransportationProduct product) {
        final TransportationDocument document = product.getDocument();
        final Transportation transportation = document.getTransportation();
        dao.save(transportation);
        dao.save(document);
        dao.save(product);
    }
}
