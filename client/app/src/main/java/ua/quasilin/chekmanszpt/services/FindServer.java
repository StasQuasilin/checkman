package ua.quasilin.chekmanszpt.services;

import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.packets.PingPacket;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public final class FindServer {

    private static HttpConnector connector = HttpConnector.getConnector();

    public static void find(){
        for (String internal : URL.INTERNAL_SERVER_ADDRESS){
            String answer = connector.request(internal + URL.PING, new PingPacket());
        }
    }
}
