package entity.log.comparators;

import entity.Worker;
import entity.log.IChangeComparator;
import entity.weight.Weight;
import utils.ChangeLogUtil;

/**
 * Created by szpt_user045 on 02.05.2019.
 */
public class WeightComparator extends IChangeComparator<Weight> {

    private Float brutto;
    private Float tara;
    private boolean isNew;

    @Override
    public void fix(Weight oldObject) {
        if (!(isNew = oldObject == null)){
            brutto = oldObject.getBrutto();
            tara = oldObject.getTara();
        } else {
            brutto = 0f;
            tara = 0f;
        }
    }

    @Override
    public void compare(Weight newObject, Worker worker) {
        compare(brutto, newObject.getBrutto(), "weight.brutto");
        compare(tara, newObject.getTara(), "weight.tara");
        ChangeLogUtil.writeLog(newObject.getUid(), getTitle(), worker, changes);
    }

    @Override
    public String getTitle() {
        return isNew ? "weight.new" : "weight.edit";
    }
}
