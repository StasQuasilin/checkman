package utils.turns;

import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.Turn;
import utils.TurnDateTime;
import utils.hibernate.Hibernator;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 20.05.2019.
 */
public class VROTurnService {
    private static final Hibernator hibernator = Hibernator.getInstance();
    public static VROTurn getTurn(TurnDateTime turnDate){
        Turn turn = TurnService.getTurn(turnDate);
        VROTurn vroTurn = hibernator.get(VROTurn.class, "turn", turn);
        if (vroTurn == null) {
            vroTurn = new VROTurn();
            vroTurn.setTurn(turn);
            hibernator.save(vroTurn);
        }
        return vroTurn;
    }
}
