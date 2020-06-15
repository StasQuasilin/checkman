package ua.svasilina.spedition.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import ua.svasilina.spedition.entity.Product;

public class ProductsUtil {

    private static final ArrayList<Product> products = new ArrayList<>();
    private static final HashMap<Product, ArrayList<Product>> children = new HashMap<>();
    static {
        final Product product1 = new Product(1, "Олія раф");
        final Product product2 = new Product(2, "Олія нераф");
        final Product product3 = new Product(3, "Готова продукція");
        final Product product4 = new Product(4, "Рафінація");
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        children.put(product1, new ArrayList<>(Collections.singleton(product1)));
        children.put(product2, new ArrayList<>(Collections.singleton(product2)));
        children.put(product3, new ArrayList<>(Collections.singleton(product3)));
        children.put(product4, new ArrayList<>(Arrays.asList(product1, product2)));
    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    public ArrayList<Product> getChildren(Product product){
        return children.get(product);
    }

    public Product getProduct(int productId) {
        for(Product product : products){
            if (productId == product.getId()){
                return product;
            }
        }
        return null;
    }
}
