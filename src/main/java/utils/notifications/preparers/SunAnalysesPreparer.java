package utils.notifications.preparers;

import entity.laboratory.SunAnalyses;
import entity.transport.TransportationProduct;
import entity.weight.Weight;
import utils.LanguageBase;
import utils.notifications.AnalysesPreparer;

import static bot.TelegramNotificator.NEW_LINE;

public class SunAnalysesPreparer extends AnalysesPreparer {

    private static final String CORRECTION = "notification.weight.correction";
    private static final String HUMIDITY_1 = "bot.notificator.humidity1";
    private static final String HUMIDITY_2 = "bot.notificator.humidity2";
    private static final String SORENESS = "bot.notificator.soreness";
    private static final String OILINESS = "bot.notificator.oiliness";
    private static final String ACID = "bot.notificator.oil.acid";
    private static final String OIL_IMPURITY = "bot.notificator.oil.impurity";

    public SunAnalysesPreparer(TransportationProduct transportationProduct, LanguageBase languageBase) {
        super(transportationProduct, languageBase);
    }

    @Override
    public String analysesData(TransportationProduct transportationProduct, String language) {
        final SunAnalyses analyses = transportationProduct.getSunAnalyses();
        final Weight weight = transportationProduct.getWeight();
        final float correction = weight != null ? weight.getCorrection() : 0;
        return (analyses.isContamination() ? "❗ Заражено шкідниками ❗" + NEW_LINE : "") +
                String.format(languageBase.get(language, HUMIDITY_1), analyses.getHumidity1()) + NEW_LINE +
                (analyses.getHumidity2() > 0 ? String.format(languageBase.get(language, HUMIDITY_2), analyses.getHumidity2()) + NEW_LINE : "") +
                String.format(languageBase.get(language, SORENESS), analyses.getSoreness()) + NEW_LINE +
                String.format(languageBase.get(language, OILINESS), analyses.getOiliness()) + NEW_LINE +
                String.format(languageBase.get(language, ACID), analyses.getAcidValue()) + NEW_LINE +
                String.format(languageBase.get(language, OIL_IMPURITY), analyses.getOilImpurity()) + NEW_LINE +
                (correction > 0 ? String.format(languageBase.get(language, CORRECTION), correction) + NEW_LINE : "");
    }
}
