package ua.quasilin.chekmanszpt.services;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationManagerCompat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;

import ua.quasilin.chekmanszpt.activity.StartActivity;
import ua.quasilin.chekmanszpt.activity.messages.MessageActivity;
import ua.quasilin.chekmanszpt.entity.Chat;
import ua.quasilin.chekmanszpt.entity.ChatContact;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.entity.ChatMessage;
import ua.quasilin.chekmanszpt.entity.subscribes.Subscriber;
import ua.quasilin.chekmanszpt.utils.AdapterList;
import ua.quasilin.chekmanszpt.utils.JsonParser;
import ua.quasilin.chekmanszpt.utils.NotificationBuilder;
import ua.quasilin.chekmanszpt.utils.Starter;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public class MessagesHandler {

    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String CONTACTS = "contacts";
    private static final String CHATS = "chats";
    private static final String UPDATE = "update";

    private final Context context;
    private final BackgroundService service;
    MessagesHandler(Context context, BackgroundService service) {
        this.context = context;
        this.service = service;
    }

    private static final String MESSAGES = "messages";
    public void handle(String text){
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                String message = msg.getData().getString(MESSAGES);
                JSONObject json = JsonParser.parse(message);
                if (json != null) {
                    Object type = json.get(TYPE);
                    if (type != null) {
                        Subscriber subscriber = Subscriber.valueOf(type.toString());
                        JSONObject data = (JSONObject) json.get(DATA);
                        switch (subscriber){
                            case MESSAGES:
                                JSONObject update = (JSONObject) data.get(UPDATE);
                                JSONArray chats = (JSONArray) update.get(CHATS);
                                if (chats != null) {
                                    for (Object c : chats) {
                                        ChatContainer.addChat(new Chat(c));
                                    }
                                }
                                JSONArray messages = (JSONArray) update.get(MESSAGES);
                                if (messages != null) {
                                    for (Object m : messages){
                                        ChatMessage chatMessage = new ChatMessage(m);
                                        if (!ChatContainer.addMessage(chatMessage)){
                                            int notificationId = (int) chatMessage.getId();
                                            Intent intent = new Intent(context, MessageActivity.class);
                                            intent.putExtra("chatId", chatMessage.getChat());
                                            Notification build = NotificationBuilder.build(
                                                    context, notificationId,
                                                    chatMessage.getSender().getValue(),
                                                    chatMessage.getText(), intent);
                                            NotificationManagerCompat compat = NotificationManagerCompat.from(context);
                                            compat.notify(notificationId, build);
                                        }
                                    }
                                }
                                JSONArray contacts = (JSONArray) update.get(CONTACTS);
                                if (contacts != null) {
                                    for (Object c : contacts) {
                                        ChatContainer.addContact(new ChatContact(c));
                                    }
                                    Collections.sort(ChatContainer.getContacts());
                                }
                                break;
                        }
                        AdapterList.update();

                    }
                }
            }
        };
        new Thread(() -> {
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGES, text);
            message.setData(bundle);
            handler.sendMessage(message);
        }).start();
    }

    public void stop() {
        context.startActivity(new Intent(context, StartActivity.class));
        service.findServer();
    }
}
