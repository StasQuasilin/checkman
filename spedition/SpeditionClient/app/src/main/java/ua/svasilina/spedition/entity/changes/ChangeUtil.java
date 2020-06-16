package ua.svasilina.spedition.entity.changes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ChangeUtil {
    private final ArrayList<Field> fieldList = new ArrayList<>();

    public void compare(Object oldObject, Object newObject){
        addFields(oldObject.getClass().getDeclaredFields());
        addFields(newObject.getClass().getDeclaredFields());
        for (Field field : fieldList){
            if (Modifier.isPublic(field.getModifiers())){
                System.out.print(field.getName() + ": " + field.getType());
                getFieldValue(field, oldObject, newObject);
            }
            System.out.println();
        }
    }

    private void getFieldValue(Field field, Object oldObject, Object newObject) {
        Object oldValue = null;
        try {
            oldValue = field.get(oldObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Object newValue = null;
        try {
            newValue = field.get(newObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (field.getType().equals(ArrayList.class)){

        }
    }

    void addFields(Field[] fields){
        for (Field field : fields){
            if (!this.fieldList.contains(field)){
                this.fieldList.add(field);
            }
        }
    }

}
