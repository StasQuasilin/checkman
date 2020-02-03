package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.organisations.Address;
import entity.organisations.LoadAddress;
import entity.organisations.Organisation;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Kvasik on 01.02.2020.
 */
@WebServlet(Branches.API.WAYBILL_PRINT)
public class WaybillPrintAPI extends ServletAPI {

    public static final String WAYBILL_PAGE = "/pages/weight/waybillPage.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            req.setAttribute(NUMBER, body.get(NUMBER));
            req.setAttribute(DATE, body.get(DATE));
            Transportation transportation = dao.getObjectById(Transportation.class, body.get(TRANSPORTATION));
            Organisation counterparty = transportation.getCounterparty();
            req.setAttribute(TRANSPORTATION, transportation);
            req.setAttribute(ORGANISATION_TYPE, dao.getOrganisationTypeByName(counterparty.getType()));
            req.setAttribute(LEGAL_ADDRESS, dao.getLegalAddress(counterparty));
            req.setAttribute(SEALS, dao.getSealsByTransportation(transportation));
            Address address = dao.getObjectById(Address.class, body.get(ADDRESS));
            if (address == null){
                List<LoadAddress> loadAddress = dao.getLoadAddress(counterparty);
                if (loadAddress.size() > 0){
                    address = loadAddress.get(0).getAddress();
                }
            }
            req.setAttribute(ADDRESS, address);

            req.setAttribute(NETTO, U.getNumberByWords(transportation.getWeight().getNetto()));
            req.setAttribute(BRUTTO, U.getNumberByWords(transportation.getWeight().getBrutto()));
            float price = Float.parseFloat(String.valueOf(body.get(PRICE)));
            req.setAttribute(PRICE, U.getNumberByWords(transportation.getWeight().getNetto() * price));
            req.getRequestDispatcher(WAYBILL_PAGE).forward(req, resp);
        }
    }
}
