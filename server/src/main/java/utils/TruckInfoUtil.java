package utils;

import entity.transport.TruckInfo;
import entity.transport.Vehicle;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

/**
 * Created by szpt_user045 on 23.12.2019.
 */
public class TruckInfoUtil {

    dbDAO dao = dbDAOService.getDAO();
    private OpenDataBotAPI openData = new OpenDataBotAPI();

    public TruckInfo getInfo(String num){
        String number = clearNumber(num);
        TruckInfo truckInfo = dao.getTruckInfo(number);
        if (truckInfo == null){
            truckInfo = new TruckInfo();
            truckInfo.setNumber(number);
            openData.infoRequest(truckInfo);
            if (truckInfo.getVin() != null || truckInfo.getModel() != null) {
                dao.save(truckInfo);
            }
        }

        return truckInfo;
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
