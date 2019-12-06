package utils.turns;

import entity.production.TurnSettings;
import org.apache.log4j.Logger;
import utils.TurnDateTime;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by quasilin on 01.04.2019.
 */
public final class TurnBox{

    private static final Logger log = Logger.getLogger(TurnBox.class);
    private static final dbDAO dao = dbDAOService.getDAO();
    private static List<TurnSettings> turns;
    private static TurnDateTime def;
    static  {
        init();
    }

    static void init(){
        turns = dao.getTurnSettings();
        turns.sort((o1, o2) -> Integer.compare( o1.getNumber(), o2.getNumber()));

        def = new TurnDateTime(-1, LocalDateTime.now(), LocalDateTime.now());
    }

    public static TurnDateTime getTurnDate(LocalDateTime date){
        if (turns.size() == 0) {
            log.warn("No any turn setting!!!");
        }
//        else {
//            log.info("Look at turn for: " + date);
//        }

        for (TurnSettings turn : turns) {
            LocalTime _b = turn.getBegin().toLocalTime();
            LocalDateTime begin = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), _b.getHour(), _b.getMinute());

            LocalTime _e = turn.getEnd().toLocalTime();
            LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), _e.getHour(), _e.getMinute());
            if (begin.isAfter(end)) {
                end = end.plusDays(1);
            }
            if (date.getHour() < begin.getHour()){
                begin = begin.minusDays(1);
                end = end.minusDays(1);
            }

            if ((date.isAfter(begin) || date.equals(begin)) && date.isBefore(end)){
//                log.info("\tTurn# " + turn.getNumber() + ", ");
//                log.info("\t" + begin);
//                log.info("\t" + end);
                return new TurnDateTime(turn.getNumber(), begin, end);
            }
        }
        def.setDate(date);
        return def;
    }

    public static List<TurnSettings> getTurns() {
        return turns;
    }

    public static TurnSettings getTurn(long turnId) {
        for (TurnSettings turn : turns) {
            if (turn.getId() == turnId) {
                return turn;
            }
        }
        return turns.get(0);
    }

    public static TurnSettings getTurnByNumber(int number) {
        for (TurnSettings turn : turns) {
            if (turn.getNumber() == number) {
                return turn;
            }
        }
        return turns.get(0);
    }
}
