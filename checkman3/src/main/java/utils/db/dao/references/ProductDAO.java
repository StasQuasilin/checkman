package utils.db.dao.references;

import entity.references.Product;

import java.util.List;

public interface ProductDAO {
    Product getProductById(Object id);

    void save(Product product);

    List<Product> getProducts();
}
