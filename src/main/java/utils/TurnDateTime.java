package utils;

import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
public class TurnDateTime {
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
