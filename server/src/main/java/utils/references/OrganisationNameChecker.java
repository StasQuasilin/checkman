package utils.references;

import constants.Constants;
import entity.organisations.Organisation;

/**
 * Created by szpt_user045 on 24.01.2020.
 */
public class OrganisationNameChecker implements Constants{

    public synchronized boolean check(Organisation organisation){
        if (organisation.getName().length() > 20) {
            String name = organisation.getName();
            String[] split = name.split(" ");
            StringBuilder builder = new StringBuilder();
            if (split.length > 1) {
                builder.append(split[0]).append(SPACE);
                for (int i = 1; i < split.length; i++) {
                    builder.append(split[i].substring(0, 1).toUpperCase());
                }
            } else {
                builder.append(split[0].substring(0, 20));
            }
            organisation.setFullName(name);
            organisation.setName(builder.toString());
            return true;
        }
        return false;
    }
}
