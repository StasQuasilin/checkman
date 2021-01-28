package api.laboratory;

import entity.laboratory.transportation.ActNumber;
import entity.laboratory.transportation.ActType;
import utils.boxes.IBox;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

/**
 * Created by szpt_user045 on 12.06.2019.
 */
public class ActNumberService {

    static dbDAO dao = dbDAOService.getDAO();

    public static void updateNumber(ActType type, int number){
        ActNumber actNumber = dao.getActNumber(type);
        actNumber.setNumber(number);
        dao.save(actNumber);
    }

    public static int getActNumber(ActType type) {
        return dao.getActNumberIncrement(type);
    }
}
