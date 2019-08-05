package ua.quasilin.chekmanszpt.activity.messages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.entity.Chat;
import ua.quasilin.chekmanszpt.entity.ChatContainer;

public class MessageActivity extends AppCompatActivity {

    MessagesViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        int chatPosition = getIntent().getIntExtra("chat", -1);
        Chat chat = ChatContainer.getChat(chatPosition);

        adapter = new MessagesViewAdapter(getApplicationContext(),R.layout.message_list_row, chat.getMessages());
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                Log.i("MA", "onChange");
                adapter.notifyDataSetChanged();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(chat.getTitle());
        setSupportActionBar(toolbar);

        ListView messagesList = findViewById(R.id.messagesList);
        messagesList.setAdapter(adapter);
        EditText messageInput = findViewById(R.id.messageInput);
        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            String text = messageInput.getText().toString();
            System.out.println("send " + text);
            messageInput.getText().clear();
        });
    }
}
