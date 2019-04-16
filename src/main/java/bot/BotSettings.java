package bot;

import entity.bot.UserBotSetting;
import utils.boxes.IBox;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class BotSettings extends IBox {

    private static final BotSettings instance = new BotSettings();
    private BotSettings(){}

    public static BotSettings getInstance() {
        return instance;
    }

    public void save(UserBotSetting botSetting) {
        HIBERNATOR.save(botSetting);
    }
}
