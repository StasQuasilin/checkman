package ua.quasilin.chekmanszpt.activity.messages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.entity.ChatContact;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.utils.AdapterList;

public class ContactsActivity extends AppCompatActivity {

    public static boolean isGroupChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ImageButton buttonClose = findViewById(R.id.closeContacts);
        buttonClose.setOnClickListener(v -> onBackPressed());
        CheckBox isGroup = findViewById(R.id.isGroup);
        isGroupChecked = isGroup.isChecked();
        LinearLayout layout = findViewById(R.id.groupSettings);
        showGroupSettings(isGroup.isChecked(), layout);
        EditText chatNameInput = findViewById(R.id.groupNameInput);
        chatNameInput.clearFocus();

        Button selectAll= findViewById(R.id.selectAll);
        Button createChat = findViewById(R.id.createChat);
        ContactsViewAdapter adapter = createAdapter();
        ListView contactList = findViewById(R.id.contactList);
        contactList.setAdapter(adapter);
        AdapterList.add(adapter);
        isGroup.setOnClickListener(v -> {
            isGroupChecked = isGroup.isChecked();
            showGroupSettings(isGroup.isChecked(), layout);
            adapter.notifyDataSetChanged();
        });
        selectAll.setOnClickListener(v -> {
            ChatContainer.selectAllContacts();
            adapter.notifyDataSetChanged();
        });
        createChat.setOnClickListener(v -> {
            if (ChatContainer.createChat(chatNameInput.getText().toString(), getApplicationContext())){
                chatNameInput.getText().clear();
            }
        });
    }

    private void showGroupSettings(boolean show, LinearLayout layout) {
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        if (show){
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }else {
            layoutParams.height = 0;
        }
        layout.setLayoutParams(layoutParams);
    }

    private ContactsViewAdapter createAdapter() {
        return new ContactsViewAdapter(getApplicationContext(),
                R.layout.chat_list_row, ChatContainer.getContacts());
    }


}
