package utils;

import org.apache.log4j.Logger;
import sun.misc.PostVMInitHook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class LanguageBase{

    static LanguageBase base = new LanguageBase();

    final String[] languages = {"ru"};
    public final String defLang = languages[0];
    final String baseName = "messages_";
    public static LanguageBase getBase() {
        return base;
    }

    final private HashMap<String, ResourceBundle> BUNDLES = new HashMap<>();

    public LanguageBase() {
        for (String l : languages){
            String resourceName = baseName + l;
            BUNDLES.put(l, ResourceBundle.getBundle(resourceName));
        }
    }

    public String get(String key){
        return get(defLang, key);
    }
    public String get(String language, String key){
        if (language == null){
            language = defLang;
        }
        try {
            return ResourceBundle.getBundle(baseName + language).getString(key);
        } catch (MissingResourceException e){
            return "%" + key + "%";
        }
    }
}
