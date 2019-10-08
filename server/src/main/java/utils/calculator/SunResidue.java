package utils.calculator;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class SunResidue extends AbstractCalculator<Float>{

    private static final String KEY = "vOJpfvOC60";
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
    public void writeValue(Float value) {

    }

    @Override
    public Float readValue() {
        return null;
    }
}
