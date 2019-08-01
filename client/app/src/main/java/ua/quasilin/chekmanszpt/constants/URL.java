package ua.quasilin.chekmanszpt.constants;

/**
 * Created by Kvasik on 30.07.2019.
 */

public final class URL {

    public static final String[] EXTERNAL_SERVER_ADDRESS = {

    };
    public static final String[] INTERNAL_SERVER_ADDRESS = {
            "10.10.10.201:3322/checkman"
    };
    private static final String PROTOCOL_WS = "ws";
    private static final String PROTOCOL_HTTP = "http";
    private static String currentAddress = INTERNAL_SERVER_ADDRESS[0];
    public static final String SOCKET_ADDRESS = "ws://10.10.10.201:3322/checkman/echo";
    public static final String SUBSCRIBER = "/api/subscriber";
    public static final String PING = "/ping";
    public static final String LOGIN = "/a/sign/in";
    public static final String FIND_WORKER = "/a/worker/find";
    private static final String API = "/api/old";
    public static final String CHAT_SEND = API + "/chat/send";

    public static String buildWsAddress(String url){
        return buildAddress(PROTOCOL_WS, url);
    }
    public static String buildHttpAddress(String url){
        return buildAddress(PROTOCOL_HTTP, url);
    }
    private static String buildAddress(String protocol, String url){
        return protocol + "://" + currentAddress + url;
    }
}
