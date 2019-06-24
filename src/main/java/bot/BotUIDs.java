package bot;

import entity.Worker;
import utils.PasswordGenerator;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.HashMap;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class BotUIDs {

    dbDAO dao = dbDAOService.getDAO();

    private static final BotUIDs BOT_UI_DS = new BotUIDs();

    private BotUIDs() {}

    public static BotUIDs getBox() {
        return BOT_UI_DS;
    }

    public BotUID generateToken(Worker worker){
        BotUID botUID = dao.getBotUidByWorker(worker);

        if (botUID != null && botUID.isOld()){
            dao.remove(botUID);
            botUID = null;
        }

        if(botUID == null) {
            botUID = new BotUID(randomUID(), worker);
            dao.save(botUID);
        }

        return botUID;
    }

    String randomUID(){
        String uid = PasswordGenerator.getPassword(6);
        if (dao.getBotUidByUid(uid) != null){
            return randomUID();
        }
        return uid;
    }

    public BotUID getUID(String token) {
        BotUID botUID = dao.getBotUidByUid(token);
        if (botUID != null && botUID.isOld()){
            dao.remove(botUID);
        }
        return botUID;
    }
}
