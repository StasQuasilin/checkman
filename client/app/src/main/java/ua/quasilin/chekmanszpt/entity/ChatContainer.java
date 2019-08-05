package ua.quasilin.chekmanszpt.entity;

import java.util.ArrayList;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public final class ChatContainer {
    public static Worker worker;
    private static final int MESSAGE_LIST_SIZE = 28;
    private static final ArrayList<Chat> chats = new ArrayList<>();

    public static void addChat(Chat chat){
        chats.add(chat);
    }
    public static void addMessage(ChatMessage message){
        for (Chat chat : chats){
            if (chat.getId() == message.getChat()){
                chat.addMessage(message);
                break;
            }
        }
    }

    public static ArrayList<Chat> getChats() {
        return chats;
    }

    public static Chat getChat(int position){
        return chats.get(position);
    }
}
