package utils;

import entity.ApplicationSettings;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

/**
 * Created by quasilin on 18.03.2019.
 */
public class ApplicationSettingsBox {

    dbDAO dao = dbDAOService.getDAO();

    private static final ApplicationSettingsBox box = new ApplicationSettingsBox();

    private ApplicationSettings settings;

    public ApplicationSettingsBox() {
        initSettings();
    }

    public static ApplicationSettingsBox getBox() {
        return box;
    }

    void initSettings(){
        settings = dao.getApplicationSettings();
    }

    public ApplicationSettings getSettings() {
        return settings;
    }
}
