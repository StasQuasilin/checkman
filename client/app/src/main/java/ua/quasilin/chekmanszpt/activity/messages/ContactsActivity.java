package ua.quasilin.chekmanszpt.activity.messages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import ua.quasilin.chekmanszpt.R;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Button buttonClose = findViewById(R.id.closeContacts);
        buttonClose.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MessageActivity.class));
        });
    }
}
