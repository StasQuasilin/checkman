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
    private Integer dealId;
    private Float plan;
    private TransportCustomer customer;
    private String documentOrganisation;
    private Boolean canceled;
    boolean newPlan;

    @Override
    public void fix(LoadPlan oldObject) {
        newPlan = oldObject == null;
        if (!newPlan){
            date = oldObject.date;
            dealId = oldObject.getDeal().getId();
            plan = oldObject.getPlan();
            customer = oldObject.getCustomer();
            documentOrganisation = oldObject.getDocumentOrganisation().getValue();
            canceled = oldObject.isCanceled();
        }
    }

    @Override
    public void compare(LoadPlan newObject, Worker worker) {
        compare(newPlan ? null : date, newObject.getDate(), "plan.date");
        compare(newPlan ? null : dealId, newObject.getDeal().getId(), "plan.deal");
        compare(newPlan ? null : plan, newObject.getPlan(), "plan.plan");
        compare(newPlan ? null : customer, newObject.getCustomer(), "plan.customer");
        compare(newPlan ? null : documentOrganisation, newObject.getDocumentOrganisation().getValue(), "from");
        compare(newPlan ? null : canceled, newObject.isCanceled(), "plan.canceled");
        ChangeLogUtil.writeLog(newObject.getUid(), getTitle(), worker, changes);
        changes.clear();
    }

    @Override
    public String getTitle() {
        return newPlan ? "new.plan" : "edit.plan";
    }
}
