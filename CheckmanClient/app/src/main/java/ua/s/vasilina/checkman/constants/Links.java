package ua.s.vasilina.checkman.constants;

public interface Links {
    String HOME = "/checkman";
    String EXTERNAL = "ws://91.222.16.30" + HOME;
    String INTERNAL = "ws://10.10.10.201:3322" + HOME;
    String API = "/api/v1";
    String SUBSCRIBE = "/api/subscriber";
    String EXTERNAL_SUBSCRIBE = EXTERNAL + SUBSCRIBE;
    String INTERNAL_SUBSCRIBE = INTERNAL + SUBSCRIBE;
}
