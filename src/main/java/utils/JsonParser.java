package utils;

import bot.BotUID;
import entity.*;
import entity.answers.IAnswer;
import entity.bot.UserBotSetting;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.laboratory.CakeAnalyses;
import entity.laboratory.LaboratoryTurn;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.probes.OilProbe;
import entity.laboratory.probes.SunProbe;
import entity.laboratory.storages.StorageAnalyses;
import entity.laboratory.storages.StorageTurn;
import entity.laboratory.subdivisions.extraction.*;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.laboratory.subdivisions.vro.*;
import entity.log.Change;
import entity.log.ChangeLog;
import entity.organisations.Organisation;
import entity.production.Turn;
import entity.products.Product;
import entity.rails.Train;
import entity.rails.Truck;
import entity.seals.Seal;
import entity.transport.ActionTime;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.Weight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class JsonParser {

    final static String NAME = "name";
    final static String VALUE = "value";

    public static JSONObject toJson(Organisation organisation) {
        JSONObject json = pool.getObject();
        if (organisation != null) {
            json.put(ID, organisation.getId());
            json.put(TYPE, organisation.getType());
            json.put(NAME, organisation.getName());
            json.put(VALUE, organisation.getValue());
        }
        return json;
    }
    
    public static final String DATE_TO = "date_to";
    public static final String VISIBILITY = "visibility";
    public static final String DONE = "done";
    public static final String PRICE = "price";
    
    public static JSONObject toJson(Deal deal) {
        JSONObject json = new JSONObject();
        json.put(ID, deal.getId());
        json.put(DATE, deal.getDate().toString());
        json.put(DATE_TO, deal.getDateTo().toString());
        json.put(ORGANISATION, toJson(deal.getOrganisation()));
        json.put(VISIBILITY, deal.getDocumentOrganisation().getValue());
        json.put(PRODUCT, toJson(deal.getProduct()));
        json.put(QUANTITY, deal.getQuantity());
        json.put(DONE, deal.getDone());
        json.put(PRICE, deal.getPrice());
        json.put(CREATOR, toJson(deal.getCreator()));
        json.put(HASH, deal.getHash());
        json.put(UNIT, deal.getUnit().getName());
        json.put(TYPE, deal.getType().toString());
        return json;
    }

    public static JSONObject toJson(Worker worker) {
        JSONObject json = pool.getObject();
        json.put(ID, worker.getId());
        json.put(PERSON, toJson(worker.getPerson()));
        return json;
    }

    final static String FORENAME = "forename";
    final static String SURNAME = "surname";
    final static String PATRONYMIC = "patronymic";
    final static String PHONES = "phones";

    private static JSONObject toJson(Person person) {
        JSONObject json = pool.getObject();
        json.put(ID, person.getId());
        json.put(FORENAME, person.getForename());
        json.put(SURNAME, person.getSurname());
        json.put(PATRONYMIC, person.getPatronymic());
        json.put(VALUE, person.getValue());
        json.put(PHONES, toPhoneJson(person.getPhones()));
        return json;
    }

    private static JSONArray toPhoneJson(Set<PhoneNumber> phones) {
        JSONArray array = new JSONArray();
        for (PhoneNumber number : phones) {
            array.add(toJson(number));
        }
        return array;
    }

    private static JSONObject toJson(PhoneNumber number) {
        JSONObject json = new JSONObject();
        json.put(ID, number.getId());
        json.put(NUMBER, number.getNumber());
        return json;
    }

    final static String ANALYSES = "analyses";

    private static JSONObject toJson(Product product) {
        JSONObject json = pool.getObject();
        json.put(ID, product.getId());
        json.put(NAME, product.getName());
        json.put(ANALYSES, product.getAnalysesType().toString());
        return json;
    }
    
    public static final String STATUS = "status";
    
    public static JSONObject toJson(IAnswer answer) {
        JSONObject json = new JSONObject();
        json.put(STATUS, answer.status());
        for (Map.Entry<String, String> entry : answer.getParams().entrySet()){
            json.put(entry.getKey(), entry.getValue());
        }
        return json;
    }

    public static final String PLAN = "plan";
    public static final String CUSTOMER = "customer";

    public static JSONObject toJson(LoadPlan lp) {
        JSONObject json = new JSONObject();
        json.put(ID, lp.getId());
        json.put(DATE, lp.getDate().toString());
        json.put(PLAN, lp.getPlan());
        json.put(CUSTOMER, lp.getCustomer().toString());
        json.put(TRANSPORTATION, toJson(lp.getTransportation()));
        json.put(HASH, lp.hashCode());
        return json;
    }

    final static String VEHICLE = "vehicle";
    final static String DRIVER = "driver";
    final static String TIME_IN = "timeIn";
    final static String TIME_OUT = "timeOut";
    final static String WEIGHT = "weight";
    final static String ANY = "any";
    final static String ARCHIVE = "archive";

    public static JSONObject toJson(Transportation transportation) {

        JSONObject json = pool.getObject();
        if (transportation != null){
            json.put(ID, transportation.getId());
            json.put(VEHICLE, toJson(transportation.getVehicle()));
            json.put(DRIVER, toJson(transportation.getDriver()));
            json.put(TIME_IN, toJson(transportation.getTimeIn()));
            json.put(TIME_OUT, toJson(transportation.getTimeOut()));
            json.put(HASH, transportation.hashCode());
            json.put(WEIGHT, toJson(transportation.getWeight()));
            json.put(ANALYSES, toJson(transportation.getSunAnalyses(), transportation.getOilAnalyses(), transportation.getCakeAnalyses()));
            json.put(ANY, transportation.anyAction());
            json.put(ARCHIVE, transportation.isArchive());
        }
        return json;
    }

    public static final String SUN = "sun";
    public static final String OIL = "oil";
    public static final String CAKE = "cake";

    private static JSONObject toJson(SunAnalyses sunAnalyses, OilAnalyses oilAnalyses, CakeAnalyses cakeAnalyses) {
        JSONObject json = pool.getObject();

        json.put(SUN, toJson(sunAnalyses));
        json.put(OIL, toJson(oilAnalyses));
        json.put(CAKE, toJson(cakeAnalyses));
        return json;
    }

    public static final String PROTEIN = "protein";
    public static final String CELLULOSE = "cellulose";

    private static JSONObject toJson(CakeAnalyses analyses) {
        JSONObject json = new JSONObject();
        if (analyses != null) {
            json.put(ID, analyses.getId());
            json.put(HUMIDITY, analyses.getHumidity());
            json.put(PROTEIN, analyses.getProtein());
            json.put(CELLULOSE, analyses.getCellulose());
            json.put(OILINESS, analyses.getOiliness());

            json.put(CREATE, toJson(analyses.getCreateTime()));

            if (analyses.getCreator() != null) {
                json.put(CREATOR, toJson(analyses.getCreator()));
            }
        }
        return json;
    }
    
    public static final String ORGANOLEPTIC = "organoleptic";
    public static final String COLOR = "color";
    public static final String ACID = "acid";
    public static final String PEROXIDE = "peroxide";
    public static final String PHOSPHORUS = "phosphorus";
    public static final String HUMIDITY = "humidity";
    public static final String SOAP = "soap";
    public static final String WAX = "wax";
    
    private static JSONObject toJson(OilAnalyses analyses) {
        JSONObject json = new JSONObject();
        if (analyses != null) {
            json.put(ID, analyses.getId());
            json.put(ORGANOLEPTIC, analyses.isOrganoleptic());
            json.put(COLOR, analyses.getColor());
            json.put(ACID, analyses.getAcidValue());
            json.put(PEROXIDE, analyses.getPeroxideValue());
            json.put(PHOSPHORUS, analyses.getPhosphorus());
            json.put(HUMIDITY, analyses.getHumidity());
            json.put(SOAP, analyses.isSoap());
            json.put(WAX, analyses.getWax());
            json.put(CREATE, toJson(analyses.getCreateTime()));
            if (analyses.getCreator() != null) {
                json.put(CREATOR, toJson(analyses.getCreator()));
            }
        }
        return json;
    }

    public static final String OILINESS = "oiliness";
    public static final String HUMIDITY1 = "humidity1";
    public static final String HUMIDITY2 = "humidity2";
    public static final String SORENESS = "soreness";
    public static final String OIL_IMPURITY = "oilImpurity";
    public static final String ACID_VALUE = "acidValue";
    public static final String CONTAMINATION = "contamination";
    public static final String CREATE = "create";

    private static JSONObject toJson(SunAnalyses analyses) {
        JSONObject json = new JSONObject();
        if (analyses != null) {
            json.put(ID, analyses.getId());
            json.put(OILINESS, analyses.getOiliness());
            json.put(HUMIDITY1, analyses.getHumidity1());
            json.put(HUMIDITY2, analyses.getHumidity2());
            json.put(SORENESS, analyses.getSoreness());
            json.put(OIL_IMPURITY, analyses.getOilImpurity());
            json.put(ACID_VALUE, analyses.getAcidValue());
            json.put(CONTAMINATION, analyses.isContamination());
            json.put(CREATE, toJson(analyses.getCreateTime()));
            if (analyses.getCreator() != null) {
                json.put(CREATOR, toJson(analyses.getCreator()));
            }
        }
        return json;
    }

    public static final String BRUTTO = "brutto";
    public static final String BRUTTO_Time = "brutto_Time";
    public static final String TARA = "tara";
    public static final String NETTO = "netto";
    public static final String CORRECTION = "correction";
    public static final String TARA_Time = "tara_Time";

    private static JSONObject toJson(Weight weight) {
        JSONObject json = pool.getObject();
        if (weight != null) {
            json.put(ID, weight.getId());
            json.put(BRUTTO, weight.getBrutto());
            json.put(BRUTTO_Time, toJson(weight.getBruttoTime()));
            json.put(TARA, weight.getTara());
            json.put(NETTO, weight.getNetto());
            json.put(CORRECTION, weight.getCorrection());
            json.put(TARA_Time, toJson(weight.getTaraTime()));
        }
        return json;
    }

    final static String CREATOR = "creator";
    final static String Time = "Time";

    private static JSONObject toJson(ActionTime actionTime) {
        JSONObject json = pool.getObject();
        if (actionTime != null){
            json.put(ID, actionTime.getId());
            json.put(CREATOR, toJson(actionTime.getCreator()));
            json.put(Time, actionTime.getTime().toString());
        }
        return json;
    }

    final static String PERSON = "person";

    public static JSONObject toJson(Driver driver) {
        JSONObject json = pool.getObject();
        if (driver != null){
            json.put(ID, driver.getId());
            json.put(PERSON, toJson(driver.getPerson()));
            json.put(ORGANISATION, toJson(driver.getOrganisation()));
        }
        return json;
    }

    final static String MODEL = "model";
    final static String NUMBER = "number";
    final static String TRAILER = "trailer";

    public static JSONObject toJson(Vehicle vehicle) {
        JSONObject json = new JSONObject();
        if (vehicle != null){
            json.put(ID, vehicle.getId());
            json.put(MODEL, vehicle.getModel());
            json.put(NUMBER, vehicle.getNumber());
            if (vehicle.getTrailer() != null){
                json.put(TRAILER, vehicle.getTrailer());
            }
        }
        return json;
    }
    final static JsonPool pool = JsonPool.getPool();
    final static String ID = "id";
    final static String DATE = "date";
    final static String TYPE = "type";
    final static String ORGANISATION = "organisation";
    final static String PRODUCT = "product";
    final static String QUANTITY = "quantity";
    final static String UNIT = "unit";
    final static String REALISATION = "realisation";
    final static String TRANSPORTATION = "transportation";
    final static String HASH = "hash";

    public static JSONObject toLogisticJson(LoadPlan loadPlan) {
        final JSONObject json = pool.getObject();
        json.put(ID, loadPlan.getId());
        json.put(DATE, loadPlan.getDate().toString());
        json.put(TYPE, loadPlan.getDeal().getType().toString());
        json.put(ORGANISATION, toJson(loadPlan.getDeal().getOrganisation()));
        json.put(PRODUCT, toJson(loadPlan.getDeal().getProduct()));
        json.put(QUANTITY, loadPlan.getPlan());
        json.put(UNIT, loadPlan.getDeal().getUnit().getName());
        json.put(REALISATION, loadPlan.getDocumentOrganisation().getValue());
        json.put(TRANSPORTATION, toJson(loadPlan.getTransportation()));
        json.put(HASH, loadPlan.hashCode());

        return json;
    }


    public static JSONObject toJson(SunProbe sun) {
        JSONObject json = new JSONObject();
        json.put("id", sun.getId());
        json.put("type", AnalysesType.sun.toString());
        json.put("date", sun.getAnalyses().getCreateTime().getTime().toString());
        if (sun.getManager() != null) {
            json.put("manager", toJson(sun.getManager()));
        }
        if (sun.getOrganisation() != null) {
            json.put("organisation", sun.getOrganisation());
        }
        json.put("analyses", toJson(sun.getAnalyses()));
        json.put("hash", sun.hashCode());
        return json;
    }

    public static JSONObject toJson(OilProbe oil) {
        JSONObject json = new JSONObject();
        json.put("id", oil.getId());
        json.put("type", AnalysesType.oil.toString());
        json.put("date", oil.getAnalyses().getCreateTime().getTime().toString());
        if (oil.getManager() != null) {
            json.put("manager", toJson(oil.getManager()));
        }
        if (oil.getOrganisation() != null) {
            json.put("organisation", oil.getOrganisation());
        }
        json.put("analyses", toJson(oil.getAnalyses()));
        json.put("hash", oil.hashCode());
        return json;
    }

    public static final String UID = "uid";

    public static JSONObject toJson(User user) {
        JSONObject json = toJson(user.getWorker());
        json.put(UID, user.getUid());
        return json;
    }

    public static JSONObject toJson(Seal seal) {
        JSONObject json = new JSONObject();
        json.put("id", seal.getId());
        json.put("number", seal.getNumber());
        return json;
    }

    public static JSONObject toJson(BotUID botUID) {
        JSONObject json = new JSONObject();
        json.put("id", botUID.getId());
        json.put("uid", botUID.getUid());
        return json;
    }

    public static JSONObject toJson(UserBotSetting setting) {
        JSONObject json = new JSONObject();
        json.put("id", setting.getTelegramId());
        json.put("transport", setting.getTransport().toString());
        json.put("weight", setting.getWeight().toString());
        json.put("analyses", setting.getAnalyses().toString());
        json.put("extraction", setting.isExtraction());
        json.put("vro", setting.isVro());
        json.put("kpo", setting.isKpo());
        json.put("show", setting.isShow());
        return json;
    }

    public static JSONArray toJson(List<Deal> deals) {
        JSONArray array = new JSONArray();
        for (Deal deal : deals) {
            array.add(toJson(deal));
        }
        return array;
    }

    public static JSONArray toJson(Collection<Driver> drivers, HashMap<Integer, Vehicle> vehicles) {
        JSONArray array = new JSONArray();
        for (Driver driver : drivers) {
            JSONObject d = toJson(driver);
            if (vehicles.containsKey(driver.getId())){
                d.put("vehicle", toJson(vehicles.get(driver.getId())));
            }
            array.add(d);
        }
        return array;
    }

    public static JSONArray toJson(Collection<Organisation> values) {
        JSONArray array = new JSONArray();
        for (Organisation organisation : values){
            array.add(toJson(organisation));
        }
        return array;
    }

    public static JSONObject toJson(Transportation transportation, ArrayList<ChangeLog> logs) {
        JSONObject json = new JSONObject();
        json.put("weight", toJson(transportation.getWeight()));
        json.put("analyses", toJson(
                transportation.getSunAnalyses(),
                transportation.getOilAnalyses(),
                transportation.getCakeAnalyses())
        );
        json.put("logs", toJson(logs));
        return json;
    }

    private static JSONArray toJson(ArrayList<ChangeLog> logs) {
        JSONArray array = new JSONArray();
        for(ChangeLog log : logs) {
            array.add(toJson(log));
        }
        return array;
    }

    private static JSONObject toJson(ChangeLog log) {
        JSONObject json = new JSONObject();
        json.put("id", log.getId());
        json.put("date", log.getTime().toString());
        json.put("message", log.getLabel());
        json.put("creator", log.getCreator().getValue());
        json.put("changes", toChangesJson(log.getChanges()));
        return json;
    }

    private static JSONArray toChangesJson(Set<Change> changes) {
        JSONArray array = new JSONArray();
        for (Change change : changes){
            array.add(toJson(change));
        }
        return array;
    }

    private static JSONObject toJson(Change change) {
        JSONObject json = new JSONObject();

        json.put("field", change.getField());

        return json;
    }

    public static JSONObject toJson(KPOPart part) {
        JSONObject json = new JSONObject();
        json.put("id", part.getId());
        json.put("date", part.getDate().toString());
        json.put("number", part.getNumber());
        json.put("organoleptic", part.isOrganoleptic());
        json.put("color", part.getColor());
        json.put("acid", part.getAcid());
        json.put("peroxide", part.getPeroxide());
        json.put("soap", part.isSoap());
        json.put("hash", part.hashCode());
        return json;
    }

    public static JSONObject toJson(Train train) {
        JSONObject json = new JSONObject();
        json.put("id", train.getId());
        json.put("deal", toJson(train.getDeal()));
        return json;
    }

    private static JSONArray toTruckJson(Set<Truck> trucks) {
        JSONArray array = new JSONArray();
        for (Truck truck : trucks){
            array.add(toJson(truck));
        }
        return array;
    }

    public static JSONObject toJson(Truck truck) {
        JSONObject json = new JSONObject();
        json.put("id", truck.getId());
        json.put("train", toJson(truck.getTrain()));
        json.put("number", truck.getNumber());
        json.put("hash", truck.hashCode());
        return json;
    }

    public static JSONObject toJson(LaboratoryTurn laboratoryTurn) {
        JSONObject json = new JSONObject();
        json.put("id", laboratoryTurn.getId());
        json.put("worker", toJson(laboratoryTurn.getWorker()));
        return json;
    }

    public static JSONObject toJson(Turn turn, List<LaboratoryTurn> query) {
        JSONObject json = toJson(turn);
        json.put("laboratory", toLaboratoryJson(query));
        int hash = turn.hashCode();
        for (LaboratoryTurn l : query){
            hash = 31 * l.getWorker().hashCode() + hash;
        }
        json.put("hash", hash);
        return json;
    }

    private static JSONObject toJson(Turn turn) {
        JSONObject json = new JSONObject();
        json.put("id", turn.getId());
        json.put("date", turn.getDate().toString());
        json.put("number", turn.getNumber());
        return json;
    }

    private static JSONArray toLaboratoryJson(List<LaboratoryTurn> query) {
        JSONArray array = new JSONArray();
        for (LaboratoryTurn turn : query){
            array.add(toJson(turn));
        }
        return array;
    }

    public static JSONObject toJson(StorageTurn turn) {
        JSONObject json = new JSONObject();
        json.put("id", turn.getId());
        json.put("turn", toJson(turn.getTurn()));
        json.put("analyses", toAnalysesJson(turn.getAnalyses()));
        json.put("hash", turn.hashCode());

        return json;
    }

    private static JSONArray toAnalysesJson(Set<StorageAnalyses> analyses) {
        JSONArray array = new JSONArray();
        for (StorageAnalyses analyse : analyses){
            array.add(toJson(analyse));
        }
        return array;
    }

    private static JSONObject toJson(StorageAnalyses analyse) {
        JSONObject json = new JSONObject();
        json.put("id", analyse.getId());
        json.put("storage", analyse.getStorage().getName());
        json.put("oil", toJson(analyse.getOilAnalyses()));
        return json;
    }

    public static class Laboratory {
        public static class Extraction {
            public static JSONObject toJson(ExtractionTurn turn) {
                JSONObject json = new JSONObject();
                json.put("id", turn.getId());
                json.put("number", turn.getTurn().getNumber());
                json.put("date", turn.getTurn().getDate().toString());
                json.put("crudes", toJson(turn.getCrudes()));
                json.put("storageProtein", toRawJson(turn.getProtein()));
                json.put("storageGrease", toGreaseJson(turn.getGreases()));
                json.put("oil", toOilJson(turn.getOils()));
                json.put("turnProtein", toTurnJson(turn.getTurnProteins()));
                json.put("turnGrease", toTurnGrease(turn.getTurnGreases()));
                json.put("hash", turn.hashCode());

                return json;
            }

            private static JSONObject toGreaseJson(Set<StorageGrease> greases) {
                JSONObject json = new JSONObject();
                for (StorageGrease grease : greases) {
                    json.put(grease.getTime().toString(), toJson(grease));
                }
                return json;
            }

            private static JSONObject toJson(StorageGrease grease) {
                JSONObject  json = new JSONObject();
                json.put("id", grease.getId());
                json.put("grease", grease.getGrease());
                json.put("humidity", grease.getHumidity());
                return json;
            }

            private static JSONArray toTurnGrease(Set<TurnGrease> turnGreases) {
                JSONArray array = new JSONArray();
                for (TurnGrease grease : turnGreases){
                    array.add(toJson(grease));
                }
                return array;
            }

            private static JSONObject toJson(TurnGrease grease) {
                JSONObject json = new JSONObject();
                json.put("id", grease.getId());
                json.put("grease", grease.getGrease());
                json.put("humidity", grease.getHumidity());
                return json;
            }

            private static JSONArray toTurnJson(Set<TurnProtein> turns) {
                JSONArray array = new JSONArray();
                for (TurnProtein protein : turns){
                    array.add(toJson(protein));
                }
                return array;
            }

            private static JSONObject toJson(TurnProtein protein) {
                JSONObject json = new JSONObject();
                json.put("id", protein.getId());
                json.put("protein", protein.getProtein());
                json.put("humidity", protein.getHumidity());
                return json;
            }

            private static JSONArray toOilJson(Set<ExtractionOIl> oils) {
                JSONArray array = new JSONArray();
                for (ExtractionOIl oil : oils){
                    array.add(toJson(oil));
                }
                return array;
            }

            private static JSONObject toJson(ExtractionOIl oil) {
                JSONObject json = new JSONObject();
                json.put("id", oil.getId());
                json.put("humidity", oil.getHumidity());
                json.put("acid", oil.getAcid());
                json.put("peroxide", oil.getPeroxide());
                json.put("phosphorus", oil.getPhosphorus());
                json.put("explosionT", oil.getExplosionT());
                return json;
            }

            private static JSONObject toRawJson(Set<StorageProtein> raws) {
                JSONObject json = new JSONObject();
                for (StorageProtein raw : raws){
                    json.put(raw.getTime().toString(), toJson(raw));
                }
                return json;
            }

            private static JSONObject toJson(StorageProtein raw) {
                JSONObject json = new JSONObject();
                json.put("id", raw.getId());
                json.put("protein", raw.getProtein());
                json.put("humidity", raw.getHumidity());
                return json;
            }

            private static JSONArray toJson(List<ExtractionCrude> crudes) {
                JSONArray array = new JSONArray();
                Collections.sort(crudes);
                for (ExtractionCrude crude : crudes) {
                    array.add(toJson(crude));
                }
                return array;
            }

            private static JSONObject toJson(ExtractionCrude crude) {
                JSONObject json = new JSONObject();

//                private int id;
                json.put("id", crude.getId());
//                private Timestamp Time;
                json.put("Time", crude.getTime().toString());
//                private float humidityIncome;
                json.put("humidityIncome", crude.getHumidityIncome());
//                private float fraction;
                json.put("fraction", crude.getFraction());
//                private float miscellas;
                json.put("miscellas", crude.getMiscellas());
//                private float humidity;
                json.put("humidity", crude.getHumidity());
//                private float dissolvent;
                json.put("dissolvent", crude.getDissolvent());
//                private float grease;
                json.put("grease", crude.getGrease());

                return json;
            }
        }
    }

    public static class VRO {
        public static JSONObject toJson(VROTurn turn) {
            JSONObject json = new JSONObject();
            json.put("id", turn.getId());
            json.put("date", turn.getTurn().getDate().toString());
            json.put("number", turn.getTurn().getNumber());
            json.put("crudes", toCrudeJson(turn.getCrudes()));
            json.put("oil", toOilJson(turn.getOils()));
            json.put("dailies", toDailyJson(turn.getDailies()));
            json.put("oilMass", toOilMass(turn.getOilMassFractions()));
            json.put("oilMassDry", toOilMassDry(turn.getOilMassFractionDries()));
            json.put("hash", turn.hashCode());

            return json;
        }

        private static JSONArray toOilMassDry(Set<OilMassFractionDry> oilMassFractionDries) {
            JSONArray array = new JSONArray();
            for (OilMassFractionDry omf : oilMassFractionDries) {
                array.add(toJson(omf));
            }
            return array;
        }

        private static JSONObject toJson(OilMassFractionDry omf) {
            JSONObject json = new JSONObject();
            json.put("id", omf.getId());
            json.put("seed", omf.getSeed());
            json.put("husk", omf.getHusk());
            json.put("forpress", toForpressDryJson(omf.getForpressCakes()));
            return json;
        }

        private static JSONArray toForpressDryJson(Set<ForpressCakeDailyDry> forpressCakes) {
            JSONArray array = new JSONArray();
            for (ForpressCakeDailyDry fcd : forpressCakes) {
                array.add(toJson(fcd));
            }
            return array;
        }

        private static JSONObject toJson(ForpressCakeDailyDry fcd) {
            JSONObject json = new JSONObject();
            json.put("id", fcd.getId());
            json.put("forpress", fcd.getForpress().getName());
            json.put("oilcake", fcd.getOilcake());
            return json;
        }

        private static JSONArray toOilMass(Set<OilMassFraction> oilMassFractions) {
            JSONArray array = new JSONArray();
            for (OilMassFraction omf : oilMassFractions) {
                array.add(toJson(omf));
            }
            return array;
        }

        private static JSONObject toJson(OilMassFraction omf) {
            JSONObject json = new JSONObject();
            json.put("id", omf.getId());
            json.put("seed", omf.getSeed());
            json.put("seedHumidity", omf.getSeedHumidity());
            json.put("husk", omf.getHusk());
            json.put("huskHumidity", omf.getHuskHumidity());
            json.put("forpress", toForpressJson(omf.getForpressCakes()));
            return json;
        }

        private static JSONArray toForpressJson(Set<ForpressCakeDaily> forpressCakes) {
            JSONArray array = new JSONArray();
            for (ForpressCakeDaily fcd : forpressCakes) {
                array.add(toJson(fcd));
            }
            return array;
        }

        private static JSONObject toJson(ForpressCakeDaily fcd) {
            JSONObject json = new JSONObject();
            json.put("id", fcd.getId());
            json.put("forpress", fcd.getForpress().getName());
            json.put("oiliness", fcd.getOiliness());
            json.put("humidity", fcd.getHumidity());
            return json;
        }

        private static JSONArray toDailyJson(Set<VRODaily> dailies) {
            JSONArray array = new JSONArray();

            for (VRODaily daily : dailies){
                array.add(toJson(daily));
            }

            return array;
        }

        private static JSONObject toJson(VRODaily daily) {
            JSONObject json = new JSONObject();
            json.put("id", daily.getId());
            json.put("kernelHumidity", daily.getKernelHumidity());
            json.put("huskHumidity", daily.getHuskHumidity());
            json.put("huskSoreness", daily.getHuskSoreness());
            json.put("kernelPercent", daily.getKernelPercent());
            json.put("huskPercent", daily.getHuskPercent());
            return json;
        }

        private static JSONArray toOilJson(Set<VROOil> oils) {
            JSONArray array = new JSONArray();
            for (VROOil oil : oils) {
                array.add(toJson(oil));
            }
            return array;
        }

        private static JSONObject toJson(VROOil oil) {
            JSONObject json = new JSONObject();
//            private int id;
            json.put("id", oil.getId());
//            private float acid;
            json.put("acid", oil.getAcid());
//            private float peroxide;
            json.put("peroxide", oil.getPeroxide());
//            private float phosphorus;
            json.put("phosphorus", oil.getPhosphorus());
//            private int color;
            json.put("color", oil.getColor());

            return json;
        }

        private static JSONObject toCakeJson(Set<ForpressCake> forpressCakes) {
            JSONObject json = new JSONObject();
            for (ForpressCake cake : forpressCakes){
                json.put(cake.getForpress().getName(), toJson(cake));
            }
            return json;
        }

        private static JSONObject toJson(ForpressCake cake) {
            JSONObject json = new JSONObject();

            json.put("id", cake.getId());
            json.put("forpress", cake.getForpress().getName());
            json.put("humidity", cake.getHumidity());
            json.put("oiliness", cake.getOiliness());

            return json;
        }

        private static JSONArray toCrudeJson(List<VROCrude> crudes) {
            JSONArray array = new JSONArray();
            Collections.sort(crudes);
            for (VROCrude crude : crudes) {
                array.add(toJson(crude));
            }
            return array;
        }

        private static JSONObject toJson(VROCrude crude) {
            JSONObject json = new JSONObject();
//            private int id;
            json.put("id", crude.getId());
//            private Timestamp Time;
            json.put("Time", crude.getTime().toString());
//            private float humidityBefore;
            json.put("humidityBefore", crude.getHumidityBefore());
//            private float sorenessBefore;
            json.put("sorenessBefore", crude.getSorenessBefore());
//            private float humidityAfter;
            json.put("humidityAfter", crude.getHumidityAfter());
//            private float sorenessAfter;
            json.put("sorenessAfter", crude.getSorenessAfter());
//            private float huskiness;
            json.put("huskiness", crude.getHuskiness());
//            private float kernelOffset;
            json.put("kernelOffset", crude.getKernelOffset());
//            private float pulpHumidity;
            json.put("pulpHumidity1", crude.getPulpHumidity1());
            json.put("pulpHumidity2", crude.getPulpHumidity2());

            json.put("cakes", toCakeJson(crude.getForpressCakes()));
            return json;
        }
    }
}
