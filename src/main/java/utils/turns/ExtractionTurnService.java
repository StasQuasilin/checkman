package utils.turns;

import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.production.Turn;
import utils.TurnDateTime;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 15.05.2019.
 */
public class ExtractionTurnService {

    static final dbDAO dao = dbDAOService.getDAO();

    public static ExtractionTurn getTurn(TurnDateTime turnDate){
        Turn turn = TurnService.getTurn(turnDate);
        ExtractionTurn extractionTurn = dao.getExtractionTurnByTurn(turn);
        if (extractionTurn == null) {
            extractionTurn = new ExtractionTurn();
            extractionTurn.setTurn(turn);
            dao.save(extractionTurn);
        }
        return extractionTurn;
    }
}
