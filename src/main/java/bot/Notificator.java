package bot;

import entity.DealType;
import entity.Worker;
import entity.bot.UserBotSetting;
import entity.documents.LoadPlan;
import entity.laboratory.CakeAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.vro.ForpressCake;
import entity.laboratory.subdivisions.vro.VROCrude;
import entity.transport.Transportation;
import entity.weight.Weight;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import utils.LanguageBase;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 17.04.2019.
 */
public class Notificator {

    private static final String TRANSPORT = "bot.notificator.transport";
    private static final String TRANSPORT_IN = "bot.notificator.transport.in";
    private static final String TRANSPORT_OUT = "bot.notificator.transport.out";
    private static final String TRANSPORT_DONE = "bot.notification.transport.done";
    private static final String BRUTTO = "bot.notificator.brutto";
    private static final String TARA = "bot.notificator.tara";
    private static final String NETTO = "bot.notificator.netto";
    private static final String HUMIDITY = "bot.notificator.humidity";
    private static final String SORENESS = "bot.notificator.soreness";
    private static final String OILINESS = "bot.notificator.oiliness";
    private static final String PROTEIN = "bot.notificator.protein";
    private static final String CELLULOSE = "";
    private static final String OIL_IMPURITY = "bot.notificator.oil.impurity";
    private static final String ACID = "bot.notificator.oil.acid";
    private static final String COLOR = "bot.notificator.color";
    private static final String PEROXIDE = "bot.notificator.peroxide";
    private static final String PHOSPHORUS = "bot.notificator.phosphorus";
    private static final String SOAP = "bot.notificator.soap";
    private static final String WAX = "bot.notificator.wax";

    
    private static final String NO_DATA = "no.data";

    private final LanguageBase lb = LanguageBase.getBase();
    private final TelegramBot telegramBot;

    public Notificator(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
    public void transportShow(LoadPlan plan){
        String message;
        for (UserBotSetting setting : getSettings()){
            Worker worker = setting.getWorker();
            boolean show = false;
            switch (setting.getTransport()){
                case my:
                    show = plan.getDeal().getCreator().getId() == worker.getId();
                    break;
                case all:
                    show = true;
                    break;
            }
            if (show){

                message = prepareMessage(plan);
                Transportation transportation = plan.getTransportation();
                if (transportation.getTimeIn() != null && transportation.getTimeOut() != null) {
                    message += "\n" + lb.get(TRANSPORT_DONE);
                } else if (transportation.getTimeIn() != null){
                    message += "\n" + lb.get(TRANSPORT_IN);
                } else {
                    message += "\n" + lb.get(TRANSPORT_OUT);
                }
                sendMessage(setting.getTelegramId(), message);
            }
        }
    }
    public void weightShow(LoadPlan plan, List<Weight> weightList) {
        String message;
        for (UserBotSetting setting : getSettings()){
            Worker worker = setting.getWorker();
            boolean show = false;
            switch (setting.getTransport()){
                case my:
                    show = plan.getDeal().getCreator().getId() == worker.getId();
                    break;
                case all:
                    show = true;
                    break;
            }
            if (show){

                float brutto = 0;
                float tara = 0;

                for (Weight weight : weightList){
                    brutto += weight.getBrutto();
                    tara += weight.getTara();
                }
                float netto = 0;
                if (brutto > 0 && tara > 0){
                    netto = brutto - tara;
                }
                message = prepareMessage(plan);
                if (brutto > 0){
                    message += "\n" + String.format(lb.get(BRUTTO), brutto);
                }
                if (tara > 0){
                    message += "\n" + String.format(lb.get(TARA), tara);
                }
                if (netto > 0){
                    message += "\n" + String.format(lb.get(NETTO), netto);
                }
                sendMessage(setting.getTelegramId(), message);
            }
        }
    }
    public void sunAnalysesShow(LoadPlan plan, LinkedList<SunAnalyses> analysesList) {
        String message;
        for (UserBotSetting setting : getSettings()){
            Worker worker = setting.getWorker();
            boolean show = false;
            switch (setting.getTransport()){
                case my:
                    show = plan.getDeal().getCreator().getId() == worker.getId();
                    break;
                case all:
                    show = true;
                    break;
            }
            if (show){

                float humidity = 0;
                float soreness = 0;
                float oiliness = 0;
                float oilImpurity = 0;
                float acid = 0;
                int count = 0;
                for (SunAnalyses analyses : analysesList){
                    humidity += analyses.getHumidity();
                    soreness += analyses.getSoreness();
                    oiliness += analyses.getOiliness();
                    oilImpurity += analyses.getOilImpurity();
                    acid += analyses.getAcidValue();
                    count ++;
                }
                
                humidity /= count;
                soreness /= count;
                oiliness /= count;
                oilImpurity /= count;
                acid /= count;

                message = prepareMessage(plan);
                
                if (humidity > 0){
                    message += "\n" + String.format(lb.get(HUMIDITY), humidity);
                }

                if (soreness > 0){
                    message += "\n" + String.format(lb.get(SORENESS), soreness);
                }

                if (oiliness > 0){
                    message += "\n" + String.format(lb.get(OILINESS), oiliness);
                }

                if (oilImpurity > 0){
                    message += "\n" + String.format(lb.get(OIL_IMPURITY), oilImpurity);
                }

                if (acid > 0){
                    message += "\n" + String.format(lb.get(ACID), acid);
                }
                
                sendMessage(setting.getTelegramId(), message);
            }
        }
    }
    public void cakeAnalysesShow(LoadPlan plan, List<CakeAnalyses> analysesList) {
        float humidity = 0;
        float protein = 0;
        float cellulose = 0;
        float oiliness = 0;
        int count = 0;
        for (CakeAnalyses analyses : analysesList) {
            humidity += analyses.getHumidity();
            protein += analyses.getProtein();
            cellulose += analyses.getCellulose();
            oiliness += analyses.getOiliness();
            count++;
        }
        
        humidity /= count;
        protein /= count;
        cellulose /= count;
        oiliness /= count;

        String message;
        for (UserBotSetting setting : getSettings()){
            Worker worker = setting.getWorker();
            boolean show = false;
            switch (setting.getTransport()){
                case my:
                    show = plan.getDeal().getCreator().getId() == worker.getId();
                    break;
                case all:
                    show = true;
                    break;
            }
            if (show){
                message = prepareMessage(plan);
                message += "\n" + String.format(lb.get(HUMIDITY), humidity);
                message += "\n" + String.format(lb.get(PROTEIN), protein);
                message += "\n" + String.format(lb.get(CELLULOSE), cellulose);
                message += "\n" + String.format(lb.get(OILINESS), oiliness);

                sendMessage(setting.getTelegramId(), message);
            }
        }
        
        
    }
    public void oilAnalysesShow(LoadPlan plan, LinkedList<OilAnalyses> analysesList) {
        float organolepticFloat = 0;
        float color = 0;
        float acid = 0;
        float peroxide = 0;
        float phosphorus = 0;
        float humidity = 0;
        float soap = 0;
        float wax = 0;
        int count = 0;
        for (OilAnalyses analyses : analysesList){
            organolepticFloat += analyses.isOrganoleptic() ? 1 : 0;
            color += analyses.getColor();
            acid += analyses.getAcidValue();
            peroxide += analyses.getPeroxideValue();
            phosphorus += analyses.getPhosphorus();
            humidity += analyses.getHumidity();
            soap += analyses.getSoap();
            wax += analyses.getWax();
            count ++;
        }

        boolean organoleptic = organolepticFloat / count > 0.5;
        color /= count;
        acid /= count;
        peroxide /= count;
        phosphorus /= count;
        humidity /= count;
        soap /= count;
        wax /= count;

        String message;
        for (UserBotSetting setting : getSettings()){
            Worker worker = setting.getWorker();
            boolean show = false;
            switch (setting.getTransport()){
                case my:
                    show = plan.getDeal().getCreator().getId() == worker.getId();
                    break;
                case all:
                    show = true;
                    break;
            }
            if (show){
                message = prepareMessage(plan);
                message += "\n" + lb.get("oil.organoleptic") + ": " + (organoleptic ? lb.get("oil.organoleptic.match") : lb.get("oil.organoleptic.doesn't.match"));
                message += "\n" + String.format(lb.get(COLOR), Math.round(color));
                message += "\n" + String.format(lb.get(ACID), acid);
                message += "\n" + String.format(lb.get(PEROXIDE), peroxide);
                message += "\n" + String.format(lb.get(PHOSPHORUS), phosphorus);
                message += "\n" + String.format(lb.get(HUMIDITY), humidity);
                message += "\n" + String.format(lb.get(SOAP), soap);
                message += "\n" + String.format(lb.get(WAX), wax);

                sendMessage(setting.getTelegramId(), message);
            }
        }
    }
    public void extractionShow(ExtractionCrude crude){
        for (UserBotSetting setting : getSettings()) {
            if (setting.isExtraction() && setting.isShow()) {
                String language = setting.getLanguage();

                String message = lb.get(language, "extraction.title");
                message += "\n" + String.format(lb.get(language, "extraction.turn"), crude.getTurn().getNumber(),
                        crude.getTime().toLocalDateTime().toString());
                message += "\n" + String.format(lb.get(language, "extraction.humidity.income"), crude.getHumidityIncome());
                message += "\n" + String.format(lb.get(language, "extraction.fraction"), crude.getFraction());
                message += "\n" + String.format(lb.get(language, "extraction.dissolvent"), crude.getDissolvent());
                message += "\n" + String.format(lb.get(language, "extraction.miscellas"), crude.getMiscellas());
                message += "\n" + String.format(lb.get(language, "extraction.humidity"), crude.getHumidity());
                //todo organoleptic
                message += "\n" + String.format(lb.get(language, "extraction.oiliness"), crude.getGrease());
                //message += "\n" + String.format(lb.get(language, "extraction.explosion.t"), crude.get)

                sendMessage(setting.getTelegramId(), message);
            }
        }
    }

    public void vroShow(VROCrude crude) {
        for (UserBotSetting setting : getSettings()) {
            if(setting.isKpo() && setting.isShow()) {
                String language = setting.getLanguage();
                String message = lb.get(language, "vro.title");
                message += "\n" + String.format(lb.get(language, "extraction.turn"), crude.getTurn().getNumber(),
                         crude.getTime().toLocalDateTime().toString());
                message += "\n" + String.format(lb.get(language, "vro.humidity.before"), crude.getHumidityBefore());
                message += "\n" + String.format(lb.get(language, "vro.soreness.before"), crude.getSorenessBefore());
                message += "\n" + String.format(lb.get(language, "vro.humidity.after"), crude.getHumidityAfter());
                message += "\n" + String.format(lb.get(language, "vro.soreness.after"), crude.getSorenessAfter());
                message += "\n" + String.format(lb.get(language, "vro.pulp.1"), crude.getPulpHumidity1());
                message += "\n" + String.format(lb.get(language, "vro.pulp.2"), crude.getPulpHumidity2());
                if (crude.getForpressCakes() != null) {
                    for (ForpressCake cake : crude.getForpressCakes()) {
                        message += "\n" + String.format(lb.get(language, "vro.forpress.humidity"), cake.getHumidity());
                        message += "\n" + String.format(lb.get(language, "vro.forpress.oiliness"), cake.getOiliness());
                    }
                }
                message += "\n" + String.format(lb.get(language, "vro.kernel.offset.2"), crude.getKernelOffset());
                message += "\n" + String.format(lb.get(language, "vro.huskiness.2"), crude.getHuskiness());

                sendMessage(setting.getTelegramId(), message);
            }
        }
    }

    String prepareMessage(LoadPlan plan){
        Transportation transportation = plan.getTransportation();
        DealType type = plan.getDeal().getType();
        String vehicle = transportation.getVehicle() != null ? transportation.getVehicle().getValue() : lb.get(NO_DATA);
        String driver = transportation.getDriver() != null ? transportation.getDriver().getPerson().getValue() : lb.get(NO_DATA);
        String organisation = plan.getDeal().getOrganisation().getValue();
        String product = plan.getDeal().getProduct().getName();
        String action = lb.get("_" + type.toString()).toLowerCase();

        return String.format(
                lb.get(TRANSPORT),
                vehicle,
                driver,
                organisation,
                action + " " + product);
    }
    private Collection<UserBotSetting> getSettings() {
        return telegramBot.getBotSettings().get();
    }
    private void sendMessage(long telegramId, String message) {
        telegramBot.sendMsg(telegramId, message);
    }


}
