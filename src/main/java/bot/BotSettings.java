package bot;

import entity.Admin;
import entity.bot.UserBotSetting;
import utils.boxes.IBox;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class BotSettings extends IBox {

    private static final BotSettings instance = new BotSettings();
    private BotSettings(){
        hibernator.query(UserBotSetting.class, null).forEach(this::addSettings);
    }

    public static BotSettings getInstance() {
        return instance;
    }

    final HashMap<Long, UserBotSetting> botSettingHashMap = new HashMap<>();

    void addSettings(UserBotSetting setting){
        botSettingHashMap.put(setting.getTelegramId(), setting);
    }

    public void save(UserBotSetting botSetting) {
        hibernator.save(botSetting);
        addSettings(botSetting);
    }

    public boolean contain(long id) {
        return botSettingHashMap.containsKey(id);
    }

    public UserBotSetting getSettings(long id) {
        return botSettingHashMap.get(id);
    }

    public Collection<UserBotSetting> get() {
        return botSettingHashMap.values();
    }

    public Admin getAdmin() {
        List<Admin> admins = hibernator.query(Admin.class, null);
        if (admins.size() == 1){
            return admins.get(0);
        } else if (admins.size() > 0){
            for (Admin admin : admins) {
                if (admin.isDuty()){
                    return admin;
                }
            }
        }

        return null;
    }
}
