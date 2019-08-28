package ua.quasilin.chekmanszpt.activity.messages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.entity.ChatContainer;

public class ChatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getApplicationContext().getResources().getString(R.string.chats));
        setSupportActionBar(toolbar);

        ListView chatList = findViewById(R.id.chatList);
        ChatViewAdapter adapter = new ChatViewAdapter(getApplicationContext(), R.layout.chat_list_row, ChatContainer.getChats());
        chatList.setAdapter(adapter);
        ImageButton button = findViewById(R.id.newChat);
        button.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ContactsActivity.class)));
    }

    @Override
    public void onBackPressed() {

    }
}
