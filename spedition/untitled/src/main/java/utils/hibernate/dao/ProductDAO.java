package utils.hibernate.dao;

import entity.Product;
import utils.hibernate.Hibernator;

import static constants.Keys.ID;

public class ProductDAO {
    private static final Hibernator hibernator = Hibernator.getInstance();

    public Product getProduct(Object id) {
        return hibernator.get(Product.class, ID, id);
    }
}
