package ua.quasilin.chekmanszpt.entity;

import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;

import org.json.simple.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class ChatMessage implements Comparable<ChatMessage> {

    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    private long id;
    private long chat;
    private Date time;
    private Worker sender;
    private String text;
//    2019-08-05 10:01:15.246


    public ChatMessage(Object o) {
        JSONObject message = (JSONObject) o;
        id = (long) message.get("id");
        chat = (long) message.get("chat");
        sender = new Worker(message.get("sender"));
        text = (String) message.get("message");
        time = Calendar.getInstance().getTime();
    }

    @Override
    public int compareTo(@NonNull ChatMessage o) {
        return time.compareTo(o.time);
    }

    public long getId() {
        return id;
    }

    public long getChat() {
        return chat;
    }

    public Worker getSender() {
        return sender;
    }

    public Date getTime() {
        return time;
    }

    public String getText() {
        return text;
    }


    public String getTimeString() {

        return null;
    }
}
