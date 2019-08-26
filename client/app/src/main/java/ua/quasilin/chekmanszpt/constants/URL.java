package ua.quasilin.chekmanszpt.constants;

/**
 * Created by Kvasik on 30.07.2019.
 */

public final class URL {

    public static final String[] EXTERNAL_SERVER_ADDRESS = {

    };
    public static final String[] INTERNAL_SERVER_ADDRESS = {
            "10.10.10.201:3322/checkman",
            "192.168.88.254:3322/checkman",
            "10.10.10.45:3332/che",
            "192.168.0.106:3322/checkman"
    };
    private static final String PROTOCOL_WS = "ws";
    public static final String PROTOCOL_HTTP = "http";
    public static String currentAddress = INTERNAL_SERVER_ADDRESS[0];
    public static final String SUBSCRIBER = "/api/subscriber";
    public static final String PING = "/ping";
    public static final String LOGIN = "/a/sign/in";
    public static final String FIND_WORKER = "/a/worker/find";
    private static final String API = "/api/old";
    public static final String CHAT_SEND = API + "/chat/send";
    public static final String GET_CHAT = API + "/chat/get";

    public static String buildWsAddress(String url){
        return buildAddress(PROTOCOL_WS, url);
    }
    public static String buildHttpAddress(String url){
        return buildAddress(PROTOCOL_HTTP, url);
    }
    private static String buildAddress(String protocol, String url){
        return buildAddress(protocol, currentAddress, url);
    }

    public static String buildAddress(String protocol, String address, String url){
        return protocol + "://" + address + url;
    }
}
