package ua.svasilina.spedition.entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ua.svasilina.spedition.entity.changes.IChangeComparable;

import static ua.svasilina.spedition.constants.Keys.DONE;
import static ua.svasilina.spedition.constants.Keys.DRIVER;
import static ua.svasilina.spedition.constants.Keys.EXPENSES;
import static ua.svasilina.spedition.constants.Keys.FARES;
import static ua.svasilina.spedition.constants.Keys.FIELDS;
import static ua.svasilina.spedition.constants.Keys.FONE;
import static ua.svasilina.spedition.constants.Keys.ID;
import static ua.svasilina.spedition.constants.Keys.LEAVE;
import static ua.svasilina.spedition.constants.Keys.NOTES;
import static ua.svasilina.spedition.constants.Keys.PER_DIEM;
import static ua.svasilina.spedition.constants.Keys.PRODUCT;
import static ua.svasilina.spedition.constants.Keys.ROUTE;
import static ua.svasilina.spedition.constants.Keys.SYNC;

public class Report extends JsonAble implements Serializable, Comparable<Report>, IChangeComparable {

    private String uuid;
    public Calendar leaveTime;
    public Calendar doneDate;
    public Driver driver;
    public Route route;
    public Product product;
    public int fare;
    private int expensesSum;
    public int perDiem;
    final public ArrayList<ReportField> fields = new ArrayList<>();
    final public ArrayList<Expense> expenses = new ArrayList<>();
    final public ArrayList<Expense> fares = new ArrayList<>();
    final public ArrayList<ReportNote> notes = new ArrayList<>();
    public boolean done;
    private boolean sync;
    public boolean fone;

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Calendar getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(Calendar leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Calendar getDoneDate() {
        return doneDate;
    }
    public void setDoneDate(Calendar doneDate) {
        this.doneDate = doneDate;
    }

    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Route getRoute() {
        return route;
    }
    public void setRoute(Route route) {
        this.route = route;
    }

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getFare() {
        return fare;
    }
    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getExpensesSum() {
        return expensesSum;
    }
    public void setExpensesSum(int expensesSum) {
        this.expensesSum = expensesSum;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
    public ArrayList<ReportNote> getNotes() {
        return notes;
    }
    public ArrayList<Expense> getFares() {
        return fares;
    }

    public int getPerDiem() {
        return perDiem;
    }
    public void setPerDiem(int perDiem) {
        this.perDiem = perDiem;
    }

    public boolean isFone() {
        return fone;
    }
    public void setFone(boolean fone) {
        this.fone = fone;
    }

    public boolean isSync() {
        return sync;
    }
    public void setSync(boolean sync) {
        this.sync = sync;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(ID, uuid);
        if (leaveTime != null) {
            json.put(LEAVE, leaveTime.getTimeInMillis());
        }
        if(doneDate != null){
            json.put(DONE, doneDate.getTimeInMillis());
        }
        if (driver != null){
            json.put(DRIVER, driver.toJson());
        }
        if (route != null){
            json.put(ROUTE, route.toJson());
        }
        if (product != null){
            json.put(PRODUCT, product.getId());
        }
        json.put(FIELDS, fields());
        json.put(FARES, fare());
        json.put(EXPENSES, expenses());
        json.put(PER_DIEM, perDiem);
        json.put(FONE, fone);
        json.put(SYNC, sync);
        json.put(NOTES, notes());

        return json;
    }

    private JSONArray fare() {
        JSONArray array = new JSONArray();
        for (Expense fare : fares){
            array.add(fare.toJson());
        }
        return array;
    }

    private JSONArray notes() {
        JSONArray array = new JSONArray();
        for (ReportNote note : notes){
            array.add(note.toJson());
        }
        return array;
    }

    private JSONArray expenses() {
        JSONArray array = new JSONArray();
        for (Expense expense : expenses){
            array.add(expense.toJson());
        }
        return array;
    }

    private JSONArray fields() {
        JSONArray array = new JSONArray();
        for (ReportField field : fields){
            array.add(field.toJson());
        }
        return array;
    }

    public List<ReportField> getFields() {
        return fields;
    }

    @Override
    public int compareTo(Report o) {
        if (leaveTime == null){
            return -1;
        } else if (o == null || o.leaveTime == null){
            return 1;
        } else {
            return o.leaveTime.compareTo(leaveTime);
        }
    }

    public void addField(ReportField field){
        fields.add(field);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void addFare(Expense fare) {
        fares.add(fare);
    }
}
