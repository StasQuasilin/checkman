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
}
