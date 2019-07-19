package utils.turns;

import entity.laboratory.probes.ProbeTurn;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.Turn;
import utils.TurnDateTime;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
public final class TurnService {

    final static dbDAO dao = dbDAOService.getDAO();

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

    public static ProbeTurn getProbeTurn(TurnDateTime turnDate){
        Turn turn = getTurn(turnDate);
        ProbeTurn probeTurn = dao.getProbeTurnByTurn(turn);
        if (probeTurn == null) {
            probeTurn = new ProbeTurn();
            probeTurn.setTurn(turn);
            dao.save(probeTurn);
        }
        return probeTurn;
    }

    public static ExtractionTurn getExtractionTurn(TurnDateTime turnDate){
        Turn turn = getTurn(turnDate);
        ExtractionTurn extractionTurn = dao.getExtractionTurnByTurn(turn);
        if (extractionTurn == null) {
            extractionTurn = new ExtractionTurn();
            extractionTurn.setTurn(turn);
            dao.save(extractionTurn);
        }
        return extractionTurn;
    }


    public static VROTurn getVROTurn(TurnDateTime turnDate){
        Turn turn = getTurn(turnDate);
        VROTurn vroTurn = dao.getVROTurnByTurn(turn);
        if (vroTurn == null) {
            vroTurn = new VROTurn();
            vroTurn.setTurn(turn);
            dao.save(vroTurn);
        }
        return vroTurn;
    }
}
