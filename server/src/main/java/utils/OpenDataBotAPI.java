package utils;


import entity.transport.TruckInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by szpt_user045 on 19.12.2019.
 */
public class OpenDataBotAPI {

    private static final Logger log = Logger.getLogger(OpenDataBotAPI.class);

    private String key = "9NfS7Rarm5yA";
    private String API = "https://opendatabot.ua/api/v2/transport-passports";
    private final OkHttpClient httpClient = new OkHttpClient();
    public static final String REQ_FORMAT = "%1s?apiKey=%2s&number=%3s";
    JsonParser parser = new JsonParser();

    public void InfoRequest(String number, TruckInfo info){
        log.info("Get info about " + number);
        Request request = new Request.Builder()
                .url(String.format(REQ_FORMAT, API, key, number))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody body = response.body();

            if (body != null){
                try {
                    JSONObject parse = parser.parse(body.string());
                    JSONObject data = (JSONObject) parse.get("data");
                    System.out.println(data);
                    if (data != null) {
                        Integer count = Integer.parseInt(String.valueOf(data.get("count")));
                        JSONArray array = (JSONArray) data.get("items");
                        if (count > 0){
                            JSONObject o = (JSONObject) array.get(0);
                            info.setCategory(String.valueOf(o.get("rankCategory")));
                            info.setColor(String.valueOf(o.get("color")));
                            info.setWeight(Integer.parseInt(String.valueOf(o.get("totalWeight"))));
                            info.setYear(String.valueOf(o.get("makeYear")));
                            info.setDocument(String.valueOf(o.get("number")));
                            info.setFuel(String.valueOf(o.get("fuel")));
                            info.setKind(String.valueOf(o.get("kind")));
                            info.setVin(String.valueOf(o.get("vin")));
                            info.setBrand(String.valueOf(o.get("brand")));
                            info.setModel(String.valueOf(o.get("model")));
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
