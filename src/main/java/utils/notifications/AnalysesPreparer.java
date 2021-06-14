package utils.notifications;

import entity.documents.DealProduct;
import entity.transport.Driver;
import entity.transport.TransportationProduct;
import utils.LanguageBase;

public abstract class AnalysesPreparer extends NotificationPreparer {

    private static final String TRANSPORT = "bot.notificator.transport";
    private static final String NO_DATA = "no.data";
    private final TransportationProduct transportationProduct;
    protected final LanguageBase languageBase;

    public AnalysesPreparer(TransportationProduct transportationProduct, LanguageBase languageBase) {
        super();
        this.transportationProduct = transportationProduct;
        this.languageBase = languageBase;
    }

    @Override
    public String prepareMessage(String lang) {
        return prepareMessage(transportationProduct, lang) + analysesData(transportationProduct, lang);
    }

    public abstract String analysesData(TransportationProduct transportationProduct, String lang);

    public String prepareMessage(TransportationProduct transportation, String lang){
        final Driver driver1 = transportation.getTransportation().getDriver();
        String driver = driver1 != null ? driver1.getPerson().getValue() : languageBase.get(lang, NO_DATA);
        final DealProduct dealProduct = transportation.getDealProduct();
        String organisation = dealProduct.getDeal().getOrganisation().getValue();
        String product = dealProduct.getProduct().getName();

        return String.format(languageBase.get(lang, TRANSPORT), organisation, driver, product);
    }
}
