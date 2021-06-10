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
    public boolean hasLanguage(String language){
        return language.contains(language);
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

    public String getLanguage(String language) {
        if (hasLanguage(language)){
            return language;
        }
        return DEFAULT_LANGUAGE;
    }
}
