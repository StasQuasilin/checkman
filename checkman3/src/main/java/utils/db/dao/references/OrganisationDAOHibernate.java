package utils.db.dao.references;

import constants.Keys;
import entity.references.Organisation;
import utils.db.hibernate.Hibernator;

import java.util.List;

public class OrganisationDAOHibernate implements OrganisationDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Organisation> find(String key) {
        return hibernator.find(Organisation.class, Keys.NAME, key);
    }
}
