package utils.notifications.preparers;

import entity.DealType;
import entity.transport.Transportation;
import utils.LanguageBase;
import utils.hibernate.dao.NotificationDAO;
import utils.notifications.NotificationPreparer;

public class TimeInPreparer extends TransportTimePreparer {

    private static final String TRANSPORT_INTO = "bot.transport.into";
    private final Transportation transportation;
    private final LanguageBase languageBase;

    public TimeInPreparer(Transportation transportation, LanguageBase languageBase, NotificationDAO notificationDAO) {
        super(languageBase, notificationDAO);
        this.transportation = transportation;
        this.languageBase = languageBase;
    }

    @Override
    public String prepareMessage(String lang) {
        return prepareMessage(transportation, lang, languageBase.get(TRANSPORT_INTO));
    }
}
