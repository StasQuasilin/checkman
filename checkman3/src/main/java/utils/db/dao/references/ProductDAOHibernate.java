package utils.db.dao.references;

import entity.references.Product;
import utils.db.hibernate.Hibernator;

import java.util.LinkedList;
import java.util.List;

import static constants.Keys.ID;

public class ProductDAOHibernate implements ProductDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final LinkedList<Product> mainProducts = new LinkedList<>();

    @Override
    public Product getProductById(Object id) {
        return hibernator.get(Product.class, ID, id);
    }

    @Override
    public void save(Product product) {
        hibernator.save(product);
        mainProducts.remove(product);
        if (product.isMain()){
            mainProducts.add(product);
        }
    }

    @Override
    public synchronized List<Product> getProducts() {
        if (mainProducts.isEmpty()){
            mainProducts.addAll(hibernator.query(Product.class, "main", true));
        }
        return mainProducts;
    }
}
