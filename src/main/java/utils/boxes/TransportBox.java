package utils.boxes;

import entity.documents.LoadPlan;

import java.util.HashMap;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
public class TransportBox extends IBox{
    private static final TransportBox box = new TransportBox();

    public static TransportBox getBox() {
        return box;
    }

    private final HashMap<Integer, LoadPlan> hashMap = new HashMap<>();

    public void put(LoadPlan plan){

    }
}
