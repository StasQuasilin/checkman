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
import entity.laboratory.transportation.CakeTransportationAnalyses;
import entity.laboratory.transportation.OilTransportationAnalyses;
import entity.laboratory.transportation.SunTransportationAnalyses;
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
    public static JSONObject toJson(Organisation organisation) {
        JSONObject json = new JSONObject();
        if (organisation != null) {
            json.put("id", organisation.getId());
            json.put("type", organisation.getType());
            json.put("name", organisation.getName());
            json.put("value", organisation.getValue());
        }
        return json;
    }

    public static JSONObject toJson(Deal deal) {
        JSONObject json = new JSONObject();
        json.put("id", deal.getId());
        json.put("date", deal.getDate().toString());
        json.put("date_to", deal.getDateTo().toString());
        json.put("organisation", toJson(deal.getOrganisation()));
        json.put("visibility", deal.getDocumentOrganisation().getValue());
        json.put("product", toJson(deal.getProduct()));
        json.put("quantity", deal.getQuantity());
        json.put("done", deal.getDone());
        json.put("price", deal.getPrice());
        json.put("creator", toJson(deal.getCreator()));
        json.put("hash", deal.hashCode());
        json.put("unit", deal.getUnit().getName());
        json.put("type", deal.getType().toString());
        return json;
    }

    public static JSONObject toJson(Worker worker) {
        JSONObject json = new JSONObject();
        json.put("id", worker.getId());
        json.put("person", toJson(worker.getPerson()));
        return json;
    }

    private static JSONObject toJson(Person person) {
        JSONObject json = new JSONObject();
        json.put("id", person.getId());
        json.put("forename", person.getForename());
        json.put("surname", person.getSurname());
        json.put("patronymic", person.getPatronymic());
        json.put("value", person.getValue());
        json.put("phones", toPhoneJson(person.getPhones()));
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
        json.put("id", number.getId());
        json.put("number", number.getNumber());
        return json;
    }

    private static JSONObject toJson(Product product) {
        JSONObject json = new JSONObject();
        json.put("id", product.getId());
        json.put("name", product.getName());
        if (product.getProductGroup() != null) {
            json.put("group", product.getProductGroup().getName());
        }
        json.put("analyses", product.getAnalysesType().toString());
        return json;
    }

    public static JSONObject toJson(IAnswer answer) {
        JSONObject json = new JSONObject();
        json.put("status", answer.status());
        for (Map.Entry<String, String> entry : answer.getParams().entrySet()){
            json.put(entry.getKey(), entry.getValue());
        }
        return json;
    }

    public static JSONObject toJson(LoadPlan lp) {
        JSONObject json = new JSONObject();
        json.put("id", lp.getId());
        json.put("date", lp.getDate().toString());
        json.put("plan", lp.getPlan());
        json.put("customer", lp.getCustomer().toString());
        json.put("transportation", toJson(lp.getTransportation()));
        json.put("hash", lp.hashCode());
        return json;
    }

    public static JSONObject toJson(Transportation transportation) {
        JSONObject json = new JSONObject();
        if (transportation != null){
            json.put("id", transportation.getId());
            json.put("vehicle", toJson(transportation.getVehicle()));
            json.put("driver", toJson(transportation.getDriver()));
            json.put("timeIn", toJson(transportation.getTimeIn()));
            json.put("timeOut", toJson(transportation.getTimeOut()));
            json.put("hash", transportation.hashCode());
            json.put("weight", toJson(transportation.getWeight()));
            json.put("analyses", toJson(transportation.getSunAnalyse(), transportation.getOilAnalyses(), transportation.getCakeAnalyses()));
            json.put("any", transportation.anyAction());
            json.put("archive", transportation.isArchive());
        }
        return json;
    }

    private static JSONObject toJson(SunAnalyses sunAnalyses, OilAnalyses oilAnalyses, CakeAnalyses cakeAnalyses) {
        JSONObject json = new JSONObject();
        
        json.put("sun", toJson(sunAnalyses));
        json.put("oil", toJson(oilAnalyses));
        json.put("cake", toJson(cakeAnalyses));


        return json;
    }

    private static JSONObject toJson(CakeAnalyses analyses) {
        JSONObject json = new JSONObject();

        json.put("id", analyses.getId());
//        private float humidity;
        json.put("humidity", analyses.getHumidity());
//        private float protein;
        json.put("protein", analyses.getProtein());
//        private float cellulose;
        json.put("cellulose", analyses.getCellulose());
//        private float oiliness;
        json.put("oiliness", analyses.getOiliness());

        json.put("create", toJson(analyses.getCreateTime()));

        if (analyses.getCreator() != null) {
            json.put("creator", toJson(analyses.getCreator()));
        }

        return json;
    }

    private static JSONObject toJson(OilAnalyses analyses) {
        JSONObject json = new JSONObject();
//        private int id;
        json.put("id", analyses.getId());
//        private boolean organoleptic;
        json.put("organoleptic", analyses.isOrganoleptic());
//        private int color;
        json.put("color", analyses.getColor());
//        private float acidValue;
        json.put("acid", analyses.getAcidValue());
//        private float peroxideValue;
        json.put("peroxide", analyses.getPeroxideValue());
//        private float phosphorus;
        json.put("phosphorus", analyses.getPhosphorus());
//        private float humidity;
        json.put("humidity", analyses.getHumidity());
//        private float soap;
        json.put("soap", analyses.isSoap());
//        private float wax;
        json.put("wax", analyses.getWax());
//        private ActionTime createTime;
        json.put("create", toJson(analyses.getCreateTime()));
//        private Worker creator;
        if (analyses.getCreator() != null) {
            json.put("creator", toJson(analyses.getCreator()));
        }
        return json;
    }

    private static JSONObject toJson(SunAnalyses analyses) {
        JSONObject json = new JSONObject();
        json.put("id", analyses.getId());
//        private float oiliness;
        json.put("oiliness", analyses.getOiliness());
//        private float humidity;
        json.put("humidity", analyses.getHumidity1());
//        private float soreness;
        json.put("soreness", analyses.getSoreness());
//        private float oilImpurity;
        json.put("oilImpurity", analyses.getOilImpurity());
//        private float acidValue;
        json.put("acidValue", analyses.getAcidValue());
        json.put("contamination", analyses.isContamination());
//        private ActionTime createTime;
        json.put("create", toJson(analyses.getCreateTime()));
//        private Worker creator;
        if (analyses.getCreator() != null) {
            json.put("creator", toJson(analyses.getCreator()));
        }
        return json;
    }

    private static JSONObject toJson(Weight weight) {
        JSONObject json = new JSONObject();
        json.put("id", weight.getId());
        json.put("brutto", weight.getBrutto());
        json.put("brutto_time", toJson(weight.getBruttoTime()));
        json.put("tara", weight.getTara());
        json.put("netto", weight.getNetto());
        json.put("correction", weight.getCorrection());
        json.put("tara_time", toJson(weight.getTaraTime()));
        return json;
    }

    private static JSONObject toJson(ActionTime actionTime) {
        JSONObject json = new JSONObject();
        if (actionTime != null){
            json.put("id", actionTime.getId());
            json.put("creator", toJson(actionTime.getCreator()));
            json.put("time", actionTime.getTime().toString());
        }
        return json;
    }

    public static JSONObject toJson(Driver driver) {
        JSONObject json = new JSONObject();
        if (driver != null){
            json.put("id", driver.getId());
            json.put("person", toJson(driver.getPerson()));
            json.put("organisation", toJson(driver.getOrganisation()));
        }
        return json;
    }

    public static JSONObject toJson(Vehicle vehicle) {
        JSONObject json = new JSONObject();
        if (vehicle != null){
            json.put("id", vehicle.getId());
            json.put("model", vehicle.getModel());
            json.put("number", vehicle.getNumber());
            if (vehicle.getTrailer() != null){
                json.put("trailer", vehicle.getTrailer());
            }
        }
        return json;
    }

    public static JSONObject toLogisticJson(LoadPlan loadPlan) {
        JSONObject json = new JSONObject();
        json.put("id", loadPlan.getId());
        json.put("date", loadPlan.getDate().toString());
        json.put("type", loadPlan.getDeal().getType().toString());
        json.put("organisation", toJson(loadPlan.getDeal().getOrganisation()));
        json.put("product", toJson(loadPlan.getDeal().getProduct()));
        json.put("quantity", loadPlan.getPlan());
        json.put("unit", loadPlan.getDeal().getUnit().getName());
        json.put("realisation", loadPlan.getDocumentOrganisation().getValue());
        json.put("transportation", toJson(loadPlan.getTransportation()));
        json.put("hash", loadPlan.hashCode());

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

    public static JSONObject toJson(User user) {
        JSONObject json = toJson(user.getWorker());
        json.put("uid", user.getUid());
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
                transportation.getSunAnalyse(),
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
//                private Timestamp time;
                json.put("time", crude.getTime().toString());
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
//            private Timestamp time;
            json.put("time", crude.getTime().toString());
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
