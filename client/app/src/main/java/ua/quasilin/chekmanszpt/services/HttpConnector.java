package ua.quasilin.chekmanszpt.services;

import android.os.AsyncTask;
import android.util.Log;

import org.json.simple.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import ua.quasilin.chekmanszpt.packets.JsonPool;
import ua.quasilin.chekmanszpt.packets.Packet;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class HttpConnector extends AsyncTask<String, Void, String> {

    final static JsonPool pool = JsonPool.getPool();
    final static String TAG = "HttpConnector";
    final static int CONNECTION_TIMEOUT = 5000;

    private final static HttpConnector connector = new HttpConnector();

    public static HttpConnector getConnector() {
        return connector;
    }

    private static final String METHOD = "POST";
    private static final String ENCODING = "UTF-8";

    public String request(String url, Packet packet){
        return request(url, packet.toJson());
    }
    private String request(String url, String body){
        return doInBackground(url, body);
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder builder = new StringBuilder();

        try {
            Log.i(TAG, "Connect to " + strings[0]);
            URL u = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestMethod(METHOD);
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            urlConnection.connect();

            BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, ENCODING));
            writer.write(strings[1]);
            writer.close();
            out.close();

            Log.i("Response code", String.valueOf(urlConnection.getResponseCode()));
            Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream(), ENCODING);
            while (httpResponseScanner.hasNextLine()) {
                builder.append(httpResponseScanner.nextLine());
            }
            httpResponseScanner.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            JSONObject object = pool.getObject();
            object.put("status", "error");
            object.put("msg", e.getLocalizedMessage());
            builder.append(object.toJSONString());
            pool.put(object);
            e.printStackTrace();

        }
        return builder.toString();
    }
}
