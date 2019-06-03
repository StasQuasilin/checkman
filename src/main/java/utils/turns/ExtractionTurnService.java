package utils.turns;

import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.production.Turn;
import utils.TurnDateTime;
import utils.hibernate.Hibernator;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 15.05.2019.
 */
public class ExtractionTurnService {
    private static final Hibernator hibernator = Hibernator.getInstance();
    public static ExtractionTurn getTurn(TurnDateTime turnDate){
        Turn turn = TurnService.getTurn(turnDate);
        ExtractionTurn extractionTurn = hibernator.get(ExtractionTurn.class, "turn", turn);
        if (extractionTurn == null) {
            extractionTurn = new ExtractionTurn();
            extractionTurn.setTurn(turn);
            hibernator.save(extractionTurn);
        }
        return extractionTurn;
    }
}
