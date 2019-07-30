package ua.quasilin.chekmanszpt.utils.db;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class dbConnector {
    private static final dbConnector connector = new dbConnector();

    public static dbConnector getConnector() {
        return connector;
    }

    private dbConnector() {

    }

    void put(String key, String value){

    }
}
