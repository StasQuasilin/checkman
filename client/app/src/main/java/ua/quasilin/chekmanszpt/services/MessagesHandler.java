package ua.quasilin.chekmanszpt.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ua.quasilin.chekmanszpt.activity.messages.ContactsActivity;
import ua.quasilin.chekmanszpt.activity.messages.MessageActivity;
import ua.quasilin.chekmanszpt.entity.subscribes.Subscriber;
import ua.quasilin.chekmanszpt.utils.JsonParser;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public final class MessagesHandler {

    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String ADD = "add";
    private static final String CONTACTS = "contacts";
    public static final String CHATS = "chats";

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

    private static final String MESSAGE = "message";
    public void handle(String text){
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                String message = msg.getData().getString(MESSAGE);
                JSONObject json = JsonParser.parse(message);
                if (json != null) {
                    Object type = json.get(TYPE);
                    if (type != null) {
                        Subscriber subscriber = Subscriber.valueOf(type.toString());
                        JSONObject data = (JSONObject) json.get(DATA);
                        switch (subscriber){
                            case MESSAGES:
                                JSONObject add = (JSONObject) data.get(ADD);

                                for (Object contact : (JSONArray) add.get(CONTACTS)){
                                    System.out.println(contact);
                                    ContactsActivity.addContact(context, contact);
                                }
                                for (Object chat : (JSONArray)add.get(CHATS)){
                                    MessageActivity.addChat(chat);
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
            bundle.putString(MESSAGE, text);
            message.setData(bundle);
            handler.sendMessage(message);
        }).start();
    }
}
