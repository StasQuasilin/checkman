package utils.boxes;

import utils.hibernate.Hibernator;

import java.util.HashMap;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
public abstract class IBox<T> {
    final HashMap<Integer, T> hashMap = new HashMap<>();
    public abstract void init();
    public abstract void save(T t);
    public abstract void put(T t);

    public abstract T get(int id);
    public abstract T remove(int id);
}
