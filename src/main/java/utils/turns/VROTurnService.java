package utils.turns;

import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.Turn;
import utils.TurnDateTime;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 20.05.2019.
 */
public class VROTurnService {

    static final dbDAO dao = dbDAOService.getDAO();

    public static VROTurn getTurn(TurnDateTime turnDate){
        Turn turn = TurnService.getTurn(turnDate);
        VROTurn vroTurn = dao.getVROTurnByTurn(turn);
        if (vroTurn == null) {
            vroTurn = new VROTurn();
            vroTurn.setTurn(turn);
            dao.save(vroTurn);
        }
        return vroTurn;
    }
}
