package utils;

import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import utils.hibernate.Hibernator;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 15.05.2019.
 */
public class ExtractionTurnService {
    private static final Hibernator hibernator = Hibernator.getInstance();
    public static ExtractionTurn getTurn(TurnBox.TurnDateTime turnDate){
        ExtractionTurn turn = hibernator.get(ExtractionTurn.class, "date", Timestamp.valueOf(turnDate.getDate()));
        if (turn == null) {
            turn = new ExtractionTurn();
            turn.setNumber(turnDate.getTurnNumber());
            turn.setDate(Timestamp.valueOf(turnDate.getDate()));
            hibernator.save(turn);
        }
        return turn;
    }
}
