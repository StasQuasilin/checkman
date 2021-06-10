package utils.notifications.preparers;

import entity.DealType;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.products.ProductProperty;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import utils.LanguageBase;
import utils.hibernate.dao.NotificationDAO;

import java.util.Set;

import static constants.Constants.COMMA;
import static constants.Constants.SPACE;

public class RegistrationPreparer extends utils.notifications.NotificationPreparer {
    private static final String FOR = "notification.transportation.for";
    private static final String ON = "notification.transportation.on";
    private static final String INCOME_VEHICLE = "notification.transportation.income.vehicle";
    private static final String DRIVER = "notification.transportation.driver";
    private static final String OUT_OF_TERRITORY = "notification.transportation.out.of.territory";

    private final Transportation transportation;
    private final LanguageBase lb;
    private final NotificationDAO dao;
    public RegistrationPreparer(Transportation transportation, LanguageBase languageBase, NotificationDAO dao) {
        this.transportation = transportation;
        lb = languageBase;
        this.dao = dao;
    }


    @Override
    public String prepareMessage(String lang) {
        StringBuilder builder = new StringBuilder();
        builder.append(lb.get(lang, ON)).append(SPACE);
        final Set<TransportationProduct> products = transportation.getProducts();
        final int size = products.size();
        int i = 0;
        for (TransportationProduct product : products){
            final DealProduct dealProduct = product.getDealProduct();
            final Deal deal = dealProduct.getDeal();
            final DealType type = deal.getType();

            String action = lb.get(lang, "_" + type.toString()).toLowerCase();
            String productName = dao.getProductProperty(dealProduct.getProduct(), type.toString());
            String organisation = deal.getOrganisation().getValue();
            builder.append(action).append(SPACE).append(productName).append(SPACE);
            builder.append(lb.get(lang, FOR)).append(SPACE).append(organisation);
            if (i < size - 1){
                builder.append(COMMA).append(SPACE);
            }
            i++;
        }
        builder.append(lb.get(lang, INCOME_VEHICLE));
        final Driver driver = transportation.getDriver();
        if (driver != null){
            builder.append(COMMA).append(SPACE);
            builder.append(lb.get(lang, DRIVER)).append(SPACE).append(driver.toString());
        }
        builder.append(SPACE);
        builder.append(lb.get(lang, OUT_OF_TERRITORY));
        return builder.toString();
    }
}
