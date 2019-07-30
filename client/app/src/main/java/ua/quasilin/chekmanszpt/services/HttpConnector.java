package ua.quasilin.chekmanszpt.services;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import ua.quasilin.chekmanszpt.packets.Packet;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class HttpConnector extends AsyncTask<String, Void, String> {

    private final static HttpConnector connector = new HttpConnector();

    static HttpConnector getConnector() {
        return connector;
    }

    private static final String METHOD = "POST";
    private static final String ENCODING = "UTF-8";

    String request(String url, Packet packet){
        return request(url, packet.toJson());
    }
    private String request(String url, String body){
        return doInBackground(url, body);
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder builder = new StringBuilder();
        try {
            URL u = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpsURLConnection) u.openConnection();
            urlConnection.setRequestMethod(METHOD);
            urlConnection.connect();
            BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, ENCODING));
            writer.write(strings[1]);
            writer.close();
            out.close();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream(), ENCODING);
                while (httpResponseScanner.hasNextLine()) {
                    builder.append(httpResponseScanner.nextLine());
                }
                httpResponseScanner.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
