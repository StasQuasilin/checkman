package ua.svasilina.spedition.entity.changes;

import java.util.Calendar;

import ua.svasilina.spedition.entity.CurrentLocation;

public class Change {
    private String filed;
    private String oldValue;
    private String newValue;
    private Calendar time;
    private CurrentLocation location;
}
