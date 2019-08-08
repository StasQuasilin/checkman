package ua.quasilin.chekmanszpt.activity.messages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.entity.ChatMessage;
import ua.quasilin.chekmanszpt.entity.Worker;

/**
 * Created by szpt_user045 on 05.08.2019.
 */

public class MessagesViewAdapter extends ArrayAdapter<ChatMessage> {

    private final Context context;
    private final ArrayList<ChatMessage> messages;

    MessagesViewAdapter(@NonNull Context context, int resource, ArrayList<ChatMessage> messages) {
        super(context, resource, messages);
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.message_list_row, parent, false);
        } else {
            view = convertView;
        }

        ChatMessage chatMessage = messages.get(position);

        TextView timeLabel = view.findViewById(R.id.timeLabel);
        timeLabel.setText(chatMessage.getTimeString());

        Worker sender = chatMessage.getSender();

        TextView senderLabel = view.findViewById(R.id.senderLabel);
        senderLabel.setText(sender.getValue());

        ConstraintLayout body = view.findViewById(R.id.messageBody);
        LinearLayout row = view.findViewById(R.id.messageRow);

        if (sender.getId() == ChatContainer.worker.getId()){
            body.setBackgroundColor(context.getResources().getColor(R.color.message_my));
            row.setGravity(Gravity.START);
        } else {
            body.setBackgroundColor(context.getResources().getColor(R.color.message));
            row.setGravity(Gravity.END);
        }

        TextView message = view.findViewById(R.id.message);
        message.setText(chatMessage.getText());

        return view;
    }
}
