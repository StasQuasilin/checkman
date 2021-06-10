package utils.hibernate.DateContainers;

import entity.bot.NotifyStatus;

import java.util.Arrays;
import java.util.LinkedList;

public class OR {
    private final LinkedList<Object> linkedList = new LinkedList<>();

    public OR(Object ... o) {
        linkedList.addAll(Arrays.asList(o));
    }

    public void add(Object o){
        linkedList.add(o);
    }

    public int size() {
        return linkedList.size();
    }

    public LinkedList<Object> items() {
        return linkedList;
    }
}
