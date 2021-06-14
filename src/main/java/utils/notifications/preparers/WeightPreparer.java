package utils.notifications.preparers;

import entity.DealType;
import entity.bot.NotifyStatus;
import entity.bot.UserNotificationSetting;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.transport.Driver;
import entity.transport.TransportationProduct;
import utils.LanguageBase;
import utils.hibernate.dao.NotificationDAO;
import utils.notifications.NotificationPreparer;

public class WeightPreparer extends NotificationPreparer {

    private static final String TRANSPORTATION_WEIGHT = "bot.transport.weight";

    private final TransportationProduct transportationProduct;
    private final LanguageBase languageBase;
    private final NotificationDAO dao;

    public WeightPreparer(TransportationProduct transportationProduct, LanguageBase languageBase, NotificationDAO dao) {
        this.transportationProduct = transportationProduct;
        this.languageBase = languageBase;
        this.dao = dao;
    }

    @Override
    public String prepareMessage(String lang) {
        final DealProduct dealProduct = transportationProduct.getDealProduct();
        final Deal deal = dealProduct.getDeal();
        DealType type = deal.getType();
        String action = languageBase.get(lang, "_" + type + ".do");
        String driverValue = "--";
        final Driver driver = transportationProduct.getTransportation().getDriver();
        if (driver !=null){
            driverValue = driver.getPerson().getValue();
        }
        String organisation = deal.getOrganisation().getValue();
        String product = dealProduct.getProduct().getName();
        final float net = transportationProduct.getWeight().getNetto();
        return String.format(languageBase.get(lang, TRANSPORTATION_WEIGHT), action, driverValue, organisation, product, net);
    }
}
