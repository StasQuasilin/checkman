package ua.quasilin.chekmanszpt.activity.messages;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.entity.Chat;
import ua.quasilin.chekmanszpt.entity.ChatContact;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.entity.Worker;

/**
 * Created by szpt_user045 on 08.08.2019.
 */

public class ContactsViewAdapter extends ArrayAdapter<ChatContact> {

    private final Context context;
    private final ArrayList<ChatContact> contacts;

    ContactsViewAdapter(@NonNull Context context, int resource, ArrayList<ChatContact> contacts) {
        super(context, resource, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.contact_list_row, parent, false);
        } else {
            view = convertView;
        }

        ChatContact contact = contacts.get(position);
        CheckBox inGroup = view.findViewById(R.id.inGroup);
        inGroup.setOnClickListener(v -> contact.select(inGroup.isChecked()));
        inGroup.setChecked(contact.isSelected());
        ViewGroup.LayoutParams layoutParams = inGroup.getLayoutParams();
        if (ContactsActivity.isGroupChecked){
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }else {
            layoutParams.width = 0;
        }
        inGroup.setLayoutParams(layoutParams);
        TextView contactName = view.findViewById(R.id.contactName);
        contactName.setText(contact.getWorker().getValue());

        view.setOnClickListener((View v) -> {
            if (ContactsActivity.isGroupChecked){
                inGroup.setChecked(!inGroup.isChecked());
                contact.select(inGroup.isChecked());
            } else {
                int chatPosition = ChatContainer.findChatByWorker(contact.getWorker().getId());

                if (chatPosition == -1){
                    chatPosition = 0;
                    final ArrayList<Worker> members = new ArrayList<>();
                    members.add(contact.getWorker());
                    members.add(ChatContainer.worker);
                    Chat chat = new Chat(
                            -1,
                            contact.getWorker().getValue(),
                            members
                    );
                    chat.setOpen(true);
                    ChatContainer.addChat(chat, chatPosition);
                }
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("chat", chatPosition);
                context.startActivity(intent);
            }

        });

        return view;
    }
}
