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

    protected final List<Change> changes = new LinkedList<>();

    public abstract void fix(T oldObject);
    public abstract void compare(T newObject, Worker worker);
    public abstract String getTitle();

    public void compare(Object o1, Object o2, String label){
        compare(o1, o1, o2, o2, label);
    }

    public synchronized void compare(Object comparator1, Object o1, Object comparator2, Object o2, String label) {
        boolean saveIt;
        if(comparator1 != null && comparator2 != null) {
            saveIt = !comparator1.equals(comparator2);
        } else{
            saveIt = comparator1 != null || comparator2 != null;
        }

        if (saveIt){
            Change change = new Change(label);
            if (o1 != null)
                change.setOldValue(o1.toString());
            if (o2 != null)
                change.setNewValue(o2.toString());
            changes.add(change);
        }
    }

}
