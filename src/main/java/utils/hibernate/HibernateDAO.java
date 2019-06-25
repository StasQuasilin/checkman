package utils.hibernate;

import bot.BotUID;
import entity.*;
import entity.bot.BotSettings;
import entity.bot.UserBotSetting;
import entity.documents.*;
import entity.laboratory.CakeAnalyses;
import entity.laboratory.LaboratoryTurn;
import entity.laboratory.probes.OilProbe;
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
import entity.seals.Seal;
import entity.storages.Storage;
import entity.storages.StorageProduct;
import entity.transport.*;
import entity.weight.Weight;
import entity.weight.WeightUnit;
import utils.TurnDateTime;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.LE;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 24.06.2019.
 */
public class HibernateDAO implements dbDAO {
    private final Hibernator hb = Hibernator.getInstance();

    @Override
    public void saveDeal(Deal deal){
        deal.setHash(deal.hashCode());
        hb.save(deal);
    }

    @Override
    public List<LoadPlan> getPlanByDeal(Deal deal) {
        return hb.query(LoadPlan.class, "deal", deal);
    }

    @Override
    public List<VROTurn> getVroTurnsByDate(HashMap<String, Object> parameters) {
        return hb.limitQuery(VROTurn.class, parameters, 14);
    }

    @Override
    public Deal getDealById(Object id) {
        return hb.get(Deal.class, "id", id);
    }

    @Override
    public Organisation getOrganisationById(Object organisationId) {
        return hb.get(Organisation.class, "id", organisationId);
    }

    @Override
    public Product getProductById(Object id) {
        return hb.get(Product.class, "id", id);
    }

    @Override
    public DocumentOrganisation getDocumentOrganisationById(Object id) {
        return hb.get(DocumentOrganisation.class, "id", id);
    }

    @Override
    public int getActNumber(ActType type) {
        ActNumber actNumber = hb.get(ActNumber.class, "type", type);
        if (actNumber == null) {
            actNumber = new ActNumber(type);
        }
        int increment = actNumber.increment();
        hb.save(actNumber);
        return increment;
    }

    @Override
    public BotSettings getBotSettings() {
        return hb.get(BotSettings.class, null);
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
    public List<LoadPlan> getLoadPlanByDeal(Object deal) {
        return hb.query(LoadPlan.class, "deal", deal);
    }

    @Override
    public ApplicationSettings getApplicationSettings() {
        return hb.get(ApplicationSettings.class, null);
    }

    @Override
    public List<LoadPlan> getTransportArchive() {
        return null;
    }

    @Override
    public LoadPlan getLoadPlanById(Object id) {
        return hb.get(LoadPlan.class, "id", id);
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
        return hb.get(Person.class, "id", personId);
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
        return hb.get(Worker.class, "id", id);
    }

    @Override
    public void saveCakeAnalyses(CakeAnalyses cakeAnalyses) {
        hb.save(cakeAnalyses);
    }

    @Override
    public void remove(Object o) {
        hb.remove(o);
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
    public void saveTransportation(ActionTime time, Transportation transportation) {
        hb.save(time);
        saveTransportation(transportation);
    }

    @Override
    public <T> void remove(Class<T> tClass, Object id) {
        hb.remove(hb.get(tClass, "id", id));
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
    public List<DocumentOrganisation> getDocumentOrganisationList() {
        return hb.query(DocumentOrganisation.class, null);
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
        return hb.get(Driver.class, "id", id);
    }

    @Override
    public List<Transportation> getTransportationsByDriver(Driver driver) {
        return hb.query(Transportation.class, "driver", driver);
    }

    @Override
    public OrganisationType getOrganisationTypeByName(String name) {
        return hb.get(OrganisationType.class, "name", name);
    }

    @Override
    public Seal getSealById(Object id) {
        return hb.get(Seal.class, "id", id);
    }

    @Override
    public VROOil getVROOilById(Object id) {
        return hb.get(VROOil.class, "id", id);
    }

    @Override
    public List<Forpress> getForpressList() {
        return hb.query(Forpress.class, null);
    }

    @Override
    public OilMassFractionDry getOilMassFractionDry(Object id) {
        return hb.get(OilMassFractionDry.class, "id", id);
    }

    @Override
    public Transportation getTransportationById(Object id) {
        return hb.get(Transportation.class, "id", id);
    }

    @Override
    public KPOPart getKPOPartById(Object id) {
        return hb.get(KPOPart.class, "id", id);
    }

    @Override
    public SunProbe getSunProbeById(Object id) {
        return hb.get(SunProbe.class, "id", id);
    }

    @Override
    public OilProbe getOilProbeById(Object id) {
        return hb.get(OilProbe.class, "id", id);
    }

    @Override
    public Vehicle getVehicleById(Object id) {
        return hb.get(Vehicle.class, "id", id);
    }

    @Override
    public VRODaily getVRODailyById(Object id) {
        return hb.get(VRODaily.class, "id", id);
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
        return hb.get(VROCrude.class, "id", id);
    }

    @Override
    public List<LaboratoryTurn> getLaboratoryTurnByTurn(Turn turn) {
        return hb.query(LaboratoryTurn.class, "turn", turn);
    }

    @Override
    public Forpress getForpressById(Object id) {
        return hb.get(Forpress.class,"id", id);
    }

    @Override
    public ExtractionCrude getExtractionCrudeById(Object id) {
        return hb.get(ExtractionCrude.class, "id", id);
    }

    @Override
    public Collection<Organisation> findOrganisation(String key) {
        Set<Integer> ids = new HashSet<>();
        List<Organisation> organisations = new LinkedList<>();
        findOrganisation("type", key, ids, organisations);
        findOrganisation("name", key, ids, organisations);
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

    @Override
    public List<Deal> getArchiveDeals(DealType type) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("archive", true);
        parameters.put("type", type);
        return hb.query(Deal.class, parameters);
    }

    @Override
    public DocumentOrganisation getDocumentOrganisationByValue(Object value) {
        return hb.get(DocumentOrganisation.class, "value", value);
    }

    @Override
    public WeightUnit getWeightUnitById(Object id) {
        return hb.get(WeightUnit.class, "id", id);
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
    public List<ExtractionTurn> getLimitExtractionTurns() {
        return hb.limitQuery(ExtractionTurn.class, "turn/date", new LE(Date.valueOf(LocalDate.now().plusDays(1))), 14);
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
        for(Worker worker : find(Worker.class, key, value)){
            if (!ids.contains(worker.getId())){
                ids.add(worker.getId());
                workers.add(worker);
            }
        }
    }

    @Override
    public TurnProtein getExtractionTurnProteinById(long id) {
        return hb.get(TurnProtein.class, "id", id);
    }

    @Override
    public OilMassFraction getOilMassFractionById(long id) {
        return hb.get(OilMassFraction.class, "id", id);
    }

    @Override
    public List<Vehicle> findVehicle(Object key) {
        final Set<Integer> ids = new HashSet<>();
        final List<Vehicle> vehicles = new LinkedList<>();
        String k = String.valueOf(key);
        findVehicle("model", k, ids, vehicles);
        findVehicle("number", k, ids, vehicles);
        findVehicle("trailer", k, ids, vehicles);

        ids.clear();
        return vehicles;
    }

    private void findVehicle(String key, String value, Set<Integer> ids, List<Vehicle> vehicles){
        find(Vehicle.class, key, value).stream()
                .filter(vehicle -> !ids.contains(vehicle.getId())).forEach(vehicle -> {
            ids.add(vehicle.getId());
            vehicles.add(vehicle);
        });
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
    public StorageAnalyses getStorageAnalysesById(Object id) {
        return hb.get(StorageAnalyses.class, "id", id);
    }

    @Override
    public StorageTurn getStorageTurnByTurn(Turn turn) {
        return hb.get(StorageTurn.class, "turn", turn);
    }

    @Override
    public Storage getStorageById(Object id) {
        return hb.get(Storage.class, "id", id);
    }

    @Override
    public ExtractionRaw getExtractionRawById(Object id) {
        return hb.get(ExtractionRaw.class, "id", id);
    }

    @Override
    public VRODaily getVroDailyById(Object id) {
        return hb.get(VRODaily.class, "id", id);
    }

    @Override
    public ExtractionOIl getExtractionOilById(Object id) {
        return hb.get(ExtractionOIl.class, "id", id);
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
    public ExtractionTurn getExtractionTurnByTurn(Turn turn) {
        return hb.get(ExtractionTurn.class, "turn", turn);
    }

    @Override
    public List<KPOPart> getLimitKPOParts() {
        return hb.limitQuery(KPOPart.class, "date", new LE(Date.valueOf(LocalDate.now().plusYears(1))), 14);
    }

    @Override
    public StorageGrease getStorageGreaseById(Object id) {
        return hb.get(StorageGrease.class, "id", id);
    }

    @Override
    public List<LoadPlan> getLoadPlansByDealType(DealType dealType) {
        return hb.query(LoadPlan.class, "deal/type", dealType);
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
    public List<Deal> getDealsByOrganisation(Object organisation) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("archive", false);
        parameters.put("organisation", organisation);
        return hb.query(Deal.class, parameters);
    }

    @Override
    public StorageProtein getStorageProteinById(Object id) {
        return hb.get(StorageProtein.class, "id", id);
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

    private void findDriver(String key, String value, Set<Integer> ids, List<Driver> drivers){
        find(Driver.class, key, value).stream()
                .filter(driver -> !ids.contains(driver.getId())).forEach(driver -> {
            ids.add(driver.getId());
            drivers.add(driver);
        });
    }

    private <T> List<T> find(Class<T> tClass, String key, String value){
        return hb.find(tClass, key, value);
    }

    @Override
    public DealProduct getDealProductById(int id) {
        return hb.get(DealProduct.class, "id", id);
    }

    @Override
    public List<Subdivision> getSubdivisions() {
        return hb.query(Subdivision.class, null);
    }

    @Override
    public TurnGrease getTurnGreaseById(Object id) {
        return hb.get(TurnGrease.class, "id", id);
    }

    @Override
    public List<DealHash> getDealHashByType(DealType dealType) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("archive", false);
        parameters.put("type", dealType);
        return hb.query(DealHash.class, parameters);
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
    public List<VROTurn> getVroTurns() {
        return hb.query(VROTurn.class, "turn", new LE(Date.valueOf(LocalDate.now().plusYears(1))));
    }

    @Override
    public List<User> findUser(Object key) {
        final List<Worker> workers = findWorker(key);
        return workers.stream().map(this::getUserByWorker).collect(Collectors.toCollection(LinkedList::new));
    }
}
