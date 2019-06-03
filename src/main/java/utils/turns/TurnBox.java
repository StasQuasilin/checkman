package utils.turns;

import entity.production.TurnSettings;
import utils.TurnDateTime;
import utils.boxes.IBox;
import utils.hibernate.HibernateSessionFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by quasilin on 01.04.2019.
 */
public class TurnBox extends IBox{

    private static final TurnBox BOX = new TurnBox();

    public static TurnBox getBox() {
        return BOX;
    }

    private List<TurnSettings> turns;
    private final TurnDateTime def;
    public TurnBox() {
        turns = HIBERNATOR.query(TurnSettings.class, null);
        turns.sort((o1, o2) -> Integer.compare( o1.getNumber(), o2.getNumber()));

        if (turns.size() > 0) {
            def = new TurnDateTime(turns.get(0).getNumber(), LocalDateTime.now());
        } else {
            def = new TurnDateTime(-1, LocalDateTime.now());
        }
    }

    public TurnDateTime getTurnDate(LocalDateTime date){
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

    public List<TurnSettings> getTurns() {
        return turns;
    }

    public TurnSettings getTurn(long turnId) {
        for (TurnSettings turn : turns) {
            if (turn.getId() == turnId) {
                return turn;
            }
        }
        return turns.get(0);
    }

    public static void main(String[] args) {
        LocalDateTime of = LocalDateTime.of(2019, 4, 8, 2, 0);
        for (int i = 0; i < 6; i++){
            getBox().getTurnDate(of);
            of = of.plusHours(6);
        }

        HibernateSessionFactory.shutdown();
    }

}
