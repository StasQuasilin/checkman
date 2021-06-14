package utils.notifications.preparers;

import entity.DealType;
import entity.transport.Transportation;
import utils.LanguageBase;
import utils.hibernate.dao.NotificationDAO;


public class RegistrationPreparer extends TransportTimePreparer {

    private static final String TRANSPORT_REGISTRATION = "bot.transport.registration";
    private final Transportation transportation;
    private final LanguageBase lb;

    public RegistrationPreparer(Transportation transportation, LanguageBase languageBase, NotificationDAO dao) {
        super(languageBase, dao);
        this.transportation = transportation;
        lb = languageBase;
    }

    @Override
    public String prepareMessage(String lang) {
        return prepareMessage(transportation, lang, lb.get(lang, TRANSPORT_REGISTRATION));
    }
}
