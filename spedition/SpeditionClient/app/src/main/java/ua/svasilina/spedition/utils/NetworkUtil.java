package ua.svasilina.spedition.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static ua.svasilina.spedition.constants.Keys.TOKEN;

public class NetworkUtil {
    private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();

    public String post(String url, String data, String token) throws IOException {
        final RequestBody requestBody = RequestBody.create(data, MEDIA_TYPE);

        final Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody);
        if (token != null) {
            builder.addHeader(TOKEN, token);
        }
        final Request request = builder.build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
