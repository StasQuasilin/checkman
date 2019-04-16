package bot;

import entity.Worker;
import utils.PasswordGenerator;
import utils.hibernate.Hibernator;

import java.util.HashMap;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class BotUIDs {
    private static final BotUIDs BOT_UI_DS = new BotUIDs();
    private final Hibernator hibernator = Hibernator.getInstance();

    private BotUIDs() {}

    public static BotUIDs getBox() {
        return BOT_UI_DS;
    }

    public BotUID generateToken(Worker worker){
        BotUID botUID = hibernator.get(BotUID.class, "worker", worker);

        if (botUID != null && botUID.isOld()){
            hibernator.remove(botUID);
            botUID = null;
        }

        if(botUID == null) {
            botUID = new BotUID(randomUID(), worker);
            hibernator.save(botUID);
        }

        return botUID;
    }

    String randomUID(){
        String uid = PasswordGenerator.getPassword(6);
        if (hibernator.query(BotUID.class, "uid", uid).size() > 0){
            return randomUID();
        }
        return uid;
    }

    public BotUID getUID(String token) {
        BotUID botUID = hibernator.get(BotUID.class, "uid", token);
        if (botUID != null && botUID.isOld()){
            hibernator.remove(botUID);
        }
        return botUID;
    }
}
