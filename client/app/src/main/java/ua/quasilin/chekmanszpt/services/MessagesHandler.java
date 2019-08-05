package ua.quasilin.chekmanszpt.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ua.quasilin.chekmanszpt.activity.messages.ChatsActivity;
import ua.quasilin.chekmanszpt.entity.Chat;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.entity.ChatMessage;
import ua.quasilin.chekmanszpt.entity.subscribes.Subscriber;
import ua.quasilin.chekmanszpt.utils.JsonParser;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public final class MessagesHandler {

    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String CONTACTS = "contacts";
    private static final String CHATS = "chats";
    private static final String UPDATE = "update";

    @SuppressLint("StaticFieldLeak")
    private static MessagesHandler handler = null;

    public static MessagesHandler getHandler() {
        return handler;
    }

    private Context context;
    private MessagesHandler(Context context) {
        this.context = context;
    }

    public static void init(Context context){
        handler  = new MessagesHandler(context);
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
                                        ChatContainer.addMessage(new ChatMessage(m));
                                    }
                                }


                                break;
                        }
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
}
