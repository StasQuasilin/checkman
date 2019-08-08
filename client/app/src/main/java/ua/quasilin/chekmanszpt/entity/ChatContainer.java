package ua.quasilin.chekmanszpt.entity;

import java.io.Serializable;
import java.util.ArrayList;

import ua.quasilin.chekmanszpt.utils.NotificationBuilder;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class ChatContainer implements Serializable {
    public static Worker worker;
    private static final ArrayList<Chat> chats = new ArrayList<>();
    private static final ArrayList<ChatContact> contacts = new ArrayList<>();

    public static void addChat(Chat chat){

        boolean haveChat = false;
        for (Chat c : chats){
            if (c.getKey().equals(chat.getKey())) {
                haveChat = true;
                c.setId(chat.getId());
                break;
            }
        }
        System.out.println("Have chat " + haveChat);
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
        System.out.println("Add contact " + contact.getWorker().getValue());
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
}
