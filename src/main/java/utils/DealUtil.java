package utils;

import entity.documents.Deal;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import utils.hibernate.dao.DealDAO;
import utils.hibernate.dao.TransportationDAO;
import utils.hibernate.dao.TransportationStatus;

import java.util.List;

public class DealUtil {

    private final DealDAO dealDAO = new DealDAO();
    private final TransportationDAO transportationDAO = new TransportationDAO();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public void removeDeal(Deal deal) {
        final List<Transportation> list = transportationDAO.getTransportationsByDeal(deal.getId(), TransportationStatus.all);
        boolean archive = false;
        for (Transportation transportation : list){
            if (transportation.any()){
                archive = true;
                transportation.setArchive(true);
                dealDAO.save(transportation);
                updateUtil.onArchive(transportation);
            } else {
                dealDAO.remove(transportation);
                updateUtil.onRemove(transportation);
            }
        }

        if (deal.getComplete() > 0 || archive){
            deal.setArchive(true);
            dealDAO.save(deal);
            updateUtil.onArchive(deal);
        } else {
            dealDAO.remove(deal);
            updateUtil.onRemove(deal);

        }
    }

    public JSONArray dealsToArray(Object organisationId) {
        List<Deal> deals = dealDAO.getDealsByOrganisation(organisationId);
        JSONArray array = new JSONArray();
        for (Deal deal : deals){
            array.add(deal.toJson());
        }
        return array;
    }
}
