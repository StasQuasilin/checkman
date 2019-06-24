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
import entity.transport.ActionTime;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.Weight;
import entity.weight.WeightUnit;
import utils.TurnDateTime;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
    public List<VROTurn> getTurns(HashMap<String, Object> parameters) {
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
    public LoadPlan[] getDriverList() {
        return new LoadPlan[0];
    }

    @Override
    public void savePerson(Person person) {

    }

    @Override
    public Person getPersonById(Object personId) {
        return null;
    }

    @Override
    public void saveWorker(Worker worker, User user) {

    }

    @Override
    public List<User> getUsersByToken(String token) {
        return null;
    }

    @Override
    public Worker getWorkerById(Object id) {
        return null;
    }

    @Override
    public void saveCakeAnalyses(CakeAnalyses cakeAnalyses) {

    }

    @Override
    public void remove(Object o) {

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
    public List<SunProbe> getSunProbes() {
        return null;
    }

    @Override
    public List<OilProbe> getOilProbes() {
        return null;
    }

    @Override
    public void saveChangeLod(ChangeLog log) {

    }

    @Override
    public void saveChange(Change change) {

    }

    @Override
    public LoadPlan getLoadPlanByTransportationId(Object id) {
        return null;
    }

    @Override
    public void saveTransportation(ActionTime time, Transportation transportation) {

    }

    @Override
    public <T> void remove(Class<T> tClass, Object id) {

    }

    @Override
    public List<Product> getProductList() {
        return null;
    }

    @Override
    public List<WeightUnit> getUnitsList() {
        return null;
    }

    @Override
    public List<DocumentOrganisation> getDocumentOrganisationList() {
        return null;
    }

    @Override
    public List<Seal> findSeal(String key) {
        return null;
    }

    @Override
    public Turn getTurnByDate(TurnDateTime turnDate) {
        return hb.get(Turn.class, "date", Timestamp.valueOf(turnDate.getDate()));
    }

    @Override
    public Driver getDriverByID(Object id) {
        return null;
    }

    @Override
    public List<Transportation> getTransportationsByDriver(Driver driver) {
        return null;
    }

    @Override
    public OrganisationType getOrganisationTypeByName(String type) {
        return null;
    }

    @Override
    public Seal getSealById(Object sealId) {
        return null;
    }

    @Override
    public VROOil getVROOilById(Object id) {
        return null;
    }

    @Override
    public List<Forpress> getForpressList() {
        return null;
    }

    @Override
    public OilMassFractionDry getOilMassFractionDry(Object id) {
        return null;
    }

    @Override
    public Transportation getTransportationById(Object id) {
        return null;
    }

    @Override
    public KPOPart getKPOPartById(Object id) {
        return null;
    }

    @Override
    public SunProbe getSunProbeById(Object id) {
        return null;
    }

    @Override
    public OilProbe getOilProbeById(Object id) {
        return null;
    }

    @Override
    public Vehicle getVehicleById(Object id) {
        return null;
    }

    @Override
    public VRODaily getVRODailyById(Object id) {
        return null;
    }

    @Override
    public List<LoadPlan> getLastPlans() {
        return null;
    }

    @Override
    public DocumentUID getDocumentUID(String uid) {
        return null;
    }

    @Override
    public User getUserByWorker(Worker worker) {
        return null;
    }

    @Override
    public VROCrude getVroCrudeById(Object id) {
        return null;
    }

    @Override
    public List<LaboratoryTurn> getLaboratoryTurnByTurn(Turn turn) {
        return null;
    }

    @Override
    public Forpress getForpressById(Object forpressId) {
        return null;
    }

    @Override
    public ExtractionCrude getExtractionCrudeById(Object id) {
        return null;
    }

    @Override
    public Collection<Organisation> findOrganisation(String key) {
        return null;
    }

    @Override
    public List<LoadPlan> getActiveTransportations() {
        return null;
    }

    @Override
    public List<Deal> getArchiveDeals(DealType type) {
        return null;
    }

    @Override
    public DocumentOrganisation getDocumentOrganisationByValue(Object value) {
        return null;
    }

    @Override
    public WeightUnit getWeightUnitById(Object unit) {
        return null;
    }

    @Override
    public BotUID getBotUidByWorker(Worker worker) {
        return null;
    }

    @Override
    public BotUID getBotUidByUid(String uid) {
        return null;
    }

    @Override
    public List<StorageTurn> getLimitStorageTurns() {
        return null;
    }

    @Override
    public User getUserByUID(String uid) {
        return null;
    }

    @Override
    public List<ProductProperty> getProductProperties(Product product) {
        return null;
    }

    @Override
    public List<ExtractionTurn> getLimitExtractionTurns() {
        return null;
    }

    @Override
    public List<Worker> findWorker(Object key) {
        return null;
    }

    @Override
    public TurnProtein getExtractionTurnProteinById(long id) {
        return null;
    }
}
