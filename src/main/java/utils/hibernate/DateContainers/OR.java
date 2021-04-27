package utils.hibernate.DateContainers;

import java.util.LinkedList;

public class OR {
    private final LinkedList<Object> linkedList = new LinkedList<>();
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
