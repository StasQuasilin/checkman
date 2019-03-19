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

    @Override
    public void fix(Deal oldObject) {
        date = oldObject.getDate();
        dateTo = oldObject.getDateTo();
        if (oldObject.getOrganisation() != null) {
            organisationId = oldObject.getOrganisation().getId();
        }
    }

    @Override
    public void compare(Deal newObject, Worker worker) {
        compare(date, newObject.getDate(), lb.get(Constants.Languages.DATE_DOCUMENT));
        compare(dateTo, newObject.getDateTo(), lb.get(Constants.Languages.DATE_TO_DOCUMENT));
        if (organisationId != newObject.getOrganisation().getId()){
            Change change = new Change(lb.get(Constants.Languages.ORGANISATION_DOCUMENT));
            if (organisationId != -1){
                change.setOldValue(hibernator.get(Organisation.class, "id", organisationId).getValue());
            }
            change.setNewValue(newObject.getOrganisation().getValue());
        }
            ChangeLogUtil.writeLog(newObject.getUid(), getTitle(), worker, changes);
        changes.clear();
    }

    @Override
    public String getTitle() {
        return date == null && dateTo == null ? lb.get(Constants.Languages.CREATE_DOCUMENT) : lb.get(Constants.Languages.EDIT_DOCUMENT);
    }
}
