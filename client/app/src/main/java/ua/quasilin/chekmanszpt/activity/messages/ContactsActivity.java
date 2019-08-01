package ua.quasilin.chekmanszpt.activity.messages;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ua.quasilin.chekmanszpt.R;

public class ContactsActivity extends AppCompatActivity {

    static ListView contactList;
    static List<String> data = new ArrayList<>();
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_contacts);
        Button buttonClose = findViewById(R.id.closeContacts);
        buttonClose.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MessageActivity.class));
        });

        contactList = findViewById(R.id.contactList);
        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data);
        contactList.setAdapter(adapter);
    }

    public static void addContact(Object contact) {
        JSONObject json = (JSONObject) (contact);
        JSONObject person = (JSONObject) json.get("person");
        data.add(String.valueOf(person.get("value")));
    }
}
