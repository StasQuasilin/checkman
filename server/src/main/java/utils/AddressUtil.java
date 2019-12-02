package utils;

import constants.Constants;
import entity.Address;
import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
public class AddressUtil implements Constants{
    public static boolean buildAddress(Address address, JSONObject a) {
        boolean save = false;
        String city = String.valueOf(a.get(CITY));
        if (address.getCity() == null || !address.getCity().equals(city)){
            address.setCity(city);
            save = true;
        }
        String street = String.valueOf(a.get(STREET));
        if (address.getStreet() == null || !address.getStreet().equals(street)){
            address.setStreet(street);
            save = true;
        }
        String build = String.valueOf(a.get(BUILD));
        if (address.getBuild() == null || !address.getBuild().equals(build)){
            address.setBuild(build);
            save = true;
        }
        return save;
    }
}
