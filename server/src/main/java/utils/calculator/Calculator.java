package utils.calculator;

import utils.LanguageBase;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public abstract class Calculator {

    public static final HashMap<String, Calculator> calculators = new HashMap<>();
    static {
        calculators.put(SunResidue.KEY, new SunResidue());
    }

    public static Collection<Calculator> calculatorList(){
        return calculators.values();
    }

    dbDAO dao = dbDAOService.getDAO();
    LanguageBase lang = LanguageBase.getBase();

    public abstract String getKey();
    public abstract String getTitle();
    public abstract String getDescription();
    public abstract float readValue();
}
