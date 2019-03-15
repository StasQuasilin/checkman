package api.references.organisation;

import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by quasilin on 14.03.2019.
 */
public class OrganisationParseTest {

    private static final Hibernator hibernator = Hibernator.getInstance();

    public static void main(String[] args) {
        String input = "  Поросятов тов";
        System.out.println(parse(input));
        HibernateSessionFactory.shutdown();
    }
    public static synchronized Organisation parse(String value){
        Organisation organisation = new Organisation();
        List<OrganisationType> types = hibernator.query(OrganisationType.class, null);

        value = value.trim();
        value = value.toUpperCase();

        for (OrganisationType type : types){
            Pattern pattern = Pattern.compile("\\s" + type.getName() + "|" + type.getName() + "\\s");
            Matcher matcher = pattern.matcher(value.toUpperCase());
            if (matcher.find()){
                organisation.setType(type.getName());
                String group = matcher.group();
                value = value.replaceFirst(group, "");
                value = value.trim();
                break;
            }
        }

        organisation.setName(value);

        return organisation;
    }
}
