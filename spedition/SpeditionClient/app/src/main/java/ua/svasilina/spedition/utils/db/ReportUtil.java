package ua.svasilina.spedition.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;

import ua.svasilina.spedition.constants.Keys;
import ua.svasilina.spedition.entity.Product;
import ua.svasilina.spedition.entity.reports.Report;
import ua.svasilina.spedition.entity.reports.SimpleReport;
import ua.svasilina.spedition.utils.ProductsUtil;
import ua.svasilina.spedition.utils.sync.SyncUtil;

public class ReportUtil {

    private static final String ONE_ROW = "1";
    private static final String SYNC_COLUMN = "is_sync";
    private final SQLiteDatabase db;
    private static final String ID_COLUMN = "id";
    private static final String LEAVE_COLUMN = "leave_time";
    private static final String DONE_COLUMN = "done_time";
    private static final String ROUTE_COLUMN = "route";
    private static final String PRODUCT_COLUMN = "product";
    private static final String SEPARATED_PRODUCT_COLUMN = "separated_products";
    private final String[] simpleReportFields = new String[]{
            ID_COLUMN, LEAVE_COLUMN, DONE_COLUMN, ROUTE_COLUMN, PRODUCT_COLUMN, SEPARATED_PRODUCT_COLUMN
    };
    private final ProductsUtil productsUtil;
    private final ReportDetailUtil detailUtil;
    private final SyncUtil syncUtil;
    private final DBHelper dbHelper;
    public ReportUtil(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        productsUtil = new ProductsUtil(context);
        detailUtil = new ReportDetailUtil(context);
        syncUtil = new SyncUtil(context, this);
    }

    public LinkedList<SimpleReport> getReportsList(){
        final LinkedList<SimpleReport> reports = new LinkedList<>();
        final SQLiteDatabase database = dbHelper.getReadableDatabase();
        final Cursor query = database.query(Tables.REPORTS, simpleReportFields, null, null, null, null, null);
        if(query.moveToFirst()){

            final int idColumn = query.getColumnIndex(ID_COLUMN);
            final int leaveColumn = query.getColumnIndex(LEAVE_COLUMN);
            final int doneColumn = query.getColumnIndex(DONE_COLUMN);
            final int routeColumn = query.getColumnIndex(ROUTE_COLUMN);
            final int productColumn = query.getColumnIndex(PRODUCT_COLUMN);
            do {
                SimpleReport simpleReport = new SimpleReport();
                simpleReport.setId(query.getInt(idColumn));
                final long leaveTime = query.getLong(leaveColumn);
                if (leaveTime > 0) {
                    simpleReport.setLeaveTime(leaveTime);
                }
                final long doneTime = query.getLong(doneColumn);
                if(doneTime > 0){
                    simpleReport.setDoneTime(doneTime);
                }

                simpleReport.setRoute(query.getString(routeColumn));
                final int productId = query.getInt(productColumn);
                if (productId > 0) {
                    simpleReport.addProduct(productsUtil.getProduct(productId));
                }
                detailUtil.getDetails(simpleReport);


                reports.add(simpleReport);
            } while (query.moveToNext());
        }
        database.close();
        Collections.sort(reports);
        return reports;
    }
    public Report getReport(int id){
        Log.i("Open report", String.valueOf(id));
        final Cursor query = db.query(Tables.REPORTS, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null, String.valueOf(1));
        if (query.moveToFirst()){
            return readReport(query);
        }

        return null;
    }

    public void saveReport(Report report) {
        final ContentValues cv = new ContentValues();

        final Calendar leaveTime = report.getLeaveTime();
        if (leaveTime != null) {
            cv.put(LEAVE_COLUMN, leaveTime.getTimeInMillis());
        }
        final Calendar doneDate = report.getDoneDate();
        if(doneDate != null){
            cv.put(DONE_COLUMN, doneDate.getTimeInMillis());
        }
        final Product product = report.getProduct();
        if(product != null){
            cv.put(PRODUCT_COLUMN, product.getId());
        }
        final String route = report.getRouteString();
        cv.put(ROUTE_COLUMN, route);
        cv.put(SYNC_COLUMN, false);

        final String id = String.valueOf(report.getId());
        final Cursor query = db.query(Tables.REPORTS, new String[]{Keys.ID}, "id=?", new String[]{id}, null, null, null, ONE_ROW);
        if (query.moveToFirst()){
            db.update(Tables.REPORTS, cv, "id=?", new String[]{id});
        } else {
            db.insert(Tables.REPORTS, null, cv);
        }
        detailUtil.saveDetails(report);
        syncUtil.saveThread(report);

    }

    public boolean removeReport(int id) {
        String[] par = new String[]{String.valueOf(id)};
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final int delete = database.delete(Tables.REPORTS, "id=?", par);
        db.delete(Tables.REPORT_DETAILS, "report=?", par);
        db.delete(Tables.REPORT_FIELDS, "report=?", par);
        db.delete(Tables.REPORT_NOTES, "report=?", par);
        if (delete > 0) {
            final ContentValues cv = new ContentValues();
            cv.put(ID_COLUMN, id);
            database.insert(Tables.REMOVE_REPORTS, null, cv);
        }
        return delete > 0;
    }

    public void makeSync(int localId, int serverId) {
        final ContentValues cv = new ContentValues();
        cv.put(Keys.SERVER_ID, serverId);
        cv.put(SYNC_COLUMN, true);
        
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.update(Tables.REPORTS, cv, "id=?", new String[]{String.valueOf(localId)});
    }

    public LinkedList<Report> getUnSyncReports() {
        final LinkedList<Report> reports = new LinkedList<>();
        final SQLiteDatabase database = dbHelper.getReadableDatabase();
        final Cursor query = database.query(Tables.REPORTS, null, SYNC_COLUMN + "=?", new String[]{String.valueOf(false)}, null, null, null);
        if (query.moveToFirst()){
            reports.addAll(readReports(query));
        }
        return reports;
    }

    private LinkedList<Report> readReports(Cursor query) {
        final LinkedList<Report> reports = new LinkedList<>();
        do{
            reports.add(readReport(query));
        }while (query.moveToNext());
        return reports;
    }

    private Report readReport(Cursor query) {
        final int idColumn = query.getColumnIndex(ID_COLUMN);
        final int leaveColumn = query.getColumnIndex(LEAVE_COLUMN);
        final int doneColumn = query.getColumnIndex(DONE_COLUMN);
        final int routeColumn = query.getColumnIndex(ROUTE_COLUMN);
        final int productColumn = query.getColumnIndex(PRODUCT_COLUMN);

        Report report = new Report();

        report.setId(query.getInt(idColumn));

        final long leaveTime = query.getLong(leaveColumn);
        if (leaveTime > 0){
            report.setLeaveTime(leaveTime);
        }
        final long doneTime = query.getLong(doneColumn);
        if (doneTime > 0){
            report.setDoneTime(doneTime);
        }

        report.setRoute(query.getString(routeColumn));
        final int productId = query.getInt(productColumn);
        final Product product = productsUtil.getProduct(productId);
        report.setProduct(product);

        detailUtil.getDetails(report);

        return report;
    }

    public String[] getRemoved() {
        final SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] result;
        final Cursor query = database.query(Tables.REMOVE_REPORTS, null, null, null, null, null, null);
        if (query.moveToFirst()){
            result = new String[query.getCount()];
            int i = 0;
            final int idColumn = query.getColumnIndex(ID_COLUMN);
            do {
                result[i] = query.getString(idColumn);
                i++;
            } while (query.moveToNext());
        } else {
            result = new String[0];
        }
        database.close();
        return result;
    }

    public void forgotAbout(String uuid) {
        final SQLiteDatabase da = dbHelper.getWritableDatabase();
        da.delete(Tables.REMOVE_REPORTS, "id=?", new String[]{uuid});
        da.close();
    }
}
