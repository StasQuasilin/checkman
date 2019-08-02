package utils.boxes;

import entity.documents.LoadPlan;

import java.util.HashMap;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
public class TransportBox extends IBox<LoadPlan>{
    private static final TransportBox box = new TransportBox();

    public static TransportBox getBox() {
        return box;
    }

    @Override
    public void init() {
    }

    @Override
    public void save(LoadPlan loadPlan) {
        put(loadPlan);
    }

    @Override
    public void put(LoadPlan plan) {
        hashMap.put(plan.getId(), plan);
    }

    @Override
    public LoadPlan get(int id) {
        if (hashMap.containsKey(id)){
            return hashMap.get(id);
        }
        return null;
    }

    @Override
    public LoadPlan remove(int id) {
        if (hashMap.containsKey(id)){
            return hashMap.remove(id);
        }
        return null;
    }
}
