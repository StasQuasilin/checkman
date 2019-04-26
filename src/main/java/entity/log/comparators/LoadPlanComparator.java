package entity.log.comparators;

import entity.Worker;
import entity.documents.Deal;
import entity.documents.DocumentOrganisation;
import entity.documents.LoadPlan;
import entity.log.IChangeComparator;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import utils.ChangeLogUtil;

import java.sql.Date;

/**
 * Created by szpt_user045 on 26.04.2019.
 */
public class LoadPlanComparator extends IChangeComparator<LoadPlan> {

    private Date date;
    private int dealId;
    private float plan;
    private TransportCustomer customer;
    private DocumentOrganisation documentOrganisation;
    private boolean canceled;
    boolean newPlan;

    @Override
    public void fix(LoadPlan oldObject) {
        newPlan = oldObject == null;
        if (!newPlan){
            date = oldObject.date;
            dealId = oldObject.getDeal().getId();
            plan = oldObject.getPlan();
            customer = oldObject.getCustomer();
            documentOrganisation = oldObject.getDocumentOrganisation();
            canceled = oldObject.isCanceled();
        }
    }

    @Override
    public void compare(LoadPlan newObject, Worker worker) {
        compare(newPlan ? null : date, newObject.getDate(), "date");
        compare(newPlan ? null : dealId, newObject.getDeal().getId(), "deal");
        compare(newPlan ? null : plan, newObject.getPlan(), "plan");
        compare(newPlan ? null : customer, newObject.getCustomer(), "customer");
        compare(newPlan ? null : documentOrganisation, newObject.getDocumentOrganisation(), "from");
        compare(newPlan ? null : canceled, newObject.isCanceled(), "canceled");
        ChangeLogUtil.writeLog(newObject.getUid(), getTitle(), worker, changes);
        changes.clear();
    }

    @Override
    public String getTitle() {
        return newPlan ? "new" : "edit";
    }
}
