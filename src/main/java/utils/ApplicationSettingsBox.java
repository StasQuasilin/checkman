package utils;

import entity.ApplicationSettings;
import utils.hibernate.Hibernator;

/**
 * Created by quasilin on 18.03.2019.
 */
public class ApplicationSettingsBox {

    private final Hibernator hibernator = Hibernator.getInstance();

    private static final ApplicationSettingsBox box = new ApplicationSettingsBox();

    private ApplicationSettings settings;

    public ApplicationSettingsBox() {
        initSettings();
    }

    public static ApplicationSettingsBox getBox() {
        return box;
    }

    void initSettings(){
        settings = hibernator.get(ApplicationSettings.class, null);
    }

    public ApplicationSettings getSettings() {
        return settings;
    }
}
