package utils;

import entity.transport.TruckInfo;
import utils.turns.TurnBox;

import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 19.12.2019.
 */
public class ODBTest {
    public static void main(String[] args) {
//        OpenDataBotAPI api = new OpenDataBotAPI();
//        TruckInfo info = new TruckInfo();
//        api.infoRequest("СВ2207СА", info);
//        System.out.println(info);
        System.out.println(TurnBox.getTurnDate(LocalDateTime.now()).getEnd());
    }
}
