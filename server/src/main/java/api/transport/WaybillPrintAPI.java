package api.transport;

import api.ServletAPI;
import api.laboratory.ActNumberService;
import constants.Branches;
import entity.Gender;
import entity.Worker;
import entity.laboratory.transportation.ActType;
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
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kvasik on 01.02.2020.
 */
@WebServlet(Branches.API.WAYBILL_PRINT)
public class WaybillPrintAPI extends ServletAPI {

    public static final String WAYBILL_PAGE = "/pages/weight/waybillPage.jsp";

    final Locale locale = new Locale("UK", "uk");
    DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, locale);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            int number = Integer.parseInt(String.valueOf(body.get(NUMBER)));
            ActNumberService.updateNumber(ActType.waybill, number);
            req.setAttribute(NUMBER, number);
            req.setAttribute(DATE, df.format(Date.valueOf(String.valueOf(body.get(DATE)))) + R);
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

            req.setAttribute(NETTO, U.getNumberByWords(transportation.getWeight().getNetto(), Gender.female, T, KG));
            req.setAttribute(BRUTTO, U.getNumberByWords(transportation.getWeight().getBrutto(), Gender.female, T, KG));
            float price = Float.parseFloat(String.valueOf(body.get(PRICE)));
            if (price > 0) {
                float sum = transportation.getWeight().getNetto() * price;
                req.setAttribute(PRICE, U.getNumberByWords(price, HRN, KOP));
                req.setAttribute(SUM, U.getNumberByWords(sum, HRN, KOP));
                req.setAttribute(SUM_WORDS, U.getNumberByWords(sum, Gender.female, HRN, KOP));
                req.setAttribute(TAX, U.getNumberByWords(sum * 0.07, HRN, KOP));
            }
            req.setAttribute(BOOKER, dao.getObjectById(Worker.class, body.get(BOOKER)));
            req.setAttribute(ALLOWED, dao.getObjectById(Worker.class, body.get(ALLOWED)));
            req.setAttribute(HANDED_OVER, dao.getObjectById(Worker.class, body.get(HANDED_OVER)));
            req.getRequestDispatcher(WAYBILL_PAGE).forward(req, resp);
        }
    }
}
