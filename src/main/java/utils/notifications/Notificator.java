package utils.notifications;

import entity.bot.NotifyStatus;
import entity.bot.UserNotificationSetting;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import utils.LanguageBase;
import utils.hibernate.DateContainers.OR;
import utils.hibernate.dao.NotificationDAO;
import utils.notifications.preparers.RegistrationPreparer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static constants.Constants.SHOW;
import static constants.Constants.TRANSPORT;

public class Notificator {
    public static final Logger logger = Logger.getLogger(Notificator.class);
    private static final LinkedList<INotifier> notifiers = new LinkedList<>();
    private static final NotificationDAO dao = new NotificationDAO();
    private static final LanguageBase languageBase = LanguageBase.getBase();

    public static void addNotificator(INotifier notifier){
        notifiers.add(notifier);
    }

    private static final HashMap<String, Object> transportArgs = new HashMap<>();
    static {
        transportArgs.put(SHOW, true);
        transportArgs.put(TRANSPORT, new OR(NotifyStatus.all, NotifyStatus.my));
    }

    public static void transportRegistration(Transportation transportation) {
        send(transportArgs, new RegistrationPreparer(transportation, languageBase, dao));
    }

    private static void send(HashMap<String, Object> args, NotificationPreparer preparer) {
        if (notifiers.size() > 0) {
            final List<UserNotificationSetting> settingList = getNotificationSettings(args);
            if (settingList.size() > 0){
                final HashMap<String, String> messages = new HashMap<>();
                for (UserNotificationSetting setting : settingList){
                    final String language = languageBase.getLanguage(setting.getLanguage());
                    if (!messages.containsKey(language)){
                        messages.put(language, preparer.prepareMessage(language));
                    }
                    sendNotification(messages.get(language), setting.getTelegramId());
                }
            }

        }
    }

    private static void sendNotification(String msg, long chatId) {
        for (INotifier notificator : notifiers){
            notificator.sendMessage(chatId, msg);
        }
    }

    private static List<UserNotificationSetting> getNotificationSettings(HashMap<String, Object> args) {
        return dao.getSettings(args);
    }
}
