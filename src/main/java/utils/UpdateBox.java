package utils;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 10.07.2019.
 */
public class UpdateBox {
    private static final UpdateBox INSTANCE = new UpdateBox();

    public static UpdateBox instance() {
        return INSTANCE;
    }

    public enum BoxType {
        deal,
        dealArchive,
        transport,
        transportArchive
    }

    public UpdateBox() {
        update(BoxType.deal);
        update(BoxType.dealArchive);
        update(BoxType.transport);
        update(BoxType.transportArchive);
    }

    private final HashMap<BoxType, LocalDateTime> box = new HashMap<>();
    public void update(BoxType type){
        box.put(type, LocalDateTime.now());
    }
    public LocalDateTime getBoxValue(BoxType type){
        return box.get(type);
    }
}
