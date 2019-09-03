package ua.quasilin.chekmanszpt.entity;

import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;

import org.json.simple.JSONObject;

import java.util.Calendar;
import java.util.Date;

import ua.quasilin.chekmanszpt.utils.DateUtil;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class ChatMessage implements Comparable<ChatMessage> {

    private int id;
    private int chat;
    private Date time;
    private Worker sender;
    private String text;

    public ChatMessage(Object o) {
        JSONObject message = (JSONObject) o;
        id = Integer.parseInt(String.valueOf(message.get("id")));
        chat = Integer.parseInt(String.valueOf(message.get("chat")));
        sender = new Worker(message.get("sender"));
        text = (String) message.get("message");
        time = new Date((Long) message.get("epoch"));

    }

    @Override
    public int compareTo(@NonNull ChatMessage o) {
        return time.compareTo(o.time);
    }

    public int getId() {
        return id;
    }

    public int getChat() {
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
        return DateUtil.getTime(time);
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

}
