package utils;

import entity.Person;
import entity.PhoneNumber;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.organisations.Organisation;
import entity.transport.*;
import entity.weight.Weight;
import utils.hibernate.DateContainers.GE;
import utils.hibernate.custom.CustomHibernator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Carrier {

    final CustomHibernator source;
    final CustomHibernator target;
    public static final String CONF_1 = "restored/hibernate.cfg.xml";
    public static final String CONF_2 = "hibernate.cfg.xml";

    public Carrier() {
        source = new CustomHibernator(CONF_1);
        target = new CustomHibernator(CONF_2);

        saveTransport();
        saveDeals();
        System.out.println("Done!");
        source.shutdown();
        target.shutdown();
    }

    private void saveTransport() {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("date", new GE(Date.valueOf(LocalDate.of(2020, 6, 11))));
        for (Transportation transportation : source.query(Transportation.class, params)){
            checkTransportation(transportation);
        }
    }

    private void checkTransportation(Transportation transportation) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", transportation.getId());
        final Transportation targetTransportation = target.get(Transportation.class, params);
        if (targetTransportation == null){
            transportation.setId(0);
        }
        ArrayList<DocumentNote> notes = new ArrayList<>(transportation.getNotes());
        transportation.setNotes(null);

        checkTime(transportation.getTimeOut());
        checkTime(transportation.getTimeIn());
        checkTime(transportation.getTimeRegistration());
        checkTime(transportation.getCreateTime());

//        transportation.setDeal(checkDeal(transportation.getDeal()));

        if (transportation.getTransporter() != null){
            checkOrganisation(transportation.getTransporter());
        }
//        final Weight weight = transportation.getWeight();
//        if (weight != null){
//            weight.setId(0);
//            checkTime(weight.getBruttoTime());
//            checkTime(weight.getTaraTime());
//            target.save(weight);
//            transportation.setWeight(weight);
//        }

        final Vehicle vehicle = transportation.getVehicle();
        if (vehicle != null) {
            final Trailer trailer = vehicle.getTrailer();
            if (trailer != null){
                final int id = trailer.getId();
                trailer.setId(0);
                vehicle.setTrailer(checkExist(Trailer.class, trailer, id));
            }
            final int id = vehicle.getId();
            vehicle.setId(0);
            transportation.setVehicle(checkExist(Vehicle.class, vehicle, id));
        }

        final Trailer trailer = transportation.getTrailer();
        if (trailer != null){
            final int id = trailer.getId();
            trailer.setId(0);
            transportation.setTrailer(checkExist(Trailer.class, trailer, id));
        }

        final Driver driver = transportation.getDriver();
        if (driver != null){
            Person person = driver.getPerson();
            {
                final int id = person.getId();
                person.setId(0);
                ArrayList<PhoneNumber> numbers = new ArrayList<>();
                if (person.getPhones() != null){
                    numbers.addAll(person.getPhones());
                }
                person.setPhones(null);
                person = checkExist(Person.class, person, id);
                for (PhoneNumber number : numbers){
                    number.setPerson(person);
                    number.setId(0);
                    target.save(number);
                }
            }
            {
                final Vehicle driverVehicle = driver.getVehicle();
                if (driverVehicle != null) {
                    final int id = driverVehicle.getId();
                    driver.setVehicle(checkExist(Vehicle.class, driverVehicle, id));
                }
            }
            final int id = driver.getId();
            driver.setId(0);
            transportation.setDriver(checkExist(Driver.class, driver, id));
        }

//        final SunAnalyses sunAnalyses = transportation.getSunAnalyses();
//        if (sunAnalyses != null){
//            final int id = sunAnalyses.getId();
//            sunAnalyses.setId(0);
//            checkTime(sunAnalyses.getCreateTime());
//            transportation.setSunAnalyses(checkExist(SunAnalyses.class, sunAnalyses, id));
//        } else {
//            final OilAnalyses oilAnalyses = transportation.getOilAnalyses();
//            if (oilAnalyses != null){
//                final int id = oilAnalyses.getId();
//                oilAnalyses.setId(0);
//                checkTime(oilAnalyses.getCreateTime());
//                transportation.setOilAnalyses(checkExist(OilAnalyses.class, oilAnalyses, id));
//            } else {
//                final MealAnalyses mealAnalyses = transportation.getMealAnalyses();
//                if(mealAnalyses != null) {
//                    final int id = mealAnalyses.getId();
//                    mealAnalyses.setId(0);
//                    checkTime(mealAnalyses.getCreateTime());
//                    transportation.setMealAnalyses(checkExist(MealAnalyses.class, mealAnalyses, id));
//                }
//            }
//        }

        target.save(transportation);

        for (DocumentNote note : notes){
            note.setId(0);
            target.save(note);
        }
    }

    private <T> T checkExist(Class<T> tClass, T o, Object id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        final T t = target.get(tClass, params);
        if (t == null) {
            target.save(o);
            return o;
        }
        return t;
    }

    private void checkTime(ActionTime actionTime) {
        if (actionTime != null){
            actionTime.setId(0);
            target.save(actionTime);
        }
    }

    public static void main(String[] args) {
        new Carrier();
    }

    Deal checkDeal(Deal deal){
        final HashMap<String, Object> param = new HashMap<>();
        param.put("id", deal.getId());
        final Deal targetDeal = target.get(Deal.class, param);
        if (targetDeal == null) {
            deal.setId(0);
            ArrayList<DealProduct> arrayList = new ArrayList<>();
            if (deal.getProducts() != null) {
                arrayList.addAll(deal.getProducts());
                deal.setProducts(null);
            }

            checkOrganisation(deal.getOrganisation());
            checkTime(deal.getCreate());

            target.save(deal);

            for (DealProduct dealProduct : arrayList) {
                param.put("id", dealProduct.getId());
                final DealProduct tdp = target.get(DealProduct.class, param);
                if (tdp == null) {
                    dealProduct.setId(0);
                }
                dealProduct.setDeal(deal);
                target.save(dealProduct);
            }
            return deal;
        }

        return targetDeal;

    }

    private void checkOrganisation(Organisation organisation) {
        final HashMap<String, Object> param = new HashMap<>();
        param.put("id", organisation.getId());
        final Organisation targetOrganisation = target.get(Organisation.class, param);
        if (targetOrganisation == null){
            organisation.setId(0);
        }
        checkTime(organisation.getCreate());
        target.save(organisation);
    }

    void saveDeals(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("date", new GE(Date.valueOf(LocalDate.of(2020, 6, 11))));
        final List<Deal> deals = source.query(Deal.class, params);
        for (Deal deal : deals){
            checkDeal(deal);
        }
    }
}

