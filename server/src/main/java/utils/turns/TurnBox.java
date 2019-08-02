package utils.turns;

import entity.production.TurnSettings;
import utils.TurnDateTime;
import utils.boxes.IBox;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by quasilin on 01.04.2019.
 */
public final class TurnBox{

    static final dbDAO dao = dbDAOService.getDAO();

    private static List<TurnSettings> turns;
    private static final TurnDateTime def;
    static  {
        turns = dao.getTurnSettings();
        turns.sort((o1, o2) -> Integer.compare( o1.getNumber(), o2.getNumber()));

        if (turns.size() > 0) {
            def = new TurnDateTime(turns.get(0).getNumber(), LocalDateTime.now());
        } else {
            def = new TurnDateTime(-1, LocalDateTime.now());
        }
    }

    public static TurnDateTime getTurnDate(LocalDateTime date){
        System.out.println("Look at: " + date);
        for (TurnSettings turn : turns) {
            LocalTime _b = turn.getBegin().toLocalTime();
            LocalDateTime begin = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), _b.getHour(), _b.getMinute());

            LocalTime _e = turn.getEnd().toLocalTime();
            LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), _e.getHour(), _e.getMinute());
            if (begin.isAfter(end)) {
                end = end.plusDays(1);
            }
            if (date.getHour() <  begin.getHour()){
                begin = begin.minusDays(1);
                end = end.minusDays(1);
            }

            if ((date.isAfter(begin) || date.equals(begin)) && date.isBefore(end)){
                System.out.println("\tTurn# " + turn.getNumber() + ", ");
                System.out.println("\t" + begin);
                return new TurnDateTime(turn.getNumber(), begin);
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
}
