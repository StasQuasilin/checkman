package utils.turns;

import entity.laboratory.LaboratoryTurn;
import entity.production.Turn;
import utils.TurnDateTime;
import utils.hibernate.Hibernator;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
public class LaboratoryTurnService {
    private static final Hibernator hibernator = Hibernator.getInstance();
    public static List<LaboratoryTurn> getTurns(TurnDateTime date){
        return getTurns(TurnService.getTurn(date));
    }
    public static List<LaboratoryTurn> getTurns(Turn turn){
        return hibernator.query(LaboratoryTurn.class, "turn", turn);
    }
}
