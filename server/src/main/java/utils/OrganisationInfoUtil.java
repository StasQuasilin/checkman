package utils;

import constants.Constants;
import entity.Worker;
import entity.notifications.Notification;
import entity.organisations.Address;
import entity.organisations.LegalAddress;
import entity.organisations.Organisation;
import entity.organisations.OrganisationInfo;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.notifications.Notificator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 06.02.2020.
 */
public class OrganisationInfoUtil implements Constants{

    private final OpenDataBotAPI api = new OpenDataBotAPI();
    private Notificator notificator = new Notificator();
    private dbDAO dao = dbDAOService.getDAO();

    public boolean checkOrganisation(Organisation organisation, Worker worker){
        if (!U.exist(organisation.getCode())) {
            ArrayList<OrganisationInfo> info = new ArrayList<>();
            api.searchCompany(organisation.getValue(), info);
            if (info.size() == 1){
                convertOrganisationInfo(info.get(0), organisation);
                return true;
            }
//            else if (info.size() > 1){
//                sendNotification(info, worker);
//            }
        }
        return false;
    }

    public void convertOrganisationInfo(OrganisationInfo info, Organisation organisation){
        String code = info.getCode();
        organisation.setCode(code);
        LegalAddress legalAddress = dao.getLegalAddress(organisation);
        if (legalAddress == null){
            legalAddress = new LegalAddress();
            legalAddress.setOrganisation(organisation);
            legalAddress.setAddress(new Address());
        }

        parseAddress(legalAddress.getAddress(), info.getLocation());
        dao.save(legalAddress.getAddress());
        dao.save(legalAddress);
        dao.save(organisation);
    }

    private static final String INDEX_REGEX = "\\d{5}";
    private static final String REGION_REGEX = "[А-Яа-яІіИиЇї]*\\sобл\\.?";
    private static final String DISTRICT_REGEX = "[А-Яа-яІіИиЇї]*\\s(р-н|район)";
    private static final String CITY_REGEX = "(місто|село|селище|селище міського типу|м.|с.|смт.)\\s[А-Яа-яІіИиЇї[\\s-]]*,?";
    private static final String STREET_REGEX = "(вулиця|вул.|провулок|пров.|проспект|просп.)\\s[А-Яа-яІіИиЇї[\\s-]]*,?";
    private static final String BUILD_REGEX = "(будинок|буд.)\\s.+,?";
    private void parseAddress(Address address, String value){
        String index = parse(INDEX_REGEX, value);
        address.setIndex(index);
        System.out.println(index);
        String region = parse(REGION_REGEX, value);
        if (region != null){
            region = region.replaceAll("\\sобл.", EMPTY);
            address.setRegion(region.substring(0, 1).toUpperCase() + region.substring(1));
        }
        System.out.println(region);
        String district = parse(DISTRICT_REGEX, value);
        if (district != null){
            district = district.replaceAll("\\sр.*",EMPTY);
            address.setDistrict(district.substring(0, 1).toUpperCase() + district.substring(1));
        }
        System.out.println(district);
        String city = parse(CITY_REGEX, value);
        if (city != null){
            address.setCity(city.replaceAll(",", EMPTY));
        }
        System.out.println(city);
        String street = parse(STREET_REGEX, value);
        if (street != null){
            address.setStreet(street.replaceAll(",", EMPTY));
        }
        System.out.println(street);
        String build = parse(BUILD_REGEX, value);
        if (build != null){
            address.setBuild(build);
        }

        System.out.println(build);
    }

    private String parse(String regex, String string){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string.toLowerCase());
        if (matcher.find()){
            return matcher.group();
        }
        return null;
    }

    private final static String MESSAGE_TEXT =
            "<html>" +
                "<div style=\"font-size: 9pt;\">" +
                    "<b>" +
                        "Яка з перечислених організацій мається на увазі?" +
                    "</b>" +
                    "<div style=\"max-height: 240pt; overflow-y: scroll;\">%1s</div>" +
                "</div>" +
            "</html>";
    private static final String ROW_FORMAT = "<div style=\"border-bottom: solid black 1pt;\">" +
                "<div><b>Код ЄДРПОУ:</b> %1s,</div>" +
                "<div><b>Адреса:</b> %2s,</div>" +
                "<div><b>Діяльність:</b> %3s</div>" +
            "</div>";
    private void sendNotification(ArrayList<OrganisationInfo> info, Worker worker) {
        StringBuilder builder = new StringBuilder();
        for (OrganisationInfo i : info){
            builder.append(String.format(ROW_FORMAT, i.getCode(), i.getLocation(), i.getActivities()));
        }
        notificator.sendNotification(worker, new Notification(String.format(MESSAGE_TEXT, builder.toString()), true).toJson());
    }
}
