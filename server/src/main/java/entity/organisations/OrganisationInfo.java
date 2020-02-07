package entity.organisations;

/**
 * Created by szpt_user045 on 06.02.2020.
 */
public class OrganisationInfo {
    String code;
    String location;
    String ceo;
    String activities;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "OrganisationInfo {\n" +
                "\t\tcode='" + code + '\'' + "\n" +
                "\t\tlocation='" + location + '\'' + "\n" +
                "\t\tceo='" + ceo + '\'' + "\n" +
                "\t\tactivities='" + activities + '\'' + "\n" +
                "\t}";
    }
}
