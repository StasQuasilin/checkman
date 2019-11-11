package utils.hibernate;

import bot.BotUID;
import constants.Constants;
import entity.*;
import entity.bot.BotSettings;
import entity.bot.UserBotSetting;
import entity.chat.Chat;
import entity.chat.ChatMember;
import entity.chat.ChatMessage;
import entity.deal.Contract;
import entity.documents.*;
import entity.laboratory.MealAnalyses;
import entity.laboratory.turn.LaboratoryTurn;
import entity.laboratory.probes.OilProbe;
import entity.laboratory.probes.ProbeTurn;
import entity.laboratory.probes.SunProbe;
import entity.laboratory.storages.StorageAnalyses;
import entity.laboratory.storages.StorageTurn;
import entity.laboratory.subdivisions.extraction.*;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.laboratory.subdivisions.vro.*;
import entity.laboratory.transportation.ActNumber;
import entity.laboratory.transportation.ActType;
import entity.log.Change;
import entity.log.ChangeLog;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import entity.production.Forpress;
import entity.production.Turn;
import entity.production.TurnSettings;
import entity.products.Product;
import entity.products.ProductProperty;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import entity.reports.ReportFieldSettings;
import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.storages.*;
import entity.transport.*;
import entity.weight.Weight;
import entity.weight.WeightUnit;
import utils.ArchiveType;
import utils.Parser;
import utils.TurnDateTime;
import utils.U;
import utils.hibernate.DateContainers.*;
import utils.storages.PointScale;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 24.06.2019.
 */
public class HibernateDAO implements dbDAO {

    private static final String ID = Constants.ID;
    private static final String DEAL = "deal";
    private static final String SPACE = Constants.SPACE;
    private static final String TYPE = Constants.TYPE;

    private static final String ARCHIVE = Constants.ARCHIVE;
    private static final String PRODUCT = Constants.PRODUCT;
    private static final String SLASH = Constants.SLASH;
    private static final String PRODUCT_TYPE = PRODUCT + SLASH + TYPE;
    private final Hibernator hb = Hibernator.getInstance();

    @Override
    public void saveDeal(Deal deal){
        hb.save(deal);
    }

    @Override
    public List<ChatMember> getChatMembersByWorker(Worker worker) {
        return hb.query(ChatMember.class, "member", worker);
    }

    @Override
    public Chat getChatById(Object id) {
        return hb.get(Chat.class, "id", id);
    }

    @Override
    public List<ChatMember> getMembersByChat(Chat chat) {
        return hb.query(ChatMember.class, "chat", chat);
    }

    @Override
    public ChatMember getChatMember(Chat chat, Worker worker) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("chat", chat);
        parameters.put("member", worker);
        return hb.get(ChatMember.class, parameters);
    }

    @Override
    public ChatMessage getMessageById(long messageId) {
        return hb.get(ChatMessage.class, "id", messageId);
    }

    @Override
    public List<ChatMessage> getLimitMessagesByChat(Object chat) {
        return getLimitMessagesByChat(chat, 20);
    }

    @Override
    public List<ChatMessage> getLimitMessagesByChat(Object chat, int limit) {
        final HashMap<String, Object> parameters  = new HashMap<>();
        parameters.put("timestamp", new LE(Date.valueOf(LocalDate.now().plusDays(1))));
        parameters.put("chat", chat);
        return hb.limitQuery(ChatMessage.class, parameters, limit);
    }

    @Override
    public List<Worker> getWorkersWithout(Worker worker) {
        return hb.query(User.class, null).stream().filter(user -> user.getWorker().getId() != worker.getId()).map(User::getWorker).collect(Collectors.toList());
    }

    @Override
    public List<Worker> getWorkers() {
        return hb.query(User.class, null).stream().map(User::getWorker).collect(Collectors.toList());
    }

    @Override
    public List<LoadPlan> getPlanByDeal(Deal deal) {
        return hb.query(LoadPlan.class, DEAL, deal);
    }

    @Override
    public List<VROTurn> getVroTurnsByDate(HashMap<String, Object> parameters) {
        return hb.limitQuery(VROTurn.class, parameters, 14);
    }

    @Override
    public Deal getDealById(Object id) {
        return hb.get(Deal.class, ID, id);
    }

    @Override
    public Organisation getOrganisationById(Object organisationId) {
        return hb.get(Organisation.class, ID, organisationId);
    }

    @Override
    public Product getProductById(Object id) {
        return hb.get(Product.class, ID, id);
    }

    @Override
    public Shipper getShipperById(Object id) {
        return hb.get(Shipper.class, ID, id);
    }

    @Override
    public ActNumber getActNumber(ActType type) {
        ActNumber actNumber = hb.get(ActNumber.class, "type", type);
        if (actNumber == null) {
            actNumber = new ActNumber(type);
            hb.save(actNumber);
        }
        return actNumber;
    }

    @Override
    public int getActNumberIncrement(ActType type){
        ActNumber number = getActNumber(type);
        int actNumber = number.increment();
        hb.save(number);
        return actNumber;
    }

    @Override
    public BotSettings getBotSettings() {
        return hb.get(BotSettings.class, "run", false);
    }

    @Override
    public List<TurnSettings> getTurnSettings() {
        return hb.query(TurnSettings.class, null);
    }

    @Override
    public List<UserBotSetting> getUserBotSettings() {
        return hb.query(UserBotSetting.class, null);
    }

    @Override
    public void saveUserBotSetting(UserBotSetting botSetting) {
        hb.save(botSetting);
    }

    @Override
    public List<Admin> getAdminList() {
        return hb.query(Admin.class, null);
    }

    @Override
    public void saveTransportation(Transportation transportation) {
        hb.save(transportation);
    }

    @Override
    public void saveWeight(Weight weight) {
        hb.save(weight);
    }

    @Override
    public List<LoadPlan> getLoadPlanByDeal(Object deal, Boolean done, Boolean archive) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(DEAL, deal);
        if (done != null) {
            parameters.put("transportation/done", done);
        }
        if (archive != null) {
            parameters.put("transportation/archive", archive);
        }
        return hb.query(LoadPlan.class, parameters);
    }

    @Override
    public ApplicationSettings getApplicationSettings() {
        return hb.get(ApplicationSettings.class, null);
    }

    @Override
    public List<LoadPlan> getTransportArchive() {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("transportation/archive", true);
        parameters.put("date", new LE(Date.valueOf(LocalDate.now().plusYears(1))));
        return hb.limitQuery(LoadPlan.class, parameters, 30);
    }

    @Override
    public List<Transportation> getLimitArchiveTransportations(DealType type) {
        final HashMap<String, Object>parameters = new HashMap<>();
        parameters.put("archive", true);
        parameters.put("date", new LE(Date.valueOf(LocalDate.now().plusYears(10))));
        parameters.put("type", type);
        return hb.query(Transportation.class, parameters);
    }

    @Override
    public LoadPlan getLoadPlanById(Object id) {
        return hb.get(LoadPlan.class, ID, id);
    }

    @Override
    public void saveLoadPlan(LoadPlan plan) {

        hb.save(plan);
    }

    @Override
    public List<ChangeLog> getLogs(String uid) {
        return hb.query(ChangeLog.class, "document", uid);
    }

    @Override
    public List<LoadPlan> getDriverList() {
        return new LinkedList<>();
    }

    @Override
    public void savePerson(Person person) {
        save(person);
    }

    @Override
    public Person getPersonById(Object personId) {
        return hb.get(Person.class, ID, personId);
    }

    @Override
    public void saveWorker(Worker worker, User user) {
        save(worker, user);
    }

    @Override
    public List<User> getUsersByToken(String token) {
        return hb.query(User.class, "uid", token);
    }

    @Override
    public Worker getWorkerById(Object id) {
        return hb.get(Worker.class, ID, id);
    }

    @Override
    public void saveCakeAnalyses(MealAnalyses mealAnalyses) {
        hb.save(mealAnalyses);
    }

    @Override
    public void remove(Object ... objects) {
        for (Object o : objects){
            hb.remove(o);
        }
    }

    @Override
    public void save(Object ... objects) {
        for (Object o : objects){
            if (o instanceof Deal) {
                saveDeal((Deal) o);
            }else if (o instanceof Transportation){
                saveTransportation((Transportation) o);
            } else {
                hb.save(objects);
            }
        }


    }

    @Override
    public List<LoadPlan> getLoadPlans() {
        return hb.query(LoadPlan.class, "transportation/archive", false);
    }

    @Override
    public List<LoadPlan> getLimitLoadPlanArchive() {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("transportation/archive", true);
        parameters.put("date", new LE(Date.valueOf(LocalDate.now().plusYears(10))));
        return hb.limitQuery(LoadPlan.class, parameters, 14);
    }

    @Override
    public List<SunProbe> getLimitSunProbes(Date date) {
        return hb.query(SunProbe.class, null);
    }

    @Override
    public List<OilProbe> getLimitOilProbes(Date date) {
        return hb.query(OilProbe.class, null);
    }

    @Override
    public void saveChangeLod(ChangeLog log) {
        hb.save(log);
    }

    @Override
    public void saveChange(Change change) {
        hb.save(change);
    }

    @Override
    public LoadPlan getLoadPlanByTransportationId(Object id) {
        return hb.get(LoadPlan.class,"transportation", id);
    }

    @Override
    public <T> void remove(Class<T> tClass, Object id) {
        hb.remove(hb.get(tClass, ID, id));
    }

    @Override
    public List<Product> getProductList() {
        return hb.query(Product.class, null);
    }

    @Override
    public List<WeightUnit> getUnitsList() {
        return hb.query(WeightUnit.class, null);
    }

    @Override
    public List<Shipper> getShipperList() {
        return hb.query(Shipper.class, null);
    }

    @Override
    public List<Seal> findSeal(String key) {
        return hb.find(Seal.class, "number", key);
    }

    @Override
    public Turn getTurnByDate(TurnDateTime turnDate) {
        return hb.get(Turn.class, "date", Timestamp.valueOf(turnDate.getDate()));
    }

    @Override
    public Driver getDriverByID(Object id) {
        return hb.get(Driver.class, ID, id);
    }

    @Override
    public List<Transportation> getTransportationsByDriver(Driver driver) {
        return hb.query(Transportation.class, "driver/person", driver.getPerson());
    }

    @Override
    public OrganisationType getOrganisationTypeByName(String name) {
        return hb.get(OrganisationType.class, "name", name);
    }

    @Override
    public Seal getSealById(Object id) {
        return hb.get(Seal.class, ID, id);
    }

    @Override
    public VROOil getVROOilById(Object id) {
        return hb.get(VROOil.class, ID, id);
    }

    @Override
    public List<Forpress> getForpressList() {
        return hb.query(Forpress.class, null);
    }

    @Override
    public OilMassFractionDry getOilMassFractionDry(Object id) {
        return hb.get(OilMassFractionDry.class, ID, id);
    }

    @Override
    public Transportation getTransportationById(Object id) {
        return hb.get(Transportation.class, ID, id);
    }

    @Override
    public KPOPart getKPOPartById(Object id) {
        return hb.get(KPOPart.class, ID, id);
    }

    @Override
    public SunProbe getSunProbeById(Object id) {
        return hb.get(SunProbe.class, ID, id);
    }

    @Override
    public OilProbe getOilProbeById(Object id) {
        return hb.get(OilProbe.class, ID, id);
    }

    @Override
    public Vehicle getVehicleById(Object id) {
        return hb.get(Vehicle.class, ID, id);
    }

    @Override
    public VRODaily getVRODailyById(Object id) {
        return hb.get(VRODaily.class, ID, id);
    }

    @Override
    public List<LoadPlan> getLastPlans() {
        return hb.limitQuery(LoadPlan.class, "date", new LE(Date.valueOf(LocalDate.now().plusYears(1))), 200);
    }

    @Override
    public DocumentUID getDocumentUID(String uid) {
        return hb.get(DocumentUID.class, "uid", uid);
    }

    @Override
    public User getUserByWorker(Worker worker) {
        return hb.get(User.class, "worker", worker);
    }

    @Override
    public VROCrude getVroCrudeById(Object id) {
        return hb.get(VROCrude.class, ID, id);
    }

    @Override
    public LaboratoryTurn getLaboratoryTurnByTurn(Turn turn) {
        return hb.get(LaboratoryTurn.class, "turn", turn);
    }

    @Override
    public Forpress getForpressById(Object id) {
        return hb.get(Forpress.class,ID, id);
    }

    @Override
    public ExtractionCrude getExtractionCrudeById(Object id) {
        return hb.get(ExtractionCrude.class, ID, id);
    }

    @Override
    public Collection<Organisation> findOrganisation(String key) {
        Set<Integer> ids = new HashSet<>();
        List<Organisation> organisations = new LinkedList<>();
        for (String string : key.split(SPACE)){
            String s = string.trim();
            findOrganisation("type", s, ids, organisations);
            findOrganisation("name", s, ids, organisations);
        }

        ids.clear();
        return organisations;
    }

    private void findOrganisation(String key, String value, Set<Integer> ids, List<Organisation> organisations){
        for (Organisation organisation : find(Organisation.class, key, value)){
            if (!ids.contains(organisation.getId())){
                ids.add(organisation.getId());
                organisations.add(organisation);
            }
        }
    }

    @Override
    public List<LoadPlan> getActiveTransportations(Date date) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("transportation/archive", false);
        if (date == null) {
            parameters.put("date", new LE(Date.valueOf(LocalDate.now().plusYears(1))));
            return hb.limitQuery(LoadPlan.class, parameters, 30);
        } else {
            parameters.put("date", date);
            return hb.query(LoadPlan.class, parameters);
        }
    }

    private static final String TRANSPORTATION_ARCHIVE = "transportation/archivation";
    private void getTransportations(HashMap<String, Object> parameters){
        parameters.put(TRANSPORTATION_ARCHIVE, null);
        parameters.put(TRANSPORTATION_ARCHIVE, new GE(Date.valueOf(LocalDate.now().plusDays(1))));
    }

    @Override
    public List<Contract> getActiveDeals() {
        final HashMap<String, Object> param = new HashMap<>();
        param.put(ARCHIVE, false);
        return hb.query(Contract.class, param);
    }

    @Override
    public List<Deal> getLimitArchiveDeals(DealType type) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("archive", true);
        parameters.put("type", type);
        parameters.put("date", new LE(Date.valueOf(LocalDate.now().plusDays(1))));
        return hb.limitQuery(Deal.class, parameters, 14);
    }

    @Override
    public Shipper getShipperByValue(Object value) {
        return hb.get(Shipper.class, "value", value);
    }

    @Override
    public WeightUnit getWeightUnitById(Object id) {
        return hb.get(WeightUnit.class, ID, id);
    }

    @Override
    public BotUID getBotUidByWorker(Worker worker) {
        return hb.get(BotUID.class, "worker", worker);
    }

    @Override
    public BotUID getBotUidByUid(String uid) {
        return hb.get(BotUID.class, "uid", uid);
    }

    @Override
    public List<StorageTurn> getLimitStorageTurns() {
        return hb.limitQuery(StorageTurn.class, "turn/date", new LE(Date.valueOf(LocalDate.now().plusDays(1))), 14);
    }

    @Override
    public User getUserByUID(String uid) {
        return hb.get(User.class, "uid", uid);
    }

    @Override
    public List<ProductProperty> getProductProperties(Product product) {
        return hb.query(ProductProperty.class, "product", product);
    }

    @Override
    public ProductProperty getProductProperty(Product product, String key) {
        final HashMap<String, Object> param= new HashMap<>();
        param.put("product", product);
        param.put("key", key);
        return hb.get(ProductProperty.class, param);
    }

    @Override
    public List<ExtractionTurn> getLimitExtractionTurns() {
        return hb.limitQuery(ExtractionTurn.class, "turn/date", new LE(Date.valueOf(LocalDate.now().plusYears(1))), 7);
    }

    @Override
    public List<ProbeTurn> getLimitProbeTurns(){
        return hb.limitQuery(ProbeTurn.class, "turn/date", new LE(Date.valueOf(LocalDate.now().plusDays(1))), 14);
    }

    @Override
    public List<Worker> findWorker(Object key) {
        final Set<Integer> ids = new HashSet<>();
        final List<Worker> workers = new LinkedList<>();
        String k = String.valueOf(key);
        findWorker("person/forename", k, ids, workers);
        findWorker("person/surname", k, ids, workers);
        findWorker("person/patronymic", k, ids, workers);
        ids.clear();
        return workers;
    }

    private void findWorker(String key, String value, Set<Integer> ids, List<Worker> workers){
        find(Worker.class, key, value).stream().filter(worker -> !ids.contains(worker.getId())).forEach(worker -> {
            ids.add(worker.getId());
            workers.add(worker);
        });
    }

    @Override
    public TurnProtein getTurnProteinById(Object id) {
        return hb.get(TurnProtein.class, ID, id);
    }

    @Override
    public OilMassFraction getOilMassFractionById(long id) {
        return hb.get(OilMassFraction.class, ID, id);
    }

    private static final String REG_EXP = Parser.NUMBER_REGEX;

    @Override
    public List<Vehicle> findVehicle(Object key) {
        final HashMap<Integer, Integer> ids = new HashMap<>();
        final HashMap<Integer, Vehicle> vehicles = new HashMap<>();

        StringBuilder builder = new StringBuilder();

        String trim = key.toString().trim().toUpperCase().replaceAll("  ", " ");
        for (char c : trim.toCharArray()){
            if (Character.isLetter(c) || Character.isDigit(c) || Character.isSpaceChar(c)){
                builder.append(c);
            }
        }

        trim = builder.toString();

        Pattern compile = Pattern.compile(REG_EXP);
        Matcher matcher = compile.matcher(trim);
        if (matcher.find()){
            String group = matcher.group();
            findVehicle("number", group, ids, vehicles);
            if (vehicles.size() > 0){
                trim = trim.replace(group, "").trim();
            }
        }

        String model = trim.split(SPACE)[0];

        findVehicle("model", model, ids, vehicles);

        trim = trim.replace(model, "").trim();
        if (U.exist(trim)) {

            int size = vehicles.size();
            trim = Parser.prettyNumber(trim);
            findVehicle("number", trim, ids, vehicles);
            if (vehicles.size() == size) {
                findVehicle("trailer", trim, ids, vehicles);
            }
        }
        int min = 0;
        ArrayList<Vehicle> result = new ArrayList<>();

        while (vehicles.size() > 0){
            for (Map.Entry<Integer, Integer> entry : ids.entrySet()){
                if (entry.getValue() == min){
                    result.add(0, vehicles.remove(entry.getKey()));
                }
            }
            if (min == 0 && vehicles.size() > 0){
                result.clear();
            }
            min++;
        }

        ids.clear();
        vehicles.clear();
        return result;
    }

    private void findVehicle(String key, String value, HashMap<Integer, Integer> ids, HashMap<Integer, Vehicle> vehicles){
        for (Vehicle v : find(Vehicle.class, key, value)){
            int id = v.getId();
            if (ids.containsKey(id)){
                ids.put(id, ids.get(id) + 1);
            } else {
                ids.put(id, 0);
                vehicles.put(id, v);
            }
        }
    }

    @Override
    public List<OrganisationType> getOrganisationTypeList() {
        return hb.query(OrganisationType.class, null);
    }

    @Override
    public Organisation findOrganisation(String type, String name) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("type", type);
        parameters.put("name", name);
        return hb.get(Organisation.class, parameters);
    }

    @Override
    public List<Storage> getStoragesByAnalysesType(AnalysesType type) {
        final Set<Integer> ids = new HashSet<>();
        final List<Storage> storages = new LinkedList<>();
        hb.query(StorageProduct.class, "product/analysesType", type).stream()
                .filter(sp -> !ids.contains(sp.getStorage().getId())).forEach(sp -> {
            ids.add(sp.getStorage().getId());
            storages.add(sp.getStorage());
        });
        ids.clear();
        return storages;
    }

    @Override
    public List<UserBotSetting> getBotSettingsByWorker(Worker worker) {
        return hb.query(UserBotSetting.class, "worker", worker);
    }

    @Override
    public User getUserByEmail(String email) {
        return hb.get(User.class, "email", email);
    }

    @Override
    public List<User> getUsersByEmail(String email) {
        return hb.query(User.class, "email", email);
    }

    @Override
    public StorageAnalyses getStorageAnalysesById(Object id) {
        return hb.get(StorageAnalyses.class, ID, id);
    }

    @Override
    public StorageTurn getStorageTurnByTurn(Turn turn) {
        return hb.get(StorageTurn.class, "turn", turn);
    }

    @Override
    public Storage getStorageById(Object id) {
        return hb.get(Storage.class, ID, id);
    }

    @Override
    public ExtractionRaw getExtractionRawById(Object id) {
        return hb.get(ExtractionRaw.class, ID, id);
    }

    @Override
    public VRODaily getVroDailyById(Object id) {
        return hb.get(VRODaily.class, ID, id);
    }

    @Override
    public ExtractionOIl getExtractionOilById(Object id) {
        return hb.get(ExtractionOIl.class, ID, id);
    }

    @Override
    public List<LoadPlan> getLoadPlansBetweenDates(LocalDate from, LocalDate to) {
        return hb.query(LoadPlan.class, "date", new BETWEEN(Date.valueOf(from), Date.valueOf(to)));
    }

    @Override
    public List<WeightUnit> getWeightUnits() {
        return hb.query(WeightUnit.class, null);
    }

    @Override
    public List<LoadPlan> getTransportationsOnTerritory() {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("transportation/archive", false);
        parameters.put("transportation/timeIn", State.notNull);
        parameters.put("transportation/timeOut", null);
        return hb.query(LoadPlan.class, parameters);
    }

    @Override
    public List<LoadPlan> getTransportationsOnCruise() {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("transportation/archive", false);
        parameters.put("transportation/timeOut", State.notNull);
        return hb.query(LoadPlan.class, parameters);
    }

    @Override
    public List<Turn> getLimitTurns() {
        return hb.limitQuery(Turn.class, "date", new LE(Date.valueOf(LocalDate.now().plusYears(1))), 14);
    }

    @Override
    public List<LaboratoryTurn> getLimitLaboratoryTurn() {
        return hb.limitQuery(LaboratoryTurn.class, "turn/date", new LE(Date.valueOf(LocalDate.now().plusYears(1))), 14);
    }

    @Override
    public ExtractionTurn getExtractionTurnByTurn(Turn turn) {
        return hb.get(ExtractionTurn.class, "turn", turn);
    }

    @Override
    public ProbeTurn getProbeTurnByTurn(Turn turn) {
        return hb.get(ProbeTurn.class, "turn", turn);
    }

    @Override
    public List<KPOPart> getLimitKPOParts() {
        return hb.limitQuery(KPOPart.class, "date", new LE(Date.valueOf(LocalDate.now().plusYears(1))), 14);
    }

    @Override
    public StorageGrease getStorageGreaseById(Object id) {
        return hb.get(StorageGrease.class, ID, id);
    }

    @Override
    public List<LoadPlan> getLoadPlansByDealType(DealType dealType) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("transportation/archive", false);
        parameters.put("deal/type", dealType);
        return hb.query(LoadPlan.class, parameters);
    }

    @Override
    public List<Transportation> getTransportationsByType(DealType type) {
        final HashMap<String,Object> parameters = new HashMap<>();
        parameters.put("type", type);
        parameters.put("archive", false);
        return hb.query(Transportation.class, parameters);
    }

    @Override
    public List<Transportation> getTransportationByAnalyses(DealType type) {
        final HashMap<String,Object> parameters = new HashMap<>();
        parameters.put("type", type);
        parameters.put("archive", false);
        parameters.put("product/analysesType", State.notNull);
        return hb.query(Transportation.class, parameters);
    }

    @Override
    public VROTurn getVROTurnByTurn(Turn turn) {
        return hb.get(VROTurn.class, "turn", turn);
    }

    @Override
    public List<LoadPlan> getTransportationsByCustomer(TransportCustomer customer) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("transportation/archive", false);
        parameters.put("customer", customer);
        return hb.query(LoadPlan.class, parameters);
    }

    @Override
    public Vehicle getVehicleByNumber(String number) {
        return hb.get(Vehicle.class, "number", number);
    }

    @Override
    public Vehicle getVehicleByHash(int hash) {
        return hb.get(Vehicle.class, "hash", hash);
    }

    @Override
    public List<Deal> getDealsByOrganisation(Object organisation) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("archive", false);
        parameters.put("organisation", organisation);
        return hb.query(Deal.class, parameters);
    }

    @Override
    public List<Transportation> getTransportationByOrganisation(Object organisation) {
        return hb.query(Transportation.class, "counterparty", organisation);
    }

    @Override
    public List<ArchiveData> getArchiveData() {
        return hb.query(ArchiveData.class, null);
    }

    @Override
    public ArchiveData getArchiveData(ArchiveType type, int document) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("type", type);
        parameters.put("document", document);
        return hb.get(ArchiveData.class, parameters);
    }

    @Override
    public List<Transportation> getTransportationByVehicle(Vehicle vehicle) {
        return hb.query(Transportation.class, "vehicle", vehicle);
    }

    @Override
    public StorageProtein getStorageProteinById(Object id) {
        return hb.get(StorageProtein.class, ID, id);
    }

    @Override
    public UserBotSetting getUseBorSettingsByWorker(Worker worker) {
        return hb.get(UserBotSetting.class, "worker", worker);
    }

    @Override
    public List<Driver> findDriver(String key) {
        final Set<Integer> ids = new HashSet<>();
        final List<Driver> drivers = new LinkedList<>();

        findDriver("person/surname", key, ids, drivers);
        findDriver("person/forename", key, ids, drivers);
        findDriver("person/patronymic", key, ids, drivers);

        ids.clear();
        return drivers;
    }

    @Override
    public Person getPersonByName(String s) {
        return hb.get(Person.class, "surname", s);
    }

    @Override
    public Driver getDriverByPerson(Person person) {
        return hb.get(Driver.class, "person", person);
    }

    private void findDriver(String key, String value, Set<Integer> ids, List<Driver> drivers){
        HashMap<String, String> param = new HashMap<>();
        param.put(key, value);
        for (Driver driver : hb.find(Driver.class, param)){
            int id = driver.getId();
            if (!ids.contains(id)){
                ids.add(id);
                drivers.add(driver);
            }
        }
    }

    private <T> List<T> find(Class<T> tClass, String key, String value){
        return hb.find(tClass, key, value);
    }

    @Override
    public DealProduct getDealProductById(int id) {
        return hb.get(DealProduct.class, ID, id);
    }

    @Override
    public List<Subdivision> getSubdivisions() {
        return hb.query(Subdivision.class, null);
    }

    @Override
    public TurnGrease getTurnGreaseById(Object id) {
        return hb.get(TurnGrease.class, ID, id);
    }

    @Override
    public Turn getTurnByTime(Timestamp timestamp) {
        return hb.get(Turn.class, "date", timestamp);
    }

    @Override
    public List<LaboratoryTurn> getAnyTurnByDate(LocalDate date) {
        return hb.query(LaboratoryTurn.class, "turn/date", new LE(Date.valueOf(date.plusDays(1))));
    }

    @Override
    public List<VROTurn> getVroTurnsBetween(LocalDate from, LocalDate to) {
        return hb.query(VROTurn.class, "turn/date", new BETWEEN(Date.valueOf(from), Date.valueOf(to)));
    }

    @Override
    public List<VROTurn> getLimitVroTurns() {
        return hb.limitQuery(VROTurn.class, "turn/date", new LE(Date.valueOf(LocalDate.now().plusYears(1))), 14);
    }

    @Override
    public List<User> findUser(Object key) {
        return findWorker(key).stream().map(this::getUserByWorker).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Worker> getWorkersByRole(Role role) {
        return hb.query(User.class, "role", role).stream().map(User::getWorker).collect(Collectors.toList());
    }

    @Override
    public List<Seal> getSeals() {
        return hb.query(Seal.class, "transportation", State.isNull);
    }

    @Override
    public List<SealBatch> getActiveSealsBatches() {
        return hb.query(SealBatch.class, "archive", false);
    }

    @Override
    public List<Seal> getSealsByBatch(SealBatch batch) {
        return hb.query(Seal.class, "batch", batch);
    }

    @Override
    public List<TransportationNote> getTransportationNotesByTransportation(Transportation transportation) {
        return hb.query(TransportationNote.class, "transportation", transportation);
    }

    @Override
    public TransportationNote getTransportationNotesById(Object id) {
        return hb.get(TransportationNote.class, ID, id);
    }

    @Override
    public List<Turn> getTurnsBetween(LocalDate from, LocalDate to) {
        return hb.limitQuery(Turn.class, "date", new BETWEEN(Date.valueOf(from), Date.valueOf(to)), 14);
    }

    @Override
    public List<ReportFieldSettings> getReportFields() {
        return hb.query(ReportFieldSettings.class, null);
    }

    @Override
    public ManufactureReport getManufactureReport(Object id) {
        return hb.get(ManufactureReport.class, "id", id);
    }

    @Override
    public ReportField getReportField(Object fieldId) {
        return hb.get(ReportField.class, "id", fieldId);
    }

    @Override
    public List<Storage> getStorages() {
        return hb.query(Storage.class, null);
    }

    @Override
    public List<ReportFieldCategory> getReportCategories() {
        return hb.query(ReportFieldCategory.class, null);
    }

    @Override
    public ReportFieldCategory getReportCategory(Object categoryId) {
        return hb.get(ReportFieldCategory.class, "id", categoryId);
    }

    @Override
    public List<ManufactureReport> getLimitManufactureReports() {
        return hb.limitQuery(ManufactureReport.class, "turn/date", new LE(Date.valueOf(LocalDate.now().plusDays(1))), 14);
    }

    @Override
    public BotSettings getBotSettingsByChatId(Object id) {
        return hb.get(BotSettings.class, "telegramId", id);
    }

    @Override
    public BotSettings getBotSettingsById(Object id) {
        return hb.get(BotSettings.class, "id", id);
    }

    @Override
    public UserBotSetting getUserBotSettingsByChat(Object id) {
        return hb.get(UserBotSetting.class, "telegramId", id);
    }

    @Override
    public <T> List<T> query(Class<T> tClass, HashMap<String, Object> parameters) {
        return hb.query(tClass, parameters);
    }

    @Override
    public List<Organisation> getOrganisations() {
        return hb.query(Organisation.class, null);
    }

    @Override
    public <T> T getObjectById(Class<T> _class, Object id) {
        return hb.get(_class, ID, id);
    }

    @Override
    public <T> List<T> getObjects(Class<T> tClass) {
        return hb.query(tClass, null);
    }

    @Override
    public List<StorageProduct> getStorageProductByProduct(Product product) {
        return hb.query(StorageProduct.class, "product", product);
    }

    @Override
    public List<StorageProduct> getStorageProductByStorage(Object storage) {
        return hb.query(StorageProduct.class, "storage", storage);
    }

    @Override
    public <T> float sum(Class<T> tClass, HashMap<String, Object> param, String... columns) {
        return hb.sum(tClass, param, columns);
    }

    @Override
    public <T> List<T> getObjectsByParams(Class<T> tClass, HashMap<String, Object> params) {
        return hb.query(tClass, params);
    }

    @Override
    public List<Seal> getSealsByTransportation(Transportation transportation) {
        return hb.query(Seal.class, "cargo", transportation);
    }

    @Override
    public StorageEntry getStorageEntry(int documentId, StorageDocumentType documentType) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("document", documentId);
        param.put("type", documentType);
        return hb.get(StorageEntry.class, param);
    }

    @Override
    public StoragePeriodPoint getStoragePoint(Date date, Storage storage, Product product, Shipper shipper, PointScale scale) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("date", date);
        param.put("storage", storage);
        param.put("product", product);
        param.put("shipper", shipper);
        param.put("scale", scale);
        return hb.get(StoragePeriodPoint.class, param);
    }

    @Override
    public List<StorageEntry> getStorageEntries(Date from, Date to, Storage storage, Product product, Shipper shipper) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("time", new BETWEEN(from, to));
        param.put("storage", storage);
        param.put("product", product);
        param.put("shipper", shipper);
        return hb.query(StorageEntry.class, param);
    }

    @Override
    public List<StoragePeriodPoint> getStoragePoints(Date from, Date to, Storage storage, Product product, Shipper shipper, PointScale scale) {
        HashMap<String, Object> param = new HashMap<>();
        if (from == null){
            param.put("date", new LE(to));
        } else {
            param.put("date", new BETWEEN(from, to));
        }
        param.put("storage", storage);
        param.put("product", product);
        param.put("shipper", shipper);
        param.put("scale", scale);
        return hb.query(StoragePeriodPoint.class, param);
    }
}
