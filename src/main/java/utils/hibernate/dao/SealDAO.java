package utils.hibernate.dao;

import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.transport.Transportation;

import java.util.HashMap;
import java.util.List;

import static constants.Constants.ARCHIVE;

public class SealDAO extends HibernateDAO{
    private static final String CREATE_TIME = "created/time";

    public List<SealBatch> getBatchesByDate(Object date){
        final HashMap<String,Object> args = new HashMap<>();
        args.put(ARCHIVE, false);
        args.put(CREATE_TIME, date);
        return hibernator.query(SealBatch.class, args);
    }

    public List<Seal> getSealsByTransportation(Object transportation) {
        return hibernator.query(Seal.class, "cargo", transportation);
    }
}
