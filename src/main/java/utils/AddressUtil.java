package utils;

import constants.Constants;
import entity.organisations.Address;
import org.json.simple.JSONObject;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
public class AddressUtil implements Constants{

    dbDAO dao = dbDAOService.getDAO();

    public synchronized Address buildAddress(JSONObject a) {

        Address address = dao.getObjectById(Address.class, a.get(ID));
        if (address == null){
            address = new Address();
        }

        boolean save = false;

        String index = String.valueOf(a.get(INDEX));
        if (!U.exist(address.getIndex()) || !address.getIndex().equals(index)){
            address.setIndex(index);
            save = true;
        }

        String region = String.valueOf(a.get(REGION));
        if (!U.exist(address.getRegion()) || !address.getRegion().equals(region)){
            address.setRegion(region);
            save = true;
        }

        String district = String.valueOf(a.get(DISTRICT));
        if (!U.exist(address.getDistrict()) || !address.getDistrict().equals(district)){
            address.setDistrict(district);
            save = true;
        }

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

        String block = String.valueOf(a.get(BLOCK));
        if (!U.exist(address.getBlock()) || !address.getBlock().equals(block)){
            address.setBlock(block);
            save = true;
        }

        String flat = String.valueOf(a.get(FLAT));
        if (!U.exist(address.getFlat()) || !address.getFlat().equals(flat)){
            address.setFlat(flat);
            save = true;
        }
        if (save){
            dao.save(address);
        }
        return address;
    }
}
