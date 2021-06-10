package utils.hibernate.dao;

import entity.DealType;
import entity.bot.UserNotificationSetting;
import entity.products.Product;
import entity.products.ProductProperty;
import entity.transport.TransportationProduct;

import java.util.HashMap;
import java.util.List;

import static constants.Constants.*;

public class NotificationDAO extends HibernateDAO{
    public List<UserNotificationSetting> getSettings(HashMap<String, Object> args) {
        return hibernator.query(UserNotificationSetting.class, args);
    }

    public String getProductProperty(Product product, String key) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(PRODUCT, product);
        args.put(KEY, key);
        final ProductProperty property = hibernator.get(ProductProperty.class, args);
        if (property != null){
            return property.getValue().toLowerCase();
        } else {
            return product.getName().toLowerCase();
        }
    }
}
