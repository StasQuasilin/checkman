package utils.hibernate.dao;

import constants.Constants;
import utils.hibernate.Hibernator;

public abstract class HibernateDAO {
    protected final Hibernator hibernator = Hibernator.getInstance();

    public <T>T getObjectById(Class<T> tClass, Object id){
        return hibernator.get(tClass, Constants.ID, id);
    }

    public void save(Object o){
        hibernator.save(o);
    }
}
