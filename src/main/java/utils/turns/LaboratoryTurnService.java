package utils.turns;

import entity.laboratory.LaboratoryTurn;
import entity.production.Turn;
import org.apache.log4j.Logger;
import utils.TurnDateTime;
import utils.hibernate.DateContainers.LE;
import utils.hibernate.Hibernator;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
public class LaboratoryTurnService {

    final static Logger log = Logger.getLogger(LaboratoryTurnService.class);
    final static HashMap<LocalTime, Integer> intervals = new HashMap<>();
    static {
        intervals.put(LocalTime.of(8, 0), 5 * 12);
        intervals.put(LocalTime.of(20, 0), 3 * 12);
    }
    final static int MAX_ATTEMPT = 3;

    private static final Hibernator hibernator = Hibernator.getInstance();
    public static List<LaboratoryTurn> getTurns(TurnDateTime date){
        return getTurns(TurnService.getTurn(date));
    }
    public static List<LaboratoryTurn> getTurns(Turn turn){
        log.info("Get laboratory turn " + turn.getNumber() + ", time " + turn.getDate() + "...");

        List<LaboratoryTurn> turns = hibernator.query(LaboratoryTurn.class, "turn", turn);
        if (turns.size() == 0){
            log.info("\t...No turns");
            LocalDate date = turn.getDate().toLocalDateTime().toLocalDate();
            if (anyTurn(date)) {
                LocalTime time = turn.getDate().toLocalDateTime().toLocalTime();
                List<LaboratoryTurn> temp = new LinkedList<>();
                prevTurn(date, time, 0, temp);
                if (temp.size() > 0) {
                    for (LaboratoryTurn laboratoryTurn : temp) {
                        LaboratoryTurn newTurn = new LaboratoryTurn();
                        newTurn.setTurn(turn);
                        newTurn.setWorker(laboratoryTurn.getWorker());
                        hibernator.save(newTurn);
                        turns.add(newTurn);
                    }
                }
            }
        }
        return turns;
    }

    private static void prevTurn(LocalDate date, LocalTime time, int attempt, List<LaboratoryTurn> turns) {
        if (attempt < MAX_ATTEMPT){
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            dateTime = dateTime.minusHours(intervals.get(time));
            log.info("Previous turn date " + dateTime);
            Turn turn = hibernator.get(Turn.class, "date", Timestamp.valueOf(dateTime));
            if (turn != null) {
                turns.addAll(hibernator.query(LaboratoryTurn.class, "turn", turn));
                if (turns.size() == 0) {
                    prevTurn(dateTime.toLocalDate(), time, ++attempt, turns);
                }
            } else {
                prevTurn(dateTime.toLocalDate(), time, ++attempt, turns);
            }
        }
    }

    static boolean anyTurn(LocalDate date){
        final HashMap<String, Object> param = new HashMap<>();
        param.put("date", new LE(Date.valueOf(date.plusDays(1))));
        return hibernator.limitQuery(Turn.class, param, 1).size() > 0;
    }
}
