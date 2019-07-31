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
    private static final String PROTOCOL = "http";
    private static String currentAddress = INTERNAL_SERVER_ADDRESS[0];
    public static final String SOCKET_ADDRESS = "ws://10.10.10.201:3322/checkman/echo";
    public static final String PING = "/ping";
    public static final String LOGIN = "/a/sign/in";
    public static String buildAddress(String url){
        return PROTOCOL + "://" + currentAddress + url;
    }
}
