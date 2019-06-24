package utils.hibernate;

import bot.BotUID;
import entity.*;
import entity.bot.BotSettings;
import entity.bot.UserBotSetting;
import entity.documents.Deal;
import entity.documents.DocumentOrganisation;
import entity.documents.DocumentUID;
import entity.documents.LoadPlan;
import entity.laboratory.CakeAnalyses;
import entity.laboratory.LaboratoryTurn;
import entity.laboratory.probes.OilProbe;
import entity.laboratory.probes.SunProbe;
import entity.laboratory.storages.StorageTurn;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.extraction.TurnProtein;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.laboratory.subdivisions.vro.*;
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
import entity.transport.ActionTime;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.Weight;
import entity.weight.WeightUnit;
import utils.TurnDateTime;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 24.06.2019.
 */
public interface dbDAO {
    void saveDeal(Deal deal);
    List<LoadPlan> getPlanByDeal(Deal deal);
    List<VROTurn> getTurns(HashMap<String, Object> parameters);
    Deal getDealById(Object id);
    Organisation getOrganisationById(Object organisationId);
    Product getProductById(Object id);
    DocumentOrganisation getDocumentOrganisationById(Object id);
    int getActNumber(ActType type);
    BotSettings getBotSettings();
    List<TurnSettings> getTurnSettings();
    List<UserBotSetting> getUserBotSettings();
    void saveUserBotSetting(UserBotSetting botSetting);
    List<Admin> getAdminList();
    void saveTransportation(Transportation transportation);
    void saveWeight(Weight weight);
    List<LoadPlan> getLoadPlanByDeal(Object deal);
    ApplicationSettings getApplicationSettings();
    List<LoadPlan> getTransportArchive();
    LoadPlan getLoadPlanById(Object id);
    void saveLoadPlan(LoadPlan plan);
    List<ChangeLog> getLogs(String uid);
    LoadPlan[] getDriverList();
    void savePerson(Person person);
    Person getPersonById(Object personId);
    void saveWorker(Worker worker, User user);
    List<User> getUsersByToken(String token);
    Worker getWorkerById(Object id);
    void saveCakeAnalyses(CakeAnalyses cakeAnalyses);
    void remove(Object o);
    void save(Object ... o);
    List<SunProbe> getSunProbes();
    List<OilProbe> getOilProbes();
    void saveChangeLod(ChangeLog log);
    void saveChange(Change change);
    LoadPlan getLoadPlanByTransportationId(Object id);
    void saveTransportation(ActionTime time, Transportation transportation);
    <T> void remove (Class<T> tClass, Object id);
    List<Product> getProductList();
    List<WeightUnit> getUnitsList();
    List<DocumentOrganisation> getDocumentOrganisationList();
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
    List<LaboratoryTurn> getLaboratoryTurnByTurn(Turn turn);
    Forpress getForpressById(Object forpressId);
    ExtractionCrude getExtractionCrudeById(Object id);
    Collection<Organisation> findOrganisation(String key);
    List<LoadPlan> getActiveTransportations();
    List<Deal> getArchiveDeals(DealType type);
    DocumentOrganisation getDocumentOrganisationByValue(Object value);
    WeightUnit getWeightUnitById(Object unit);
    BotUID getBotUidByWorker(Worker worker);
    BotUID getBotUidByUid(String uid);
    List<StorageTurn> getLimitStorageTurns();
    User getUserByUID(String uid);
    List<ProductProperty> getProductProperties(Product product);
    List<ExtractionTurn> getLimitExtractionTurns();
    List<Worker> findWorker(Object key);
    TurnProtein getExtractionTurnProteinById(long id);
    OilMassFraction getOilMassFractionById(long id);

    List<Vehicle> findVehicle(Object key);

    List<OrganisationType> getOrganisationTypeList();

    Organisation findOrganisation(String type, String name);

    List<Storage> getStoragesByAnalysesType(AnalysesType type);

    List<BotSettings> getBotSettingsByWorker(Worker worker);

    User getUserByEmail(String email);
}
