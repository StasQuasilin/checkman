package bot;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public abstract class IBot extends ILongPollingBot {
    protected Notificator notificator;

    public Notificator getNotificator() {
        return notificator;
    }
}
