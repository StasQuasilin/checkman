package utils.calculator;

import utils.LanguageBase;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public abstract class AbstractCalculator<T> {

    dbDAO dao = dbDAOService.getDAO();
    LanguageBase lang = LanguageBase.getBase();
    public abstract String getKey();
    public abstract String getTitle();
    public abstract void writeValue(T value);
    public abstract T readValue();
}
