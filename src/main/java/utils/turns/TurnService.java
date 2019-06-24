package utils.turns;

import entity.production.Turn;
import utils.TurnDateTime;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
public class TurnService {

    static dbDAO dao = dbDAOService.getDAO();

    public static Turn getTurn(TurnDateTime turnDate){
        Turn turn = dao.getTurnByDate(turnDate);
        if (turn == null) {
            turn = new Turn();
            turn.setDate(Timestamp.valueOf(turnDate.getDate()));
            turn.setNumber(turnDate.getTurnNumber());
            dao.save(turn);
        }
        return turn;
    }
}
