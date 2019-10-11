package utils.calculator;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class ProductResidue extends Calculator {

    public static final String KEY = "vOJpfvOC60";
    private static final String TITLE = "calculator.resuide.title";
    private static final String DESCRIPTION = "calculator.resuide.description";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public float readValue() {
        return 0;
    }
}
