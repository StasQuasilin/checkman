package entity.log;

import entity.IImportantObject;
import entity.Worker;
import utils.LanguageBase;
import utils.hibernate.Hibernator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public abstract class IChangeComparator<T> {

    public final static LanguageBase lb = LanguageBase.getBase();
    protected final List<Change> changes = new LinkedList<>();
    protected final Hibernator hibernator = Hibernator.getInstance();


    public abstract void fix(T oldObject);
    public abstract void compare(T newObject, Worker worker);
    public abstract String getTitle();
    public void compare(Object o1, Object o2, String label){
        if (o1 == null || !o1.equals(o2)){
            Change change = new Change(label);
            if (o1 != null)
            change.setOldValue(o1.toString());
            change.setNewValue(o2.toString());
            changes.add(change);
        }
    }

}
