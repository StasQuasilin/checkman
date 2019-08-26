package ua.quasilin.chekmanszpt.activity.messages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.entity.Chat;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.entity.ChatMessage;
import ua.quasilin.chekmanszpt.entity.SimpleChatMessage;
import ua.quasilin.chekmanszpt.packets.GetMessagesPacket;
import ua.quasilin.chekmanszpt.packets.SendMessagePacket;
import ua.quasilin.chekmanszpt.services.HttpConnector;
import ua.quasilin.chekmanszpt.services.MessagesHandler;
import ua.quasilin.chekmanszpt.utils.AdapterList;
import ua.quasilin.chekmanszpt.utils.JsonParser;

public class MessageActivity extends AppCompatActivity {

    MessagesViewAdapter adapter;
    public static final String MESSAGES = "messages";
    private final HttpConnector connector = HttpConnector.getConnector();
    Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        int chatPosition = -1;

        chatPosition = getIntent().getIntExtra("chat", chatPosition);
        if (chatPosition ==-1){
            long chatId = getIntent().getLongExtra("chatId", -1);
            if (chatId != -1) {
                int i = 0;
                for (Chat chat : ChatContainer.getChats()){
                    if (chat.getId() == chatId){
                        chatPosition = i;
                    }
                    i++;
                }
            }
        }
        if (chatPosition != -1) {
            chat = ChatContainer.getChat(chatPosition);
            chat.setOpen(true);

            adapter = new MessagesViewAdapter(getApplicationContext(), R.layout.message_list_row, chat.getMessages());

            ListView messagesList = findViewById(R.id.messagesList);
            messagesList.setAdapter(adapter);
            AdapterList.add(adapter);

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(chat.getTitle());
            setSupportActionBar(toolbar);

            EditText messageInput = findViewById(R.id.messageInput);

            messageInput.setOnKeyListener((v, keyCode, event) -> {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                    sendMessage(chat, messageInput);
                }
                return false;
            });

            ImageButton sendButton = findViewById(R.id.sendButton);
            sendButton.setOnClickListener(v -> sendMessage(chat, messageInput));

            ProgressBar progressBar = findViewById(R.id.messagesLoadProgress);
            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    String data = msg.getData().getString(MESSAGES);
                    JSONObject messages = JsonParser.parse(data);
                    if (messages != null && messages.containsKey(MESSAGES)) {
                        for (Object o : (JSONArray) messages.get(MESSAGES)) {
                            ChatContainer.addMessage(new ChatMessage(o));
                        }
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            };
            new Thread(() -> {
                Bundle bundle = new Bundle();
                bundle.putString(MESSAGES, connector.request(URL.buildHttpAddress(URL.GET_CHAT), new GetMessagesPacket(chat.getId())));
                Message message = handler.obtainMessage();
                message.setData(bundle);
                handler.sendMessage(message);
            }).start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (chat != null) {
            chat.setOpen(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (chat != null) {
            chat.setOpen(true);
        }
    }

    Thread sendThread;
    private void sendMessage(Chat chat, EditText messageInput){
        String text = messageInput.getText().toString();
        messageInput.getText().clear();
        sendMessage(chat, new SimpleChatMessage(text));
    }

    private void sendMessage(Chat chat, SimpleChatMessage message) {
        sendThread = new Thread(() -> {
            connector.request(URL.buildHttpAddress(URL.CHAT_SEND), new SendMessagePacket(chat, message));
        });
        sendThread.start();
    }
}
