package entity.log.comparators;

import constants.Constants;
import entity.Worker;
import entity.documents.Deal;
import entity.log.Change;
import entity.log.IChangeComparator;
import entity.organisations.Organisation;
import utils.ChangeLogUtil;

import java.sql.Date;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class DealComparator extends IChangeComparator<Deal> {

    private Date date;
    private Date dateTo;
    private int organisationId = -1;
    private int product;

    @Override
    public void fix(Deal oldObject) {
        if (oldObject != null) {
            date = oldObject.getDate();
            dateTo = oldObject.getDateTo();
            if (oldObject.getOrganisation() != null) {
                organisationId = oldObject.getOrganisation().getId();
            }
        }
    }

    @Override
    public void compare(Deal newObject, Worker worker) {
        compare(date, newObject.getDate(), "date");
        compare(dateTo, newObject.getDateTo(), "dateTo");
        if (organisationId != newObject.getOrganisation().getId()){
            Change change = new Change("from");
            change.setNewValue(newObject.getOrganisation().getValue());
            changes.add(change);
        }
        ChangeLogUtil.writeLog(newObject.getUid(), getTitle(), worker, changes);
        changes.clear();
    }

    @Override
    public String getTitle() {
        return date == null && dateTo == null ? "new" : "edit";
    }
}
