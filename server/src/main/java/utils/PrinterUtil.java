package utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * Created by szpt_user045 on 31.01.2020.
 */
public class PrinterUtil {

    private final OkHttpClient httpClient = new OkHttpClient();

    public void print(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body != null) {
                System.out.println(body.string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
