package utils.calculator;

import entity.products.Product;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class ProductLoadCalculator extends AbstractCalculator<Float> {

    private static final String KEY = "S3P36nIiix";
    private static final String TITLE = "calculator.load.products";

    private final Product product;

    public ProductLoadCalculator(Product product) {
        this.product = product;
    }

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
