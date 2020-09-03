package entity.communications;

import entity.Worker;

import java.sql.Timestamp;

public class Message {
    private int id;
    private Timestamp timestamp;
    private Chat chat;
    private Worker sender;
}
