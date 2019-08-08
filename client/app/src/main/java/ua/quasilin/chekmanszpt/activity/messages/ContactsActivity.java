package ua.quasilin.chekmanszpt.activity.messages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.services.MessagesHandler;
import ua.quasilin.chekmanszpt.utils.AdapterList;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Button buttonClose = findViewById(R.id.closeContacts);
        buttonClose.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ChatsActivity.class));
        });

        ContactsViewAdapter adapter = new ContactsViewAdapter(getApplicationContext(),
                R.layout.chat_list_row, ChatContainer.getContacts());
        ListView contactList = findViewById(R.id.contactList);
        contactList.setAdapter(adapter);
        AdapterList.add(adapter);
    }
}
