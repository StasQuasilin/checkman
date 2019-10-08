package utils.storages;

import entity.products.Product;
import entity.storages.Storage;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class StorageUtil {
    private final dbDAO dao = dbDAOService.getDAO();

    public void readValues(Storage storage, Product product, LocalDate date){
        readValues(storage, product, date, date);
    }

    public void readValues(Storage storage, Product product, LocalDate from, LocalDate to){
        readValues(storage, product, Date.valueOf(from), Date.valueOf(to));
    }

    public void readValues(Storage storage, Product product, Date from, Date to){

    }

}
