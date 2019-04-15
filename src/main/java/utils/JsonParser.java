package utils;

import entity.*;
import entity.answers.IAnswer;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.laboratory.CakeAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.probes.OilProbe;
import entity.laboratory.probes.SunProbe;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionOIl;
import entity.laboratory.subdivisions.extraction.ExtractionRaw;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.vro.*;
import entity.laboratory.transportation.CakeTransportationAnalyses;
import entity.laboratory.transportation.OilTransportationAnalyses;
import entity.laboratory.transportation.SunTransportationAnalyses;
import entity.organisations.Organisation;
import entity.seals.Seal;
import entity.transport.ActionTime;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.Weight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Timestamp;
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
            json.put("weights", toJson(transportation.getWeights()));
            json.put("analyses", toJson(transportation.getSunAnalyses(), transportation.getOilAnalyses(), transportation.getCakeAnalyses()));
            json.put("archive", transportation.isArchive());
        }
        return json;
    }

    private static JSONObject toJson(Set<SunTransportationAnalyses> sunAnalyses, Set<OilTransportationAnalyses> oilAnalyses, Set<CakeTransportationAnalyses> cakeAnalyses) {
        JSONObject json = new JSONObject();
        
        JSONArray sun = new JSONArray();
        for (SunTransportationAnalyses a : sunAnalyses){
            sun.add(toJson(a.getAnalyses()));
        }
        if (sun.size() > 0) {
            json.put("sun", sun);
        }

        JSONArray oil = new JSONArray();
        for (OilTransportationAnalyses a : oilAnalyses){
            oil.add(toJson(a.getAnalyses()));
        }
        if (oil.size() > 0) {
            json.put("oil", oil);
        }

        JSONArray cake = new JSONArray();
        for (CakeTransportationAnalyses a : cakeAnalyses){
            cake.add(toJson(a.getAnalyses()));
        }
        if(cake.size() > 0) {
            json.put("cake", cake);
        }
        

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
        json.put("soap", analyses.getSoap());
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
        json.put("humidity", analyses.getHumidity());
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

    private static JSONArray toJson(Set<Weight> weights) {
        JSONArray array = new JSONArray();
        for (Weight weight : weights){
            array.add(toJson(weight));
        }
        return array;
    }

    private static JSONObject toJson(Weight weight) {
        JSONObject json = new JSONObject();
        json.put("id", weight.getId());
        json.put("brutto", weight.getBrutto());
        json.put("brutto_time", toJson(weight.getBruttoTime()));
        json.put("tara", weight.getTara());
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

    public static class Laboratory {
        public static class Extraction {
            public static JSONObject toJson(ExtractionTurn turn) {
                JSONObject json = new JSONObject();
                json.put("id", turn.getId());
                json.put("number", turn.getNumber());
                json.put("date", turn.getDate().toString());
                json.put("crudes", toJson(turn.getCrudes()));
                json.put("raws", toRawJson(turn.getRaws()));
                json.put("oil", toOilJson(turn.getOils()));
                json.put("hash", turn.hashCode());

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
//                private int id;
                json.put("id", oil.getId());
//                private float humidity;
                json.put("humidity", oil.getHumidity());
//                private float acid;
                json.put("acid", oil.getAcid());
//                private float peroxide;
                json.put("peroxide", oil.getPeroxide());
//                private float phosphorus;
                json.put("phosphorus", oil.getPhosphorus());
//                private float explosionT;
                json.put("explosionT", oil.getExplosionT());
                return json;
            }

            private static JSONObject toRawJson(Set<ExtractionRaw> raws) {
                JSONObject json = new JSONObject();
                for (ExtractionRaw raw : raws){
                    json.put(raw.getTime().toString(), toJson(raw));
                }
                return json;
            }

            private static JSONObject toJson(ExtractionRaw raw) {
                JSONObject json = new JSONObject();
                json.put("id", raw.getId());
                json.put("protein", raw.getProtein());
                json.put("cellulose", raw.getCellulose());
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
            json.put("date", turn.getDate().toString());
            json.put("number", turn.getNumber());
            json.put("crudes", toCrudeJson(turn.getCrudes()));
            json.put("oil", toOilJson(turn.getOils()));
            json.put("dailies", toDailyJson(turn.getDailies()));
            json.put("hash", turn.hashCode());

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
            json.put("pulpHumidity", crude.getPulpHumidity());

            json.put("cakes", toCakeJson(crude.getForpressCakes()));
            return json;
        }
    }
}
