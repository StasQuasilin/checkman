package utils;

import entity.production.Turn;
import utils.boxes.IBox;
import utils.hibernate.HibernateSessionFactory;

import java.sql.Time;
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

    private List<Turn> turns;
    private final TurnDateTime def;
    public TurnBox() {
        turns = HIBERNATOR.query(Turn.class, null);
        if (turns.size() > 0) {
            def = new TurnDateTime(turns.get(0).getNumber(), LocalDateTime.now());
        } else {
            def = new TurnDateTime(-1, LocalDateTime.now());
        }
    }

    public TurnDateTime getTurnDate(LocalDateTime date){
        System.out.println("Look at: " + date);
        for (Turn turn : turns) {
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

    public List<Turn> getTurns() {
        return turns;
    }

    public Turn getTurn(long turnId) {
        for (Turn turn : turns) {
            if (turn.getId() == turnId) {
                return turn;
            }
        }
        return turns.get(0);
    }

    public class TurnDateTime{
        int turnNumber;
        LocalDateTime date;

        public TurnDateTime(int turnNumber, LocalDateTime date) {
            this.turnNumber = turnNumber;
            this.date = date;
        }

        public int getTurnNumber() {
            return turnNumber;
        }

        public void setTurnNumber(int turnNumber) {
            this.turnNumber = turnNumber;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }
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
