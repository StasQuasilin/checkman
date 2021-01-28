package utils;

import java.util.ResourceBundle;

public class LanguageBase{

    static LanguageBase base = new LanguageBase();

    public static final String[] LANGUAGES = {"ua", "ru"};
    public static final String DEFAULT_LANGUAGE = LANGUAGES[0];
    final static String BASE_NAME = "messages_";
    public static LanguageBase getBase() {
        return base;
    }

    public String get(String key){
        return get(DEFAULT_LANGUAGE, key);
    }
    public String get(String language, String key){
        if (language == null){
            language = DEFAULT_LANGUAGE;
        }
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME + language);
        if (bundle.containsKey(key)){
            return bundle.getString(key);
        } else {
            return "???" + key + "???";
        }
    }
}
