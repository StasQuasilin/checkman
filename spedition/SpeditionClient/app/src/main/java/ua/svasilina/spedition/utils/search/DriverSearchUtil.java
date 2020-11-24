package ua.svasilina.spedition.utils.search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

import ua.svasilina.spedition.constants.Keys;
import ua.svasilina.spedition.entity.Driver;
import ua.svasilina.spedition.entity.Person;
import ua.svasilina.spedition.utils.db.DBHelper;
import ua.svasilina.spedition.utils.db.Tables;

public class DriverSearchUtil extends SearchUtil<Driver> {

    DBHelper helper;
    final SQLiteDatabase database;

    public DriverSearchUtil(Context context) {
        helper = new DBHelper( context);
        database = helper.getReadableDatabase();
    }

    @Override
    LinkedList<Driver> findItems(String key) {
        LinkedList<Driver> drivers = new LinkedList<>();
        final String k = "%" + key + "%";
        final Cursor query = database.query(Tables.DRIVERS, null, "surname like ? or forename like ?", new String[]{k, k}, null, null, null);
        if (query.moveToFirst()){
            final int idIdx = query.getColumnIndex(Keys.ID);
            final int surnameIdx = query.getColumnIndex(Keys.SURNAME);
            final int forenameIdx = query.getColumnIndex(Keys.FORENAME);

            do {
                final int id = query.getInt(idIdx);
                final String surname = query.getString(surnameIdx);
                final String forename = query.getString(forenameIdx);
                Driver driver = new Driver();
                driver.setId(id);
                final Person person = driver.getPerson();
                person.setSurname(surname);
                person.setForename(forename);
                drivers.add(driver);

            } while (query.moveToNext());
        }
        return drivers;
    }
}
