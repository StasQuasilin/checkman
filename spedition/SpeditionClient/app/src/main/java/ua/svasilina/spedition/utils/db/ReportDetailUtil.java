package ua.svasilina.spedition.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.UUID;

import ua.svasilina.spedition.constants.Keys;
import ua.svasilina.spedition.entity.Driver;
import ua.svasilina.spedition.entity.ReportDetail;
import ua.svasilina.spedition.entity.reports.Report;
import ua.svasilina.spedition.entity.reports.SimpleReport;

public class ReportDetailUtil {

    private static final String ID_COLUMN = "id";
    private static final String DRIVER_COLUMN = "driver";
    private static final String REPORT_COLUMN = "report";
    private static final String ONE_ROW = "1";
    private final SQLiteDatabase db;
    private final DriverUtil driverUtil;

    public ReportDetailUtil(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        driverUtil = new DriverUtil(context);
    }

    public void getDetails(Report report){
        final String id = report.getUuid();
        final LinkedList<ReportDetail> details = report.getDetails();
        getDetails(id, details);
    }

    public void getDetails(String reportId, LinkedList<ReportDetail> details){
        final Cursor query = db.query(Tables.REPORT_DETAILS, null, "report=?", new String[]{reportId}, null, null, null);
        if (query.moveToFirst()){
            final int idColumn = query.getColumnIndex(ID_COLUMN);
            final int uuidColumn = query.getColumnIndex(Keys.UUID);
            final int driverColumn = query.getColumnIndex(DRIVER_COLUMN);
            do{
                ReportDetail detail = new ReportDetail();
                detail.setId(query.getInt(idColumn));
                detail.setUuid(query.getString(uuidColumn));

                final int driverId = query.getInt(driverColumn);
                final Driver driver = driverUtil.getDriver(driverId);
                if (driver != null) {
                    detail.setDriver(driver);
                }
                details.add(detail);
            }while (query.moveToNext());
        }
    }

    public void saveDetail(ReportDetail detail, Report report){
        final ContentValues cv = new ContentValues();

        final String id = String.valueOf(detail.getId());
        cv.put(REPORT_COLUMN, report.getUuid());
        String uuid = detail.getUuid();
        if(uuid == null){
            uuid = UUID.randomUUID().toString();
            detail.setUuid(uuid);
        }
        cv.put(Keys.UUID, uuid);
        final Driver driver = detail.getDriver();
        if(driver != null){
            System.out.println("Detail driver " + driver.getId() + ":" + driver.getValue());
            driverUtil.saveDriver(driver);
            cv.put(DRIVER_COLUMN, driver.getId());
        } else {
            System.out.println("--------> Detail without driver");
        }

        final Cursor query = db.query(Tables.REPORT_DETAILS, null, "id = ?", new String[]{id}, null, null, null, ONE_ROW);
        if(query.moveToFirst()){
            db.update(Tables.REPORT_DETAILS, cv, "id = ?", new String[]{id});
        } else {
            final long insert = db.insert(Tables.REPORT_DETAILS, null, cv);
            detail.setId(insert);
        }
    }

    public void saveDetails(Report report) {
        final LinkedList<ReportDetail> details = new LinkedList<>();
        getDetails(report.getUuid(), details);

        for (ReportDetail detail : report.getDetails()){
            details.remove(detail);
            saveDetail(detail, report);
        }

        for (ReportDetail detail : details){
            removeDetail(detail);
        }
    }

    private void removeDetail(ReportDetail detail) {
        final String id = String.valueOf(detail.getId());
        db.delete(Tables.REPORT_DETAILS, "id=?", new String[]{id});
    }

    public void getDetails(SimpleReport simpleReport) {
        final Cursor query = db.query(Tables.REPORT_DETAILS, new String[]{DRIVER_COLUMN}, "report=?", new String[]{simpleReport.getUuid()}, null, null, null);
        if (query.moveToFirst()){
            final int driverColumn = query.getColumnIndex(DRIVER_COLUMN);

            do{
                final int driverId = query.getInt(driverColumn);
                final Driver driver = driverUtil.getDriver(driverId);
                simpleReport.addDriver(driver);
            }while (query.moveToNext());
        }
    }
}
