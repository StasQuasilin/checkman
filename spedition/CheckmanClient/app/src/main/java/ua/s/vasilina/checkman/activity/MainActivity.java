package ua.s.vasilina.checkman.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import ua.s.vasilina.checkman.activity.deals.DealsActivity;
import ua.s.vasilina.checkman.connection.ConnectionListener;
import ua.s.vasilina.checkman.connection.SocketConnector;
import ua.s.vasilina.checkman.constants.Links;

public class MainActivity extends AppCompatActivity {

    SocketConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connector = SocketConnector.getInstance();


        connectExternal();
        connector.send("OLOLO!");
        final Context context = getApplicationContext();
        Intent intent = new Intent(context, DealsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    void connectExternal(){
        ConnectionListener onOpen = new ConnectionListener() {
            @Override
            public void onChange() {
                Log.i("Connection", "External connection open");
            }
        };
        ConnectionListener onFailure = new ConnectionListener() {
            @Override
            public void onChange() {
                System.out.println("FAILURE");
                connectInternal();
            }
        };
        connector.connect(Links.EXTERNAL_SUBSCRIBE, onOpen, onFailure,null);
    }

    private void connectInternal() {
        ConnectionListener onOpen = new ConnectionListener() {
            @Override
            public void onChange() {
                Log.i("Connection", "Internal connection open");
            }
        };
        ConnectionListener onFailure = new ConnectionListener() {
            @Override
            public void onChange() {
                System.out.println("FAILURE INTERNAL");
//                connectInternal();
            }
        };
        connector.connect(Links.INTERNAL_SUBSCRIBE, onOpen, onFailure,null);
    }
}