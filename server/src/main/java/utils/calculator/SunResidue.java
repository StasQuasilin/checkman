package utils.calculator;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class SunResidue extends Calculator {

    public static final String KEY = "vOJpfvOC60";
    private static final String TITLE = "calculator.sun.resuide";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTitle() {
        return lang.get(TITLE);
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public float readValue() {
        return 0;
    }
}
