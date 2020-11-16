package utils.hibernate.dao;

import constants.Keys;
import entity.Counterparty;
import utils.hibernate.Hibernator;

import java.util.Collection;
import java.util.List;

public class CounterpartyDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    public List<Counterparty> getAll() {
        return hibernator.query(Counterparty.class, null);
    }

    public Counterparty getCounterparty(Object id) {
        return hibernator.get(Counterparty.class, Keys.ID, id);
    }
}
