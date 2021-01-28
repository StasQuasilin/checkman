package utils;

import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
public class TurnDateTime {
    int turnNumber;
    LocalDateTime date;
    LocalDateTime end;

    public TurnDateTime(int turnNumber, LocalDateTime date, LocalDateTime end) {
        this.turnNumber = turnNumber;
        this.date = date;
        this.end = end;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public LocalDateTime getEnd() {
        return end;
    }
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
