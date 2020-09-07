package utils.db.dao.references;

import entity.references.Product;
import utils.db.hibernate.Hibernator;

import java.util.List;

import static constants.Keys.ID;

public class ProductDAOHibernate implements ProductDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public Product getProductById(Object id) {
        return hibernator.get(Product.class, ID, id);
    }

    @Override
    public void save(Product product) {
        hibernator.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return hibernator.query(Product.class, null);
    }
}
