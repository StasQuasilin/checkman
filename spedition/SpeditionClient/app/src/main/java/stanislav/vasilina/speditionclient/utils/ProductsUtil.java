package stanislav.vasilina.speditionclient.utils;

import java.util.ArrayList;

import stanislav.vasilina.speditionclient.entity.Product;

public class ProductsUtil {

    private final ArrayList<Product> products;

    public ProductsUtil() {
        products = new ArrayList<>();
        products.add(new Product(1, "Олія раф"));
        products.add(new Product(2, "Олія нераф"));
        products.add(new Product(3, "Готова продукція"));
    }

    public ArrayList<Product> getProducts(){
        return products;
    }
}
