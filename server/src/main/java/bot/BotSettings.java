package bot;

import entity.Admin;
import entity.bot.UserBotSetting;
import utils.boxes.IBox;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class BotSettings {

    dbDAO dao = dbDAOService.getDAO();

    private static final BotSettings instance = new BotSettings();

    private BotSettings(){
        dao.getUserBotSettings().forEach(this::addSettings);
    }

    public static BotSettings getInstance() {
        return instance;
    }

    final HashMap<Long, UserBotSetting> botSettingHashMap = new HashMap<>();

    void addSettings(UserBotSetting setting){
        botSettingHashMap.put(setting.getTelegramId(), setting);
    }

    public void save(UserBotSetting botSetting) {
        dao.saveUserBotSetting(botSetting);
        addSettings(botSetting);
    }

    public boolean contain(long id) {
        return botSettingHashMap.containsKey(id);
    }

    public UserBotSetting getSettings(long telegramId) {
        return botSettingHashMap.get(telegramId);
    }

    public Collection<UserBotSetting> get() {
        return botSettingHashMap.values();
    }

    public Admin getAdmin() {
        List<Admin> admins = dao.getAdminList();
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
