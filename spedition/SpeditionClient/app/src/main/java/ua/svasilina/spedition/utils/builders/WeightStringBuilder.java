package ua.svasilina.spedition.utils.builders;

import android.content.res.Resources;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.entity.Weight;

import static ua.svasilina.spedition.constants.Keys.COMA;
import static ua.svasilina.spedition.constants.Keys.SPACE;

public class WeightStringBuilder {
    private final Resources resources;

    public WeightStringBuilder(Resources resources) {
        this.resources = resources;
    }

    public String build(Weight weight) {
        return resources.getString(R.string.B) +
                SPACE +
                weight.getGross() +
                COMA + SPACE +
                resources.getString(R.string.T) +
                SPACE +
                weight.getTare() +
                COMA + SPACE +
                resources.getString(R.string.N) +
                SPACE +
                weight.getNet();
    }
}
