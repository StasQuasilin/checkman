package utils.turns;

import entity.production.Turn;
import utils.TurnDateTime;
import utils.hibernate.Hibernator;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
public class TurnService {
    private static final Hibernator hibernator = Hibernator.getInstance();
    public static Turn getTurn(TurnDateTime turnDate){
        Turn turn = hibernator.get(Turn.class, "date", Timestamp.valueOf(turnDate.getDate()));
        if (turn == null) {
            turn = new Turn();
            turn.setDate(Timestamp.valueOf(turnDate.getDate()));
            turn.setNumber(turnDate.getTurnNumber());
            hibernator.save(turn);
        }
        return turn;
    }
}
