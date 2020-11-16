package ua.svasilina.spedition.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

import ua.svasilina.spedition.entity.Product;
import ua.svasilina.spedition.utils.db.DBHelper;
import ua.svasilina.spedition.utils.db.Tables;

public class ProductsUtil {

    SQLiteDatabase db;

    public ProductsUtil(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public LinkedList<Product> getProducts(){
        LinkedList<Product> products = new LinkedList<>();
        final Cursor query = db.query(Tables.PRODUCTS, null, null, null, null, null, null);
        if (query.moveToFirst()){

            final int serverIdIdx = query.getColumnIndex("server_id");
            final int nameIdx = query.getColumnIndex("name");

            do {
                final int serverId = query.getInt(serverIdIdx);
                final String name = query.getString(nameIdx);
                products.add(new Product(serverId, name));

            } while (query.moveToNext());
        }
        if (products.size() == 0){
            products.add(new Product(1, "- Олія раф"));
            products.add(new Product(2, "- Олія нераф"));
            products.add(new Product(3, "- Готова продукція"));
            products.add(new Product(4, "- Рафінація"));
        }
        return products;
    }



    public LinkedList<Product> getChildren(Product product){
        LinkedList<Product> products = new LinkedList<>();
        products.add(product);
        return products;
    }

    public Product getProduct(int productId) {
        final Cursor query = db.query(Tables.PRODUCTS, null, "server_id=?", new String[]{String.valueOf(productId)}, null, null, null);
        if(query.moveToFirst()){
            final int serverIdIdx = query.getColumnIndex("server_id");
            final int nameIdx = query.getColumnIndex("name");
            final int serverId = query.getInt(serverIdIdx);
            final String name = query.getString(nameIdx);
            return new Product(serverId, name);
        }
        return null;
    }
}
