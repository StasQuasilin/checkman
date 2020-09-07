package utils.db.dao.references;

import entity.references.Organisation;

import java.util.List;

public interface OrganisationDAO {
    List<Organisation> find(String key);

    Organisation getOrganisation(Object id);

    void save(Organisation organisation);
}
