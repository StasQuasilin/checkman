package utils;

import bot.BotUID;
import constants.Constants;
import controllers.IServlet;
import entity.*;
import entity.answers.Answer;
import entity.bot.UserNotificationSetting;
import entity.chat.Chat;
import entity.chat.ChatMessage;
import entity.documents.Shipper;
import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.probes.*;
import entity.laboratory.storages.StorageAnalyses;
import entity.laboratory.storages.StorageTurn;
import entity.laboratory.subdivisions.extraction.*;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.laboratory.subdivisions.vro.*;
import entity.laboratory.turn.LaboratoryTurn;
import entity.laboratory.turn.LaboratoryTurnWorker;
import entity.log.Change;
import entity.log.ChangeLog;
import entity.organisations.Organisation;
import entity.production.Turn;
import entity.products.Product;
import entity.reports.ManufactureReport;
import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.storages.Storage;
import entity.storages.StoragePeriodPoint;
import entity.transport.*;
import entity.weight.Weight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.storages.PointScale;
import utils.storages.StorageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class JsonParser implements Constants{

    private static final String MESSAGE = "message";
    private static final String IS_GROUP = "isGroup";
    private static final String EPOCH = "epoch";
    private static final String DELIVERED = "delivered";
    private static final String READ = "read";
    private static final String REGISTRATION = "registration";
    private static final String OFFICE = "office";
    private static final String AMOUNT = Constants.AMOUNT;
    private static final String LICENSE = "license";
    private static final String DRY = "dry";
    private static final String SCALE = IServlet.SCALE;
    private static final String OLD = "old";
    private static final String GRANULAS = "granulas";

    public JSONObject toJson(Organisation organisation) {
        JSONObject json = pool.getObject();
        if (organisation != null) {
            json.put(ID, organisation.getId());
            json.put(TYPE, organisation.getType());
            json.put(NAME, organisation.getName());
            json.put(VALUE, organisation.getValue());
        }
        return json;
    }
    
    public static final String DONE = "done";
    public static final String PRICE = "price";
    
    public JSONObject toJson(Worker worker) {
        JSONObject json = pool.getObject();
        if (worker != null) {
            json.put(ID, worker.getId());
            json.put(PERSON, toJson(worker.getPerson()));
            json.put(OFFICE, worker.getOffice());
        }
        return json;
    }

    final static String FORENAME = "forename";
    final static String SURNAME = "surname";
    final static String PATRONYMIC = "patronymic";
    final static String PHONES = "phones";

    private JSONObject toJson(Person person) {
        JSONObject json = pool.getObject();
        json.put(ID, person.getId());
        json.put(FORENAME, person.getForename());
        json.put(SURNAME, person.getSurname());
        json.put(PATRONYMIC, person.getPatronymic());
        json.put(VALUE, person.getValue());
        json.put(PHONES, toPhoneJson(person.getPhones()));
        return json;
    }

    private JSONArray toPhoneJson(Set<PhoneNumber> phones) {
        JSONArray array = new JSONArray();
        for (PhoneNumber number : phones) {
            array.add(toJson(number));
        }
        return array;
    }

    private JSONObject toJson(PhoneNumber number) {
        JSONObject json = new JSONObject();
        json.put(ID, number.getId());
        json.put(NUMBER, number.getNumber());
        return json;
    }

    final static String ANALYSES = "analyses";

    private JSONObject toJson(Product product) {
        JSONObject json = pool.getObject();
        if (product != null) {
            json.put(ID, product.getId());
            json.put(NAME, product.getName());
            if (product.getAnalysesType() != null) {
                json.put(ANALYSES, product.getAnalysesType().toString());
            }
        }
        return json;
    }
    
    public static final String STATUS = "status";

    @Deprecated
    public JSONObject toJson(Answer answer) {
        JSONObject json = pool.getObject();
        json.put(STATUS, answer.status());
        for (Map.Entry<String, Object> entry : answer.getParams().entrySet()){
            json.put(entry.getKey(), entry.getValue());
        }
        return json;
    }

    public static final String PLAN = "plan";

    final static String VEHICLE = "vehicle";
    final static String DRIVER = "driver";
    final static String TIME_IN = "timeIn";
    final static String TIME_OUT = "timeOut";
    final static String WEIGHT = "weight";
    final static String NOTES = "notes";
    final static String ANY = "any";
    final static String ARCHIVE = "archive";

    private JSONArray toNotesJson(List<DocumentNote> notes) {
        final JSONArray array = pool.getArray();
        if (notes != null) {
            for (DocumentNote note : notes) {
                array.add(toJson(note));
            }
        }
        return array;
    }

    final static String NOTE = "note";
    public JSONObject toJson(DocumentNote note) {
        JSONObject json = pool.getObject();
        json.put(ID, note.getId());
        json.put(TIME, note.getTime().toString());
        json.put(NOTE, note.getNote());
        json.put(CREATOR, toJson(note.getCreator()));
        return json;
    }

    public static final String SUN = "sun";
    public static final String OIL = "oil";
    public static final String CAKE = "cake";

    private JSONObject toJson(SunAnalyses sunAnalyses, OilAnalyses oilAnalyses, MealAnalyses mealAnalyses) {
        JSONObject json = pool.getObject();
        json.put(SUN, toJson(sunAnalyses));
        json.put(OIL, toJson(oilAnalyses));
        json.put(CAKE, toJson(mealAnalyses));
        return json;
    }

    public static final String PROTEIN = "protein";
    public static final String CELLULOSE = "cellulose";

    private JSONObject toJson(MealAnalyses analyses) {
        JSONObject json = new JSONObject();
        if (analyses != null) {
            json.put(ID, analyses.getId());
            json.put(HUMIDITY, analyses.getHumidity());
            json.put(PROTEIN, analyses.getProtein());
            json.put(CELLULOSE, analyses.getCellulose());
            json.put(OILINESS, analyses.getOiliness());

            json.put(CREATE, toJson(analyses.getCreateTime()));
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
    
    private JSONObject toJson(OilAnalyses analyses) {
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

    private JSONObject toJson(SunAnalyses analyses) {
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

        }
        return json;
    }

    public static final String BRUTTO = "brutto";
    public static final String BRUTTO_TIME = "brutto_Time";
    public static final String TARA = "tara";
    public static final String NETTO = "netto";
    public static final String CORRECTION = "correction";
    public static final String TARA_TIME = "tara_Time";

    private JSONObject toJson(Weight weight) {
        JSONObject json = pool.getObject();
        if (weight != null) {
            json.put(ID, weight.getId());
            json.put(BRUTTO, weight.getBrutto());
            json.put(TARA, weight.getTara());
            json.put(NETTO, weight.getNetto());
            json.put(CORRECTION, weight.getCorrection());
            json.put(BRUTTO_TIME, toJson(weight.getBruttoTime()));
            json.put(TARA_TIME, toJson(weight.getTaraTime()));
        }
        return json;
    }

    final static String CREATOR = "creator";
    final static String TIME = "time";

    private JSONObject toJson(ActionTime actionTime) {
        JSONObject json = pool.getObject();
        if (actionTime != null){
            json.put(ID, actionTime.getId());
            json.put(CREATOR, toJson(actionTime.getCreator()));
            json.put(TIME, actionTime.getTime().toString());
        }
        return json;
    }

    final static String PERSON = "person";

    public JSONObject toJson(Driver driver) {
        JSONObject json = pool.getObject();
        if (driver != null){
            json.put(ID, driver.getId());
            json.put(PERSON, toJson(driver.getPerson()));
            json.put(ORGANISATION, toJson(driver.getOrganisation()));
            if (U.exist(driver.getLicense())){
                json.put(LICENSE, driver.getLicense());
            }
            if (driver.getVehicle() != null){
                json.put(VEHICLE, toJson(driver.getVehicle()));
            }

        }
        return json;
    }

    final static String MODEL = "model";
    final static String NUMBER = "number";
    final static String TRAILER = "trailer";

    public JSONObject toJson(Vehicle vehicle) {
        JSONObject json = pool.getObject();
        if (vehicle != null){
            json.put(ID, vehicle.getId());
            json.put(MODEL, vehicle.getModel());
            json.put(NUMBER, vehicle.getNumber());
        }
        return json;
    }
    final JsonPool pool = JsonPool.getPool();
    final static String ID = "id";
    final static String DATE = "date";
    final static String TYPE = "type";
    final static String ORGANISATION = "organisation";
    final static String PRODUCT = "product";
    final static String HASH = "hash";

    public static final String MANAGER = "manager";
    public static final String TURN = "turn";
    public static final String TRANSPORTER = Constants.TRANSPORTER;
    public JSONObject toJson(SunProbe sun) {
        JSONObject json = new JSONObject();
        json.put(ID, sun.getId());
        json.put(TYPE, AnalysesType.sun.toString());
        json.put(DATE, sun.getAnalyses().getCreateTime().getTime().toString());
        if (U.exist(sun.getManager())) {
            json.put(MANAGER, sun.getManager());
        }
        if (U.exist(sun.getOrganisation())) {
            json.put(ORGANISATION, sun.getOrganisation());
        }
        json.put(ANALYSES, toJson(sun.getAnalyses()));
        return json;
    }

    public JSONObject toJson(OilProbe oil) {
        JSONObject json = new JSONObject();
        json.put(ID, oil.getId());
        json.put(TYPE, AnalysesType.oil.toString());
        json.put(DATE, oil.getAnalyses().getCreateTime().getTime().toString());
        if (oil.getManager() != null) {
            json.put(MANAGER, oil.getManager());
        }
        if (oil.getOrganisation() != null) {
            json.put(ORGANISATION, oil.getOrganisation());
        }
        json.put(ANALYSES, toJson(oil.getAnalyses()));
        json.put(HASH, oil.hashCode());
        return json;
    }

    public static final String UID = "uid";

    public JSONObject toJson(User user) {
        JSONObject json = toJson(user.getWorker());
        json.put(UID, user.getUid());
        return json;
    }

    public JSONObject toJson(Seal seal) {
        JSONObject json = new JSONObject();
        json.put("id", seal.getId());
        json.put("number", seal.getNumber());
        return json;
    }

    public JSONObject toJson(BotUID botUID) {
        JSONObject json = new JSONObject();
        json.put("id", botUID.getId());
        json.put("uid", botUID.getUid());
        return json;
    }

    public JSONObject toJson(UserNotificationSetting setting) {
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

    public JSONArray toJson(Collection<Driver> drivers, HashMap<Integer, Vehicle> vehicles) {
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

    public JSONArray toJson(Collection<Organisation> values) {
        JSONArray array = new JSONArray();
        for (Organisation organisation : values){
            array.add(toJson(organisation));
        }
        return array;
    }

    private JSONArray toJson(ArrayList<ChangeLog> logs) {
        JSONArray array = new JSONArray();
        for(ChangeLog log : logs) {
            array.add(toJson(log));
        }
        return array;
    }

    private JSONObject toJson(ChangeLog log) {
        JSONObject json = new JSONObject();
        json.put("id", log.getId());
        json.put("date", log.getTime().toString());
        json.put("message", log.getLabel());
        json.put("creator", log.getCreator().getValue());
        json.put("changes", toChangesJson(log.getChanges()));
        return json;
    }

    private JSONArray toChangesJson(Set<Change> changes) {
        JSONArray array = new JSONArray();
        for (Change change : changes){
            array.add(toJson(change));
        }
        return array;
    }

    private JSONObject toJson(Change change) {
        JSONObject json = new JSONObject();

        json.put("field", change.getField());

        return json;
    }

    public JSONObject toJson(KPOPart part) {
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

    public JSONObject toJson(LaboratoryTurn laboratoryTurn) {
        JSONObject json = pool.getObject();
        json.put(ID, laboratoryTurn.getId());
        json.put(TURN, toJson(laboratoryTurn.getTurn()));
        JSONArray array = pool.getArray();
        for (LaboratoryTurnWorker worker : laboratoryTurn.getWorkers()){
            array.add(toJson(worker.getWorker()));
        }
        json.put(WORKERS, array);
        return json;
    }

    public JSONObject toJson(Turn turn, List<LaboratoryTurn> query) {
        JSONObject json = toJson(turn);
        json.put("laboratory", toLaboratoryJson(query));
        int hash = turn.hashCode();

        json.put("hash", hash);
        return json;
    }

    public JSONObject toJson(Turn turn) {
        JSONObject json = new JSONObject();
        json.put("id", turn.getId());
        json.put("date", turn.getDate().toString());
        json.put("number", turn.getNumber());
        return json;
    }

    private JSONArray toLaboratoryJson(List<LaboratoryTurn> query) {
        JSONArray array = new JSONArray();
        for (LaboratoryTurn turn : query){
            array.add(toJson(turn));
        }
        return array;
    }

    public JSONObject toJson(StorageTurn turn) {
        JSONObject json = new JSONObject();
        json.put("id", turn.getId());
        json.put("turn", toJson(turn.getTurn()));
        json.put("analyses", toAnalysesJson(turn.getAnalyses()));

        return json;
    }

    private JSONArray toAnalysesJson(Set<StorageAnalyses> analyses) {
        JSONArray array = new JSONArray();
        for (StorageAnalyses analyse : analyses){
            array.add(toJson(analyse));
        }
        return array;
    }
    
    public static final String STORAGE = "storage";
    
    public JSONObject toJson(StorageAnalyses analyse) {
        JSONObject json = pool.getObject();
        json.put(ID, analyse.getId());
        json.put(STORAGE, analyse.getStorage().getName());
        json.put(DATE, analyse.getDate().toString());
        json.put(OIL, toJson(analyse.getOilAnalyses()));
        return json;
    }

    public static final String BATCH = "batch";
    public JSONArray toJson(HashMap<SealBatch, List<Seal>> map) {
        JSONArray array = pool.getArray();
        for (Map.Entry<SealBatch, List<Seal>> entry : map.entrySet()){
            array.add(toJson(entry.getKey(), entry.getValue()));
        }
        return array;
    }
    public static final String SEALS = "seals";
    private JSONObject toJson(SealBatch sealBatch, List<Seal> seals) {
        JSONObject json = pool.getObject();
        json.put(BATCH, toJson(sealBatch));
        json.put(SEALS, toSealJson(seals));
        return json;
    }

    private JSONArray toSealJson(List<Seal> seals) {
        JSONArray array = pool.getArray();
        for (Seal seal : seals){
            array.add(seal.getNumber());
        }
        return array;
    }

    public static final String TITLE = "title";
    public static final String FREE = "free";
    public static final String TOTAL = "summary";
    public JSONObject toJson(SealBatch batch) {
        JSONObject json = pool.getObject();
        json.put(ID, batch.getId());
        json.put(TITLE, batch.getTitle());
        json.put(DATE, toJson(batch.getCreated()));
        json.put(FREE, batch.getFree());
        json.put(TOTAL, batch.getTotal());
        json.put(HASH, batch.hashCode());
        return json;
    }

    public static final String CRUDES = "crude";
    public static final String STORAGE_PROTEIN = "storageProtein";
    public static final String STORAGE_GREASE = "storageGrease";
    public static final String TURN_PROTEIN = "turnProtein";
    public static final String TURN_GREASE = "turnGrease";

    final JSONParser parser = new JSONParser();
    public synchronized Object parse(BufferedReader reader) throws IOException, ParseException {
        return parser.parse(reader);
    }

    public JSONObject parse(String string) throws ParseException {
        return (JSONObject) parser.parse(string);
    }

//    public JSONObject toJson(ExtractionTurn turn) {
//        JSONObject json = new JSONObject();
//        json.put(ID, turn.getId());
//        json.put(NUMBER, turn.getTurn().getNumber());
//        json.put(DATE, turn.getTurn().getDate().toString());
//        json.put(CRUDES, toJson(turn.getCrudes()));
//        json.put(STORAGE_PROTEIN, toRawJson(turn.getProtein()));
//        json.put(STORAGE_GREASE, toGreaseJson(turn.getGreases()));
//        json.put(OIL, toOilJson(turn.getOils()));
//        json.put(TURN_PROTEIN, toTurnJson(turn.getTurnProteins()));
//        json.put(TURN_GREASE, toTurnGrease(turn.getTurnGreases()));
//        json.put(CELLULOSE, toTurnCellulose(turn.getCellulose()));
//        json.put(GRANULAS, toMealGranules(turn.getGranules()));
//
//        return json;
//    }

    private JSONArray toMealGranules(Set<MealGranules> granules) {
        JSONArray array = pool.getArray();
        for(MealGranules g : granules){
            array.add(g.toJson());
        }
        return array;
    }

    private JSONArray toTurnCellulose(Set<TurnCellulose> cellulose) {
        JSONArray array = pool.getArray();
        for (TurnCellulose c : cellulose){
            array.add(toJson(c));
        }
        return array;
    }

    private JSONObject toJson(TurnCellulose c) {
        JSONObject json = pool.getObject();
        json.put(ID, c.getId());
        json.put(CELLULOSE, c.getCellulose());
        json.put(HUMIDITY, c.getHumidity());
        json.put(DRY, c.DryRecalculation());
        return json;
    }

    private JSONObject toGreaseJson(Set<StorageGrease> greases) {
        JSONObject json = new JSONObject();
        for (StorageGrease grease : greases) {
            json.put(grease.getTime().toString(), toJson(grease));
        }
        return json;
    }

    private JSONObject toJson(StorageGrease grease) {
        JSONObject  json = new JSONObject();
        json.put("id", grease.getId());
        json.put("grease", grease.getGrease());
        json.put("humidity", grease.getHumidity());
        return json;
    }

    private JSONArray toTurnGrease(Set<TurnGrease> turnGreases) {
        JSONArray array = new JSONArray();
        for (TurnGrease grease : turnGreases){
            array.add(toJson(grease));
        }
        return array;
    }

    private JSONObject toJson(TurnGrease grease) {
        JSONObject json = new JSONObject();
        json.put("id", grease.getId());
        json.put("grease", grease.getGrease());
        json.put("humidity", grease.getHumidity());
        return json;
    }

    private JSONArray toTurnJson(Set<TurnProtein> turns) {
        JSONArray array = new JSONArray();
        for (TurnProtein protein : turns){
            array.add(toJson(protein));
        }
        return array;
    }

    private JSONObject toJson(TurnProtein protein) {
        JSONObject json = new JSONObject();
        json.put("id", protein.getId());
        json.put("protein", protein.getProtein());
        json.put("humidity", protein.getHumidity());
        json.put("nuclear", protein.getNuclearGrease());
        return json;
    }

    private JSONArray toOilJson(Set<ExtractionOIl> oils) {
        JSONArray array = new JSONArray();
        for (ExtractionOIl oil : oils){
            array.add(toJson(oil));
        }
        return array;
    }

    private JSONObject toJson(ExtractionOIl oil) {
        JSONObject json = new JSONObject();
        json.put("id", oil.getId());
        json.put("humidity", oil.getHumidity());
        json.put("acid", oil.getAcid());
        json.put("peroxide", oil.getPeroxide());
        json.put("phosphorus", oil.getPhosphorus());
        json.put("explosionT", oil.getExplosionT());
        return json;
    }

    private JSONObject toRawJson(Set<StorageProtein> raws) {
        JSONObject json = new JSONObject();
        for (StorageProtein raw : raws){
            json.put(raw.getTime().toString(), toJson(raw));
        }
        return json;
    }

    private JSONObject toJson(StorageProtein raw) {
        JSONObject json = new JSONObject();
        json.put("id", raw.getId());
        json.put("protein", raw.getProtein());
        json.put("humidity", raw.getHumidity());
        json.put("nuclear", raw.getNuclearGrease());
        return json;
    }

    private JSONArray toJson(List<ExtractionCrude> crudes) {
        JSONArray array = new JSONArray();
        Collections.sort(crudes);
        for (ExtractionCrude crude : crudes) {
            array.add(toJson(crude));
        }
        return array;
    }

    private JSONObject toJson(ExtractionCrude crude) {
        JSONObject json = pool.getObject();
        json.put("id", crude.getId());
        json.put("time", crude.getTime().toString());
        json.put("humidityIncome", crude.getHumidityIncome());
        json.put("oilinessIncome", crude.getOilinessIncome());
        json.put("fraction", crude.getFraction());
        json.put("miscellas", crude.getMiscellas());
        json.put("humidity", crude.getHumidity());
        json.put("dissolvent", crude.getDissolvent());
        json.put("grease", crude.getGrease());
        json.put("explosionTemperature", crude.getExplosionTemperature());
        json.put("oilHumidity", crude.getOilHumidity());

        return json;
    }

    public JSONObject toJson(VROTurn turn) {
        JSONObject json = new JSONObject();
        json.put(ID, turn.getId());
        json.put(DATE, turn.getTurn().getDate().toString());
        json.put(NUMBER, turn.getTurn().getNumber());
        json.put("crudes", toCrudeJson(turn.getCrudes()));
        json.put("oil", toVroOilJson(turn.getOils()));
        json.put("dailies", toDailyJson(turn.getDailies()));
        json.put("oilMass", toOilMass(turn.getOilMassFractions()));
        json.put("oilMassDry", toOilMassDry(turn.getOilMassFractionDries()));
        json.put("granules", toGranules(turn.getGranulesAnalyses()));
        json.put("protein", toProteinJson(turn.getProteins()));

        return json;
    }

    private JSONArray toProteinJson(Set<SunProtein> proteins) {
        JSONArray array = pool.getArray();
        for (SunProtein p : proteins){
            array.add(toJson(p));
        }
        return array;
    }

    private JSONObject toJson(SunProtein p) {
        JSONObject json = pool.getObject();
        json.put(ID, p.getId());
        json.put(PROTEIN, p.getProtein());
        json.put(HUMIDITY, p.getHumidity());
        json.put(DRY, p.DryRecalculation());
        return json;
    }

    private JSONArray toGranules(Set<GranulesAnalyses> granulesAnalyses) {
        JSONArray array = pool.getArray();
        for (GranulesAnalyses analyses : granulesAnalyses){
            array.add(toJson(analyses));
        }
        return array;
    }

    private JSONObject toJson(GranulesAnalyses analyses) {
        JSONObject json = pool.getObject();
        json.put(ID, analyses.getId());
        json.put("density", analyses.getDensity());
        json.put(HUMIDITY, analyses.getHumidity());
        json.put("dust", analyses.getDust());
        json.put("match", analyses.isMatch());
        return json;
    }

    private JSONArray toOilMassDry(Set<OilMassFractionDry> oilMassFractionDries) {
        JSONArray array = new JSONArray();
        for (OilMassFractionDry omf : oilMassFractionDries) {
            array.add(toJson(omf));
        }
        return array;
    }

    private JSONObject toJson(OilMassFractionDry omf) {
        JSONObject json = new JSONObject();
        json.put(ID, omf.getId());
        json.put("seed", omf.getSeed());
        json.put("husk", omf.getHusk());
        json.put("forpress", toForpressDryJson(omf.getForpressCakes()));
        return json;
    }

    private JSONArray toForpressDryJson(Set<ForpressCakeDailyDry> forpressCakes) {
        JSONArray array = new JSONArray();
        for (ForpressCakeDailyDry fcd : forpressCakes) {
            array.add(toJson(fcd));
        }
        return array;
    }

    private JSONObject toJson(ForpressCakeDailyDry fcd) {
        JSONObject json = new JSONObject();
        json.put(ID, fcd.getId());
        json.put("forpress", fcd.getForpress().getName());
        json.put("oilcake", fcd.getOilcake());
        return json;
    }

    private JSONArray toOilMass(Set<OilMassFraction> oilMassFractions) {
        JSONArray array = new JSONArray();
        for (OilMassFraction omf : oilMassFractions) {
            array.add(toJson(omf));
        }
        return array;
    }

    private JSONObject toJson(OilMassFraction omf) {
        JSONObject json = new JSONObject();
        json.put(ID, omf.getId());
        json.put("seedWet", omf.getSeedWet());
        json.put("seedHumidity", omf.getSeedHumidity());
        json.put("seedDry", omf.getSeedDry());
        json.put("huskWet", omf.getHuskWet());
        json.put("huskHumidity", omf.getHuskHumidity());
        json.put("huskDry", omf.getHuskDry());
        json.put("forpress", toForpressJson(omf.getForpressCakes()));
        return json;
    }

    private JSONArray toForpressJson(Set<ForpressCakeDaily> forpressCakes) {
        JSONArray array = new JSONArray();
        for (ForpressCakeDaily fcd : forpressCakes) {
            array.add(toJson(fcd));
        }
        return array;
    }

    private JSONObject toJson(ForpressCakeDaily fcd) {
        JSONObject json = new JSONObject();
        json.put(ID, fcd.getId());
        json.put("forpress", fcd.getForpress().getName());
        json.put("wet", fcd.getWet());
        json.put("humidity", fcd.getHumidity());
        json.put("dry", fcd.getDry());
        return json;
    }

    private JSONArray toDailyJson(Set<VRODaily> dailies) {
        JSONArray array = new JSONArray();

        for (VRODaily daily : dailies){
            array.add(toJson(daily));
        }

        return array;
    }

    private JSONObject toJson(VRODaily daily) {
        JSONObject json = pool.getObject();
        json.put(ID, daily.getId());
        json.put("kernelHumidity", daily.getKernelHumidity());
        json.put("huskHumidity", daily.getHuskHumidity());
        json.put("huskSoreness", daily.getHuskSoreness());
        json.put("kernelPercent", daily.getKernelPercent());
        json.put("huskPercent", daily.getHuskPercent());
        return json;
    }

    private JSONArray toVroOilJson(Set<VROOil> oils) {
        JSONArray array = new JSONArray();
        for (VROOil oil : oils) {
            array.add(toJson(oil));
        }
        return array;
    }

    private JSONObject toJson(VROOil oil) {
        JSONObject json = new JSONObject();
//            private int id;
        json.put(ID, oil.getId());
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

    private JSONObject toCakeJson(Set<ForpressCake> forpressCakes) {
        JSONObject json = new JSONObject();
        for (ForpressCake cake : forpressCakes){
            json.put(cake.getForpress().getName(), toJson(cake));
        }
        return json;
    }

    private JSONObject toJson(ForpressCake cake) {
        JSONObject json = new JSONObject();

        json.put(ID, cake.getId());
        json.put("forpress", cake.getForpress().getName());
        json.put("humidity", cake.getHumidity());
        json.put("oiliness", cake.getOiliness());

        return json;
    }

    private JSONArray toCrudeJson(List<VROCrude> crudes) {
        JSONArray array = new JSONArray();
        Collections.sort(crudes);
        for (VROCrude crude : crudes) {
            array.add(toJson(crude));
        }
        return array;
    }

    private JSONObject toJson(VROCrude crude) {
        JSONObject json = new JSONObject();
//            private int id;
        json.put(ID, crude.getId());
//            private Timestamp Time;
        json.put(TIME, crude.getTime().toString());
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

    public JSONObject toJson(ProbeTurn turn) {
        JSONObject json = toJson(turn.getTurn());
        json.put(SUN, toSunProbes(turn.getSunProbes()));
        json.put(OIL, toOilProbes(turn.getOilProbes()));
        json.put(MEAL, toMealProbes(turn.getMealProbes()));
        json.put(CAKE, toCakeProbes(turn.getCakeProbes()));
        return json;
    }

    private JSONArray toCakeProbes(Set<CakeProbe> cakeProbes) {
        JSONArray array = pool.getArray();
        for (CakeProbe probe : cakeProbes) {
            array.add(toJson(probe));
        }
        return array;
    }

    private JSONObject toJson(CakeProbe probe) {
        return pool.getObject();
    }

    private JSONArray toMealProbes(Set<MealProbe> mealProbes) {
        JSONArray array = pool.getArray();
        for (MealProbe probe : mealProbes) {
            array.add(toJson(probe));
        }
        return array;
    }

    private JSONObject toJson(MealProbe probe) {
        return pool.getObject();
    }

    private JSONArray toOilProbes(Set<OilProbe> oilProbes) {
        JSONArray array = pool.getArray();
        for (OilProbe probe : oilProbes) {
            array.add(toJson(probe));
        }
        return array;
    }

    private JSONArray toSunProbes(Set<SunProbe> sunProbes) {
        JSONArray array = pool.getArray();
        for (SunProbe probe : sunProbes) {
            array.add(toJson(probe));
        }
        return array;
    }

    public JSONObject toJson(Chat chat) {
        JSONObject json = pool.getObject();
        json.put(ID, chat.getId());
        json.put(TITLE, chat.getTitle());
        json.put(IS_GROUP, chat.getGroupChat());
        json.put(MESSAGE, toJson(chat.getLastMessage()));
        JSONArray members = pool.getArray();
        members.addAll(chat.getMembers().stream().map(member -> toJson(member.getMember())).collect(Collectors.toList()));
        json.put(MEMBERS, members);
        return json;
    }

    public JSONObject toJson(ChatMessage message) {
        JSONObject object = pool.getObject();
        if (message != null) {
            object.put(ID, message.getId());
            object.put(CHAT, message.getChat().getId());
            object.put(TIME, message.getTimestamp().toString());
            object.put(EPOCH, message.getTimestamp().getTime());
            object.put(SENDER, toJson(message.getSender().getMember()));
            object.put(MESSAGE, message.getMessage());
            if (message.getDelivered() != null) {
                object.put(DELIVERED, message.getDelivered().toString());
            }
            if (message.getRead() != null) {
                object.put(READ, message.getRead().toString());
            }
        }
        return object;
    }

    public JSONObject toJson(ManufactureReport report) {
        JSONObject json = pool.getObject();
        json.put(ID, report.getId());
        json.put(DATE, report.getTurn().getDate().toString());
        json.put(TURN, toJson(report.getTurn()));
        return json;
    }

    public JSONObject toJson(Storage storage, Product product, Shipper shipper, float value) {
        JSONObject json = pool.getObject();
        json.put(STORAGE, storage.getId());
        json.put(PRODUCT, product.getId());
        json.put(SHIPPER, shipper.getId());
        json.put(VALUE, value);
        return json;
    }

    public JSONArray toStockJson(HashMap<Storage, HashMap<Product, HashMap<Shipper, Float>>> stocks) {
        JSONArray array = pool.getArray();
        for (Map.Entry<Storage, HashMap<Product, HashMap<Shipper, Float>>> storageEntry : stocks.entrySet()){
            Storage storage = storageEntry.getKey();
            JSONObject storageJson = toJson(storage);
            JSONArray storageValues = pool.getArray();
            for (Map.Entry<Product, HashMap<Shipper, Float>> productEntry : storageEntry.getValue().entrySet()){
                Product product = productEntry.getKey();
                JSONObject productJson = toJson(product);
                JSONArray productValues = pool.getArray();
                for (Map.Entry<Shipper, Float> entry : productEntry.getValue().entrySet()){
                    JSONObject jsop = toJson(entry.getKey());
                    jsop.put(VALUE, entry.getValue());
                    productValues.add(jsop);
                }
                productJson.put(VALUE, productValues);
                storageValues.add(productJson);
            }
            storageJson.put(VALUE, storageValues);
            array.add(storageJson);
        }
        return array;
    }

    private JSONObject toJson(Storage storage) {
        JSONObject json = pool.getObject();
        json.put(ID, storage.getId());
        json.put(NAME, storage.getName());
        return json;
    }

    private JSONObject toJson(Shipper shipper) {
        JSONObject jsop = pool.getObject();
        jsop.put(ID, shipper.getId());
        jsop.put(NAME, shipper.getValue());
        return jsop;
    }

    public JSONObject toJson(StoragePeriodPoint point) {
        JSONObject json = pool.getObject();
        json.put(ID, point.getId());
        json.put(DATE, point.getDate().toString());
        json.put(PRODUCT, toJson(point.getProduct()));
        json.put(SHIPPER, toJson(point.getShipper()));
        json.put(AMOUNT, point.getAmount());
        PointScale scale = point.getScale();
        PointScale s = scale;
        LocalDate date = point.getDate().toLocalDate();
        json.put(SCALE, scale.toString());
        while ((s = StorageUtil.nextScale(s)) != scale){
            scale = s;
            LocalDate beginDate = StorageUtil.getBeginDate(date, scale);
            json.put(scale.toString(), java.sql.Date.valueOf(beginDate).toString());
        }

        return json;
    }
}
