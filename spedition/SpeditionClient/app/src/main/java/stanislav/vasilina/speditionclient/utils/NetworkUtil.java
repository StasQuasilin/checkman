package stanislav.vasilina.speditionclient.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static stanislav.vasilina.speditionclient.constants.Keys.TOKEN;

class NetworkUtil {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    static final String token = "OlololoTrolololo";

    String post(String url, String data) throws IOException {
        final RequestBody body = RequestBody.create(data, JSON);


        Request request = new Request.Builder()
                .url(url)
                .addHeader(TOKEN, token)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
