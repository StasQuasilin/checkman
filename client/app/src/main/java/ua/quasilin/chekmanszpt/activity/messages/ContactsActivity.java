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

public class ContactsActivity extends AppCompatActivity {

    static List<String> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Button buttonClose = findViewById(R.id.closeContacts);
        buttonClose.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MessageActivity.class));
        });

        ListView contactList = findViewById(R.id.contactList);
        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data);
        contactList.setAdapter(adapter);
    }

    public static void addContact(Context context, Object contact) {
        JSONObject json = (JSONObject) (contact);
        JSONObject person = (JSONObject) json.get("person");

        data.add(String.valueOf(person.get("value")));
    }
}
