package utils.constructors;

import constants.Keys;
import entity.references.Organisation;
import utils.db.dao.DaoService;
import utils.db.dao.references.OrganisationDAO;
import utils.json.JsonObject;

public class OrganisationConstructor {

    private final OrganisationDAO organisationDAO = DaoService.getOrganisationDAO();

    public Organisation getOrganisation(JsonObject json){
        Organisation organisation = organisationDAO.getOrganisation(json.get(Keys.ID));
        if (organisation == null){
            organisation = new Organisation();
            final String name = json.getString(Keys.NAME);
            if(organisation.getName() == null || !organisation.getName().equals(name)){
                organisation.setName(name);
            }
        }

        organisationDAO.save(organisation);
        return organisation;

    }
}
