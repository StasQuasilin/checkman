package utils;

import entity.AnalysesType;
import entity.Person;
import entity.Product;
import entity.Worker;
import entity.answers.IAnswer;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.laboratory.CakeAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.probes.SunProbe;
import entity.laboratory.transportation.CakeTransportationAnalyses;
import entity.laboratory.transportation.OilTransportationAnalyses;
import entity.laboratory.transportation.SunTransportationAnalyses;
import entity.organisations.Organisation;
import entity.transport.ActionTime;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.Weight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.Set;

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
//        private ActionTime createTime;
        json.put("createTime", toJson(analyses.getCreateTime()));
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
}
