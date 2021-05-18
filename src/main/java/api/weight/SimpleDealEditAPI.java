package api.weight;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.answers.Answer;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.transport.ActionTime;
import entity.weight.Unit;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;
import utils.hibernate.dao.DealDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet(Branches.API.Transportation.DEAL_EDIT)
public class SimpleDealEditAPI extends ServletAPI {


    private final UpdateUtil updateUtil = new UpdateUtil();
    private DealDAO dealDAO = new DealDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
            boolean save = false;
            boolean newDeal = false;
            Deal deal = dealDAO.getDealById(body.get(ID));
            if (deal == null){
                deal = new Deal();
                final Date date = Date.valueOf(LocalDate.now());
                deal.setDate(date);
                deal.setDateTo(date);
                deal.setCreate(new ActionTime(getWorker(req)));
                newDeal = true;
            }

            final DealType type = DealType.valueOf((String) body.get(TYPE));
            if (newDeal || deal.getType() != type){
                deal.setType(type);
                save = true;
            }

            final Organisation organisation = dao.getObjectById(Organisation.class, body.get(ORGANISATION));
            if (newDeal || deal.getOrganisation().getId() != organisation.getId()){
                deal.setOrganisation(organisation);
                save = true;
            }

            final Product product = dao.getObjectById(Product.class, body.get(PRODUCT));
            if (newDeal || deal.getProduct().getId() != product.getId()){
                deal.setProduct(product);
                save = true;
            }

            int price = initIntValue(body, PRICE);
            if(newDeal || deal.getPrice() != price){
                deal.setPrice(price);
                save = true;
            }
            //shipper
            final Shipper shipper = dao.getObjectById(Shipper.class, body.get(SHIPPER));
            if(newDeal || deal.getShipper().getId() != shipper.getId()){
                deal.setShipper(shipper);
                save = true;
            }

            int quantity = initIntValue(body, QUANTITY);
            if (newDeal || deal.getQuantity() != quantity){
                deal.setQuantity(quantity);
                save = true;
            }
            Unit unit = dao.getObjectById(Unit.class, body.get(UNIT));
            if(newDeal || deal.getUnit().getId() != unit.getId()){
                deal.setUnit(unit);
            }

            if (save){
                dao.save(deal.getCreate());
                dao.save(deal);

                updateUtil.onSave(deal);
            }

            Answer answer = new SuccessAnswer();
            answer.add(Constants.ID, deal.getId());
            answer.add(Constants.DEAL, deal.toJson());

            write(resp, answer.toJson());
        }

    }

    private int initIntValue(JSONObject body, String key) {
        if (body.containsKey(key)){
            String s = String.valueOf(body.get(key));
            if(!s.isEmpty()){
                return Integer.parseInt(s);
            }
        }
        return 0;
    }
}
