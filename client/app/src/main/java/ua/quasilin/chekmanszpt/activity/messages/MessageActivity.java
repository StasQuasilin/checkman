package ua.quasilin.chekmanszpt.activity.messages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import ua.quasilin.chekmanszpt.R;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getApplicationContext().getResources().getString(R.string.messages));
        setSupportActionBar(toolbar);
        Button contacts = findViewById(R.id.contacts);
        contacts.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ContactsActivity.class));
        });
    }

    public static void addChat(Object chat) {

    }
}
