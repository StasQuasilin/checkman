package utils.turns;

import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.vro.VROTurn;
import utils.TurnBox;
import utils.hibernate.Hibernator;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 20.05.2019.
 */
public class VROTurnService {
    private static final Hibernator hibernator = Hibernator.getInstance();
    public static VROTurn getTurn(TurnBox.TurnDateTime turnDate){
        VROTurn turn = hibernator.get(VROTurn.class, "date", Timestamp.valueOf(turnDate.getDate()));
        if (turn == null) {
            turn = new VROTurn();
            turn.setNumber(turnDate.getTurnNumber());
            turn.setDate(Timestamp.valueOf(turnDate.getDate()));
            hibernator.save(turn);
        }
        return turn;
    }
}
