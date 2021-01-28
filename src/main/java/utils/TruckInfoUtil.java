package utils;

import entity.transport.TruckInfo;
import entity.transport.Vehicle;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.ArrayList;

/**
 * Created by szpt_user045 on 23.12.2019.
 */
public class TruckInfoUtil {

    dbDAO dao = dbDAOService.getDAO();
    private OpenDataBotAPI openData = new OpenDataBotAPI();

    public ArrayList<TruckInfo> getInfo(String num){
        String number = clearNumber(num);
        ArrayList<TruckInfo> infos = new ArrayList<>();
        infos.addAll(dao.getTruckInfo(number));
        if (infos.size() == 0){
            openData.infoRequest(infos, number);
            infos.forEach(dao::save);
        }

        return infos;
    }

    public static String clearNumber(String number){
        StringBuilder builder = new StringBuilder();
        for (char c : number.toUpperCase().toCharArray()){
            if (Character.isDigit(c) || Character.isLetter(c)){
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
