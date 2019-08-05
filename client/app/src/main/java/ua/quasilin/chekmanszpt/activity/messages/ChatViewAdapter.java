package ua.quasilin.chekmanszpt.activity.messages;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.entity.Chat;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class ChatViewAdapter extends ArrayAdapter<Chat> {

    private final Context context;
    private final ArrayList<Chat> chats;

    ChatViewAdapter(@NonNull Context context, int resource, ArrayList<Chat> chats) {
        super(context, resource, chats);
        this.context = context;
        this.chats = chats;
    }

    public Chat getItem(int id){
        return chats.get(id);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.chat_list_row, parent, false);
        } else {
            view = convertView;
        }
        Chat item = chats.get(position);

        TextView chatName = view.findViewById(R.id.chatName);
        chatName.setText(item.getTitle());

        view.setOnClickListener(v -> {
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("chat", position);
            context.startActivity(intent);
        });
        return view;
    }


}
