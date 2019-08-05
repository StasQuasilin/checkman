package ua.quasilin.chekmanszpt.entity;

import android.support.annotation.NonNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class Chat implements Comparable<Chat>{

    private long id;
    private String title;
    private final ArrayList<ChatMessage> messagesArray = new ArrayList<>();

    public Chat(Object chat) {
        JSONObject json = (JSONObject) chat;
        id = (long) json.get("id");
        title = (String) json.get("title");
        JSONArray messages = (JSONArray) json.get("messages");
        if (messages != null) {
            for (Object o : messages){
                messagesArray.add(new ChatMessage(o));
            }
        }
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        Collections.sort(messagesArray);
        return messagesArray.get(0).getText();
    }

    @Override
    public int compareTo(@NonNull Chat o) {
        return 0;
    }

    public void addMessage(ChatMessage message) {
        messagesArray.add(message);
        Collections.sort(messagesArray);
    }

    public ArrayList<ChatMessage> getMessages() {
        return messagesArray;
    }
}
