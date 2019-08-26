package ua.quasilin.chekmanszpt.entity;

import android.support.annotation.NonNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class Chat implements Comparable<Chat>{

    private static final int MESSAGE_ARRAY_SIZE = 10;

    private long id;
    private String title;
    private final ArrayList<Worker> members = new ArrayList<>();
    private final ArrayList<ChatMessage> messagesArray = new ArrayList<>();
    private String key;
    private boolean open = false;

    public Chat(Object chat) {
        JSONObject json = (JSONObject) chat;
        id = (long) json.get("id");
        title = (String) json.get("title");

        for (Object o : (JSONArray)json.get("members")){
            members.add(new Worker(o));
        }
        if (json.containsKey("key")){
            key = String.valueOf(json.get("key"));
        } else {
            key = UUID.randomUUID().toString();
        }

    }

    public Chat(long id, String title, ArrayList<Worker> members){
        this.id = id;
        this.title = title;
        this.members.addAll(members);
        key = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        if (messagesArray.size() > 0) {
            Collections.sort(messagesArray);
            ChatMessage chatMessage = messagesArray.get(messagesArray.size() - 1);
            return chatMessage.getSender().getValue() + ": " + chatMessage.getText();
        }
        return "";
    }

    @Override
    public int compareTo(@NonNull Chat o) {
        return 0;
    }

    void addMessage(ChatMessage message) {
        boolean contain = false;
        for (ChatMessage m : messagesArray) {
            if (m.getId() == message.getId()){
                contain = true;
            }
        }
        if (!contain){
            messagesArray.add(message);
        }
        Collections.sort(messagesArray);
        for (int i = 0; i < messagesArray.size() - MESSAGE_ARRAY_SIZE; i++){
            messagesArray.remove(0);
        }
    }

    public ArrayList<ChatMessage> getMessages() {
        return messagesArray;
    }

    public String getKey() {
        return key;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public ArrayList<Worker> getMembers() {
        return members;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
