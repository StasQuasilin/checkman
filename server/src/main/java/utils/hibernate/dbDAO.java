package utils.hibernate;

import bot.BotUID;
import entity.*;
import entity.bot.BotSettings;
import entity.bot.UserBotSetting;
import entity.chat.Chat;
import entity.chat.ChatMember;
import entity.chat.ChatMessage;
import entity.deal.Contract;
import entity.documents.*;
import entity.laboratory.MealAnalyses;
import entity.laboratory.Protocol;
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
import entity.organisations.LoadAddress;
import entity.organisations.Organisation;
import entity.organisations.OrganisationType;
import entity.production.Forpress;
import entity.production.Turn;
import entity.production.TurnSettings;
import entity.products.Product;
import entity.products.ProductGroup;
import entity.products.ProductProperty;
import entity.products.ProductSettings;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import entity.reports.ReportFieldSettings;
import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.storages.*;
import entity.transport.*;
import entity.weight.Weight;
import entity.weight.Unit;
import utils.ArchiveType;
import utils.TurnDateTime;
import utils.storages.PointScale;

import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 24.06.2019.
 */
public interface dbDAO {
    void saveDeal(Deal deal);
    List<LoadPlan> getPlanByDeal(Deal deal);
    List<VROTurn> getVroTurnsByDate(HashMap<String, Object> parameters);
    Deal getDealById(Object id);
    Organisation getOrganisationById(Object organisationId);
    Product getProductById(Object id);
    Shipper getShipperById(Object id);
    ActNumber getActNumber(ActType type);
    int getActNumberIncrement(ActType type);
    BotSettings getBotSettings();
    List<TurnSettings> getTurnSettings();
    List<UserBotSetting> getUserBotSettings();
    void saveUserBotSetting(UserBotSetting botSetting);
    List<Admin> getAdminList();
    void saveTransportation(Transportation transportation);
    void saveWeight(Weight weight);
    List<LoadPlan> getLoadPlanByDeal(Object deal, Boolean done, Boolean archive);
    ApplicationSettings getApplicationSettings();
    List<LoadPlan> getTransportArchive();
    LoadPlan getLoadPlanById(Object id);
    void saveLoadPlan(LoadPlan plan);
    List<ChangeLog> getLogs(String uid);
    List<Driver> getDriverList();
    void savePerson(Person person);
    Person getPersonById(Object personId);
    void saveWorker(Worker worker, User user);
    List<User> getUsersByToken(String token);
    Worker getObjectById(Object id);
    void saveCakeAnalyses(MealAnalyses mealAnalyses);
    void remove(Object ... o);
    void save(Object ... o);
    List<SunProbe> getLimitSunProbes(Date date);
    List<OilProbe> getLimitOilProbes(Date date);
    void saveChangeLod(ChangeLog log);
    void saveChange(Change change);
    LoadPlan getLoadPlanByTransportationId(Object id);
    <T> void remove (Class<T> tClass, Object id);
    List<Product> getProductList();
    List<Unit> getUnitsList();
    List<Shipper> getShipperList();
    List<Seal> findSeal(String key);
    Turn getTurnByDate(TurnDateTime turnDate);
    Driver getDriverByID(Object id);
    List<Transportation> getTransportationsByDriver(Driver driver);
    OrganisationType getOrganisationTypeByName(String type);
    Seal getSealById(Object sealId);
    VROOil getVROOilById(Object id);
    List<Forpress> getForpressList();
    OilMassFractionDry getOilMassFractionDry(Object id);
    Transportation getTransportationById(Object id);
    KPOPart getKPOPartById(Object id);
    SunProbe getSunProbeById(Object id);
    OilProbe getOilProbeById(Object id);
    Vehicle getVehicleById(Object id);
    VRODaily getVRODailyById(Object id);
    List<LoadPlan> getLastPlans();
    DocumentUID getDocumentUID(String uid);
    User getUserByWorker(Worker worker);
    VROCrude getVroCrudeById(Object id);
    LaboratoryTurn getLaboratoryTurnByTurn(Turn turn);
    Forpress getForpressById(Object forpressId);
    ExtractionCrude getExtractionCrudeById(Object id);
    Collection<Organisation> findOrganisation(String key);
    List<LoadPlan> getActiveTransportations(Date date);
    List<Deal> getLimitArchiveDeals(DealType type);
    Shipper getShipperByValue(Object value);
    Unit getWeightUnitById(Object unit);
    BotUID getBotUidByWorker(Worker worker);
    BotUID getBotUidByUid(String uid);
    List<StorageTurn> getLimitStorageTurns();
    User getUserByUID(String uid);
    List<ProductProperty> getProductProperties(Product product);
    List<ExtractionTurn> getLimitExtractionTurns();
    List<ProbeTurn> getLimitProbeTurns();
    List<Worker> findWorker(Object key);
    TurnProtein getTurnProteinById(Object id);
    <T> List<T> findVehicle (Class<T> tClass, Object key);
    List<OrganisationType> getOrganisationTypeList();
    Organisation findOrganisation(String type, String name);
    List<Storage> getStoragesByAnalysesType(AnalysesType type);
    List<UserBotSetting> getBotSettingsByWorker(Worker worker);
    User getUserByEmail(String email);
    List<User> getUsersByEmail(String email);
    StorageAnalyses getStorageAnalysesById(Object id);
    StorageTurn getStorageTurnByTurn(Turn turn);
    Storage getStorageById(Object id);
    ExtractionRaw getExtractionRawById(Object id);
    VRODaily getVroDailyById(Object id);
    ExtractionOIl getExtractionOilById(Object id);
    List<LoadPlan> getLoadPlansBetweenDates(LocalDate from, LocalDate to);
    List<Unit> getWeightUnits();
    List<LoadPlan> getTransportationsOnTerritory();
    List<LoadPlan> getTransportationsOnCruise();
    List<Turn> getLimitTurns();
    ExtractionTurn getExtractionTurnByTurn(Turn turn);
    List<KPOPart> getLimitKPOParts();
    StorageGrease getStorageGreaseById(Object id);
    List<LoadPlan> getLoadPlansByDealType(DealType dealType);
    VROTurn getVROTurnByTurn(Turn turn);
    List<LoadPlan> getTransportationsByCustomer(TransportCustomer customer);
    List<Deal> getDealsByOrganisation(Object organisation);
    StorageProtein getStorageProteinById(Object id);
    UserBotSetting getUseBorSettingsByWorker(Worker worker);
    List<Driver> findDriver(String key);
    DealProduct getDealProductById(int id);
    List<Subdivision> getSubdivisions();
    TurnGrease getTurnGreaseById(Object id);
    Turn getTurnByTime(Timestamp timestamp);
    List<LaboratoryTurn> getAnyTurnByDate(LocalDate date);
    List<VROTurn> getVroTurnsBetween(LocalDate from, LocalDate to);
    List<VROTurn> getLimitVroTurns();
    List<User> findUser(Object key);
    List<Worker> getWorkersByRole(Role role);
    List<Seal> getSeals();
    List<SealBatch> getActiveSealsBatches();
    List<Seal> getSealsByBatch(SealBatch batch);
    List<TransportationNote> getTransportationNotesByTransportation(Transportation transportation);
    TransportationNote getTransportationNotesById(Object id);
    List<Turn> getTurnsBetween(LocalDate from, LocalDate to);
    List<Deal> getDealsByType(DealType type);
    Person getPersonByName(String surname, String forename, String patronymic);
    Driver getDriverByPerson(Person person);
    List<Transportation> getTransportationsByType(DealType type);
    List<LoadPlan> getLoadPlans();
    Vehicle getVehicleByNumber(String number);
    List<Transportation> getTransportationByVehicle(Vehicle vehicle);
    List<ArchiveData> getArchiveData();
    ProbeTurn getProbeTurnByTurn(Turn turn);
    List<LaboratoryTurn> getLimitLaboratoryTurn();
    List<LoadPlan> getLimitLoadPlanArchive();
    List<Worker> getWorkersWithout(Worker worker);
    List<ChatMember> getChatMembersByWorker(Worker worker);
    Chat getChatById(Object id);
    List<ChatMember> getMembersByChat(Chat chat);
    ChatMember getChatMember(Chat chat, Worker worker);
    ChatMessage getMessageById(long messageId);
    List<ChatMessage> getLimitMessagesByChat(Object chat);
    ArchiveData getArchiveData(ArchiveType type, int document);
    List<ChatMessage> getLimitMessagesByChat(Object chat, int limit);
    ProductProperty getProductProperty(Product product, String key);
    List<Transportation> getLimitArchiveTransportations(DealType type);
    List<Transportation> getTransportationByOrganisation(Object organisation);
    List<Transportation> getTransportationByAnalyses(DealType type);
    List<Worker> getWorkers();
    List<ReportFieldSettings> getReportFields();
    ManufactureReport getManufactureReport(Object id);
    ReportField getReportField(Object fieldId);
    List<Storage> getStorages();
    List<ReportFieldCategory> getReportCategories();
    List<ManufactureReport> getLimitManufactureReports();
    ReportFieldCategory getReportCategory(Object categoryId);
    BotSettings getBotSettingsById(Object id);
    BotSettings getBotSettingsByChatId(Object id);
    UserBotSetting getUserBotSettingsByChat(Object id);
    <T>List<T> query(Class<T> tClass, HashMap<String, Object> parameters);
    List<Organisation> getOrganisations();
    List<Vehicle> getVehiclesByName(Object name);
    <T>T getObjectById(Class<T> _class, Object o);
    <T> List<T> getObjects(Class<T> tClass);
    List<StorageProduct> getStorageProductByProduct(Product product);
    List<StorageProduct> getStorageProductByStorage(Object id);
    <T> float sum(Class<T> tClass, HashMap<String, Object> param, String... columns);
    <T> List<T> getObjectsByParams(Class<T> tClass, HashMap<String, Object> params);
    List<Seal> getSealsByTransportation(Transportation transportation);
    StorageEntry getStorageEntry(int documentId, StorageDocumentType documentType);
    StoragePeriodPoint getStoragePoint(Date date, Storage storage, Product product, Shipper shipper, PointScale scale);
    List<StorageEntry> getStorageEntries(Date from, Date to, Storage storage, Product product, Shipper shipper);
    List<StoragePeriodPoint> getStoragePoints(Date from, Date to, Storage storage, Product product, Shipper shipper, PointScale scale);
    List<Transportation> getTransportationsByTransporter(Organisation transporter);
    Product getProductByName(String name);
    List<LoadAddress> getLoadAddress(Organisation organisation);
    ProductSettings getProductSettings(Product product);
    <T>List<T> find(Class<T> tClass, String key, String value);
    List<Contract> getContractsByType(DealType type);
    List<Contract> getContractsByOrganisation(Organisation organisation);
    List<Transportation2> getTransportations(DealType type);
    float findPrice(Object counterparty, Object product);
    List<Protocol> getProtocols();
    Protocol getProtocol(Product product);
    TruckInfo getTruckInfo(String number);
}