package bot;

import java.util.ArrayList;

/**
 * Created by szpt_user045 on 27.12.2019.
 */
public class Notificator{
    private final ArrayList<INotificator> notificatorList = new ArrayList<>();
    private final static Notificator instance = new Notificator();

    public static Notificator getInstance() {
        return instance;
    }

    public void registerBot(IBot bot){
        notificatorList.add(bot.getNotificator());
    }

    private void sendMessage(String message){
        for (INotificator notificator : notificatorList){
//            notificator.sendMessage(message);
        }
    }
}
