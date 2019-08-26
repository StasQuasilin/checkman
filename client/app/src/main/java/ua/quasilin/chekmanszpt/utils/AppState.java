package ua.quasilin.chekmanszpt.utils;

import android.content.Context;
import android.content.Intent;

import ua.quasilin.chekmanszpt.activity.LoginActivity;
import ua.quasilin.chekmanszpt.activity.NoConnectionActivity;
import ua.quasilin.chekmanszpt.activity.StartActivity;
import ua.quasilin.chekmanszpt.activity.messages.ChatsActivity;

/**
 * Created by Kvasik on 14.08.2019.
 */

public final class AppState {

    public static State state = State.findServer;

    public static void openActivity(Context context) {
        Intent intent;
        switch (AppState.state){
            case chats:
                intent = new Intent(context, ChatsActivity.class);
                break;
            case login:
                intent = new Intent(context, LoginActivity.class);
                break;
            case noServer:
                intent = new Intent(context, NoConnectionActivity.class);
                break;
            default:
                intent = new Intent(context, StartActivity.class);
        }
        context.startActivity(intent);
    }

    public enum State {
        findServer,
        noServer,
        login,
        chats
    }
}
