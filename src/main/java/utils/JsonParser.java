package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Person;
import entity.Product;
import entity.Worker;
import entity.answers.IAnswer;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.documents.LoadPlan;
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

    private static JSONObject toJson(Worker worker) {
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
            json.put("archive", transportation.isArchive());
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


}
