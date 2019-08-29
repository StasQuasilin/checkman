package ua.quasilin.chekmanszpt.entity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.activity.messages.MessageActivity;
import ua.quasilin.chekmanszpt.utils.NotificationBuilder;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class ChatContainer implements Serializable {
    public static Worker worker;
    private static final ArrayList<Chat> chats = new ArrayList<>();
    private static final ArrayList<ChatContact> contacts = new ArrayList<>();
    public static String token;

    public static void addChat(Chat chat){

        boolean haveChat = false;
        for (Chat c : chats){
            if (c.getKey().equals(chat.getKey())) {
                haveChat = true;
                c.setId(chat.getId());
                break;
            }
        }
        if (!haveChat) {
            if (chat.getTitle().isEmpty()){
                for (Worker worker : chat.getMembers()){
                    if (worker.getId() != ChatContainer.worker.getId()){
                        chat.setTitle(worker.getValue());
                        break;
                    }
                }
            }
            chats.add(chat);
        }
    }

    public static void sortChats(){
        Collections.sort(chats);
    }

    public static boolean addMessage(ChatMessage message){
        for (Chat chat : chats){
            if (chat.getId() == message.getChat() && chat.isOpen()){
                chat.addMessage(message);
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Chat> getChats() {
        return chats;
    }

    public static Chat getChat(int position){
        return chats.get(position);
    }

    public static void addContact(ChatContact contact) {
        contacts.add(contact);
    }

    public static ArrayList<ChatContact> getContacts() {
        return contacts;
    }

    public static int findChatByWorker(long workerId) {
        int chatPosition = -1;
        for (int i = 0; i < chats.size(); i++){
            for (Worker worker : chats.get(i).getMembers()){
                if (worker.getId() == workerId){
                    chatPosition = i;
                    break;
                }
            }
        }
        return chatPosition;
    }

    public static void addChat(Chat chat, int index) {
        chats.add(index, chat);
    }

    public static void addChatId(long id, String key) {
        for (Chat chat : chats){
            if (chat.getKey().equals(key)){
                chat.setId(id);
            }
        }
    }

    public static void clear() {
        for (Chat chat : chats){
            Log.i("Clear chat", chat.getTitle());
            chat.getMembers().clear();
            chat.getMessages().clear();
        }
        chats.clear();
        contacts.clear();
    }

    private static boolean selected = false;
    public static void selectAllContacts() {
        selected = !selected;
        for (ChatContact contact : contacts){
            contact.select(selected);
        }
    }

    public static boolean createChat(String chatName, Context context) {
        long id = -1;
        ArrayList<Worker> members = new ArrayList<>();
        for (ChatContact contact : contacts){
            if (contact.isSelected()){
                members.add(contact.getWorker());
            }
        }
        if(members.size() > 0) {
            for (ChatContact contact : contacts){
                contact.select(false);
            }
            String title;
            if (chatName == null || chatName.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                int titleSize = members.size() < 3 ? members.size() : 3;
                for (int i = 0; i < titleSize; i++) {
                    builder.append(members.get(i).getPerson().getSurname());
                    if (i < titleSize - 1) {
                        builder.append(", ");
                    }
                    if (members.size() > 3) {
                        builder.append(context.getResources().getString(R.string.andOthers));
                    }
                }
                title = builder.toString();
            } else {
                title = chatName;
            }

            Chat chat = new Chat(id, title, members);
            int chatPosition = 0;
            chat.setOpen(true);
            addChat(chat, chatPosition);
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("chat", chatPosition);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
