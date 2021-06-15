package utils.notifications.preparers;

import entity.AnalysesType;
import entity.laboratory.OilAnalyses;
import entity.transport.TransportationProduct;
import utils.LanguageBase;
import utils.notifications.AnalysesPreparer;
import utils.notifications.NotificationPreparer;

import static bot.TelegramNotificator.NEW_LINE;
import static bot.TelegramNotificator.SPACE;

public class OilAnalysesPreparer extends AnalysesPreparer {

    private static final String MATCH = "oil.organoleptic.match";
    private static final String NO_MATCH = "oil.organoleptic.match";
    private static final String ORGANOLEPTIC = "oil.organoleptic";
    private static final String COLOR = "bot.notificator.color";
    private static final String ACID = "bot.notificator.oil.acid";
    private static final String PEROXIDE = "bot.notificator.peroxide";
    private static final String PHOSPHORUS = "bot.notificator.phosphorus";
    private static final String HUMIDITY = "bot.extraction.oil.humidity";
    private static final String WAX = "bot.notificator.wax";
    private static final String WAX_HAVE = "notification.kpo.wax.yes";
    private static final String WAX_NO = "notification.kpo.wax.no";
    private static final String SOAP = "bot.notificator.soap";
    private static final String SOAP_HAVE = "notification.kpo.soap.yes";
    private static final String SOAP_NO = "notification.kpo.soap.no";
    
    public OilAnalysesPreparer(TransportationProduct transportationProduct, LanguageBase languageBase) {
        super(transportationProduct, languageBase);
    }

    @Override
    public String analysesData(TransportationProduct transportationProduct, String lang) {
        final OilAnalyses analyses = transportationProduct.getOilAnalyses();
        StringBuilder builder = new StringBuilder();
        builder.append(languageBase.get(lang,ORGANOLEPTIC)).append(SPACE);
        builder.append(analyses.isOrganoleptic() ? languageBase.get(lang,MATCH) : languageBase.get(lang,NO_MATCH)).append(NEW_LINE);
        builder.append(String.format(languageBase.get(lang,COLOR), analyses.getColor())).append(NEW_LINE);
        builder.append(String.format(languageBase.get(lang,ACID), analyses.getAcidValue())).append(NEW_LINE);
        builder.append(String.format(languageBase.get(lang,PEROXIDE), analyses.getPeroxideValue())).append(NEW_LINE);
        if (analyses.getPhosphorus() > 0) {
            builder.append(String.format(languageBase.get(lang,PHOSPHORUS), analyses.getPhosphorus())).append(NEW_LINE);
        }
        builder.append(String.format(languageBase.get(lang,HUMIDITY), analyses.getHumidity())).append(NEW_LINE);
        AnalysesType type = transportationProduct.getDealProduct().getProduct().getAnalysesType();

        if (type == AnalysesType.raf) {
            builder.append(String.format(languageBase.get(lang,WAX),
                    analyses.isWaxB() ? languageBase.get(lang,WAX_HAVE) : languageBase.get(lang,WAX_NO))).append(NEW_LINE);
            builder.append(String.format(languageBase.get(lang,SOAP),
                    (analyses.isSoap() ? languageBase.get(lang,SOAP_HAVE) : languageBase.get(lang,SOAP_NO)))).append(NEW_LINE);
        }
        if (type == AnalysesType.oil && analyses.getExplosion() > 0){
            builder.append(String.format(languageBase.get(lang, "bot.notificato-r.explosion"), analyses.getExplosion()));
        }
        return builder.toString();
    }
}
