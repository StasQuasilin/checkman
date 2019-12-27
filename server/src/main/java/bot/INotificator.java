package bot;

import constants.Constants;
import entity.AnalysesType;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.transport.Transportation;
import utils.DateUtil;
import utils.LanguageBase;

import java.sql.Date;

/**
 * Created by szpt_user045 on 27.12.2019.
 */
public abstract class INotificator implements Constants{

    private final LanguageBase lb = LanguageBase.getBase();

    private static final String TRANSPORT = "bot.notificator.transport";
    private static final String TRANSPORT_IN = "bot.notificator.transport.in";
    private static final String TRANSPORT_OUT_BUY = "bot.notificator.transport.out.buy";
    private static final String TRANSPORT_DONE = "bot.notification.transport.done";
    private static final String BRUTTO = "bot.notificator.brutto";
    private static final String TARA = "bot.notificator.tara";
    private static final String NETTO = "bot.notificator.netto";
    private static final String HUMIDITY_1 = "bot.notificator.humidity1";
    private static final String HUMIDITY_2 = "bot.notificator.humidity2";
    private static final String SORENESS = "bot.notificator.soreness";
    private static final String OILINESS = "bot.notificator.oiliness";
    private static final String PROTEIN = "bot.notificator.protein";
    private static final String CELLULOSE = "bot.notificator.cellulose";
    private static final String OIL_IMPURITY = "bot.notificator.oil.impurity";
    private static final String ACID = "bot.notificator.oil.acid";
    private static final String COLOR = "bot.notificator.color";
    private static final String PEROXIDE = "bot.notificator.peroxide";
    private static final String PHOSPHORUS = "bot.notificator.phosphorus";
    private static final String SOAP = "bot.notificator.soap";
    private static final String WAX = "bot.notificator.wax";
    private static final String NO_DATA = "no.data";
    private static final String TRANSPORT_REGISTRATION = "bot.transport.registration";
    private static final String TRANSPORT_INTO = "bot.transport.into";
    private static final String TRANSPORTATION_WEIGHT = "bot.transport.weight";
    private static final String TURN = "turn";
    private static final String ORGANOLEPTIC = "oil.organoleptic";
    private static final String MATCH = "oil.organoleptic.match";
    private static final String NO_MATCH = "oil.organoleptic.match";
    private static final String SOAP_HAVE = "notification.kpo.soap.yes";
    private static final String SOAP_NO = "notification.kpo.soap.no";
    private static final String CORRECTION = "notification.weight.correction";
    private static final String DRY_PROTEIN = "extraction.turn.protein.dry";
    private static final String WAX_HAVE = "notification.kpo.wax.yes";
    private static final String WAX_NO = "notification.kpo.wax.no";
    private static final String HUMIDITY = "bot.extraction.oil.humidity";

    public String getMessage(ExtractionCrude crude, String language){
        int turnNumber = crude.getTurn().getTurn().getNumber();
        String turnDate = DateUtil.prettyDate(Date.valueOf(crude.getTurn().getTurn().getDate().toLocalDateTime().toLocalDate()));
        String turnTime = crude.getTime().toLocalDateTime().toLocalTime().toString();

        String message = lb.get(language, "extraction.title");
        message += NEW_LINE + String.format(lb.get(language, "extraction.turn"), turnNumber, turnDate, turnTime);
        message += NEW_LINE + String.format(lb.get(language, "extraction.humidity.income"), crude.getHumidityIncome());
        message += NEW_LINE + String.format(lb.get(language, "extraction.oiliness.income"), crude.getOilinessIncome());
        message += NEW_LINE + String.format(lb.get(language, "extraction.fraction"), crude.getFraction());
        message += NEW_LINE + String.format(lb.get(language, "extraction.dissolvent"), crude.getDissolvent());
        message += NEW_LINE + String.format(lb.get(language, "extraction.miscellas"), crude.getMiscellas());
        message += NEW_LINE + String.format(lb.get(language, "extraction.humidity"), crude.getHumidity());
        message += NEW_LINE + String.format(lb.get(language, "extraction.oiliness"), crude.getGrease());
        message += NEW_LINE + String.format(lb.get(language, "extraction.oil.explosion.t"), crude.getExplosionTemperature());
        return message;
    }

    String getMessage(Transportation transportation, OilAnalyses analyses, String language) {
        StringBuilder builder = new StringBuilder();
        builder.append(prepareMessage(transportation)).append(NEW_LINE);
        builder.append(lb.get(ORGANOLEPTIC)).append(analyses.isOrganoleptic() ? lb.get(MATCH) : lb.get(NO_MATCH)).append(NEW_LINE);
        builder.append(String.format(lb.get(COLOR), analyses.getColor())).append(NEW_LINE);
        builder.append(String.format(lb.get(ACID), analyses.getAcidValue())).append(NEW_LINE);
        builder.append(String.format(lb.get(PEROXIDE), analyses.getPeroxideValue())).append(NEW_LINE);
        if (analyses.getPhosphorus() > 0) {
            builder.append(String.format(lb.get(PHOSPHORUS), analyses.getPhosphorus())).append(NEW_LINE);
        }
        builder.append(String.format(lb.get(HUMIDITY), analyses.getHumidity())).append(NEW_LINE);
        AnalysesType type = transportation.getProduct().getAnalysesType();

        if (type == AnalysesType.raf) {
            builder.append(String.format(lb.get(WAX),
                    analyses.isWaxB() ? lb.get(WAX_HAVE) : lb.get(WAX_NO))).append(NEW_LINE);
            builder.append(String.format(lb.get(SOAP),
                    (analyses.isSoap() ? lb.get(SOAP_HAVE) : lb.get(SOAP_NO)))).append(NEW_LINE);
        }
        if (type == AnalysesType.oil && analyses.getExplosion() > 0){
            builder.append(String.format(lb.get(language, "bot.notificator.explosion"), analyses.getExplosion()));
        }
        return builder.toString();
    }

    String getMessage(Transportation transportation, SunAnalyses analyses, float correction, String language){
        String corr = correction > 0 ? String.format(lb.get(CORRECTION), correction) + NEW_LINE : "";
        return prepareMessage(transportation) + NEW_LINE +
                (analyses.isContamination() ? "❗ Заражено шкідниками ❗" + NEW_LINE : "") +
                String.format(lb.get(language, HUMIDITY_1), analyses.getHumidity1()) + NEW_LINE +
                (analyses.getHumidity2() > 0 ? String.format(lb.get(language, HUMIDITY_2), analyses.getHumidity2()) + NEW_LINE : "") +
                String.format(lb.get(language, SORENESS), analyses.getSoreness()) + NEW_LINE +
                String.format(lb.get(language, OILINESS), analyses.getOiliness()) + NEW_LINE +
                String.format(lb.get(language, ACID), analyses.getAcidValue()) + NEW_LINE +
                String.format(lb.get(language, OIL_IMPURITY), analyses.getOilImpurity()) + NEW_LINE +
                corr + NEW_LINE;
    }

    public String prepareMessage(Transportation transportation){
        String driver = transportation.getDriver() != null ? transportation.getDriver().getPerson().getValue() : lb.get(NO_DATA);
        String organisation = transportation.getCounterparty().getValue();
        String product = transportation.getProduct().getName();

        return String.format(lb.get(TRANSPORT), organisation, driver, product);
    }
}
