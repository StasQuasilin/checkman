package bot;

import entity.Admin;
import entity.DealType;
import entity.User;
import entity.Worker;
import entity.bot.UserBotSetting;
import entity.documents.LoadPlan;
import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.subdivisions.extraction.*;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.laboratory.subdivisions.vro.ForpressCake;
import entity.laboratory.subdivisions.vro.VROCrude;
import entity.products.Product;
import entity.products.ProductProperty;
import entity.transport.Transportation;
import entity.weight.Weight;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import utils.DateUtil;
import utils.LanguageBase;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Date;
import java.util.*;

/**
 * Created by szpt_user045 on 17.04.2019.
 */
public class Notificator {

    private static final String TRANSPORT = "bot.notificator.transport";
    private static final String TRANSPORT_IN = "bot.notificator.transport.in";
    private static final String TRANSPORT_OUT_BUY = "bot.notificator.transport.out.buy";
    private static final String TRANSPORT_DONE = "bot.notification.transport.done";
    private static final String BRUTTO = "bot.notificator.brutto";
    private static final String TARA = "bot.notificator.tara";
    private static final String NETTO = "bot.notificator.netto";
    private static final String HUMIDITY_1 = "bot.notificator.humidity1";
    private static final String HUMIDITY_2 = "bot.notificator.humidity2";
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
    private static final String TRANSPORT_REGISTRATION = "bot.transport.registration";
    private static final String TRANSPORT_INTO = "bot.transport.into";
    private static final String TRANSPORTATION_WEIGHT = "bot.transport.weight";

    private final LanguageBase lb = LanguageBase.getBase();
    private final TelegramBot telegramBot;
    private dbDAO dao = dbDAOService.getDAO();

    public Notificator(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void transportShow(Transportation transportation){
        String message;
        for (UserBotSetting setting : getSettings()){
            if (setting.isShow()) {
                Worker worker = setting.getWorker();
                boolean show = false;
                switch (setting.getTransport()) {
                    case my:
                        show = transportation.getCreator().getId() == worker.getId();
                        break;
                    case all:
                        show = true;
                        break;
                }
                if (show && transportation.getTimeIn() != null && !transportation.isArchive()) {
                    String language = setting.getLanguage();
                    message = prepareMessage(transportation);
                    message += "\n" + lb.get(language, TRANSPORT_IN);
                    sendMessage(setting.getTelegramId(), message, null);
                }
            }
        }
    }

    public void transportRegistration(Transportation transportation){
        transportRegistration(transportation, lb.get(TRANSPORT_REGISTRATION));
    }

    public void transportInto(Transportation transportation){
        transportRegistration(transportation, lb.get(TRANSPORT_INTO));
    }

    public void transportRegistration(Transportation transportation, String messageFormat) {
        DealType type = transportation.getType();
        String action = lb.get("_" + type.toString()).toLowerCase();

        Product product = transportation.getProduct();
        ProductProperty productProperty = dao.getProductProperty(product, type.toString());
        String productName = (productProperty != null ? productProperty.getValue() : product.getName()).toLowerCase();
        String driver = "--";
        if (transportation.getDriver() != null) {
            driver = transportation.getDriver().getPerson().getValue();
        }
        String organisation = transportation.getCounterparty().getValue();
        String message = String.format(messageFormat, action, productName, driver, organisation);

        getSettings().stream().filter(UserBotSetting::isShow).forEach(setting -> sendMessage(setting.getTelegramId(), message, null));
    }

    public void weightShow(Transportation transportation) {

        DealType type = transportation.getType();
        String action = lb.get("_" + type + ".do");
        String driver = "--";
        if (transportation.getDriver() !=null){
            driver = transportation.getDriver().getPerson().getValue();
        }
        String organisation = transportation.getCounterparty().getValue();
        String product = transportation.getProduct().getName();
        String message = String.format(lb.get(TRANSPORTATION_WEIGHT), action, driver, organisation, product, transportation.getWeight().getNetto());

        getSettings().stream().filter(UserBotSetting::isShow).forEach(setting -> {
            sendMessage(setting.getTelegramId(), message, null);
        });
    }
    public void sunAnalysesShow(Transportation transportation, SunAnalyses analyses) {
        String message;
        for (UserBotSetting setting : getSettings()){
            if (setting.isShow()) {
                Worker worker = setting.getWorker();
                boolean show = false;
                switch (setting.getTransport()) {
                    case my:
                        show = transportation.getId() == worker.getId();
                        break;
                    case all:
                        show = true;
                        break;
                }
                if (show) {

                    float humidity1 = analyses.getHumidity1();
                    float humidity2 = analyses.getHumidity2();
                    float soreness = analyses.getSoreness();
                    float oiliness = analyses.getOiliness();
                    float oilImpurity = analyses.getOilImpurity();
                    float acid = analyses.getAcidValue();

                    message = "";//prepareMessage(plan);

                    if (humidity1 > 0) {
                        message += "\n" + String.format(lb.get(HUMIDITY_1), humidity1);
                    }

                    if (humidity2 > 0) {
                        message += "\n" + String.format(lb.get(HUMIDITY_2), humidity2);
                    }

                    if (soreness > 0) {
                        message += "\n" + String.format(lb.get(SORENESS), soreness);
                    }

                    if (oiliness > 0) {
                        message += "\n" + String.format(lb.get(OILINESS), oiliness);
                    }

                    if (oilImpurity > 0) {
                        message += "\n" + String.format(lb.get(OIL_IMPURITY), oilImpurity);
                    }

                    if (acid > 0) {
                        message += "\n" + String.format(lb.get(ACID), acid);
                    }

                    sendMessage(setting.getTelegramId(), message, null);
                }
            }
        }
    }
    public void cakeAnalysesShow(LoadPlan plan, MealAnalyses analyses) {


        String message;
        for (UserBotSetting setting : getSettings()){
            if(setting.isShow()) {
                Worker worker = setting.getWorker();
                boolean show = false;
                switch (setting.getTransport()) {
                    case my:
                        show = plan.getDeal().getCreator().getId() == worker.getId();
                        break;
                    case all:
                        show = true;
                        break;
                }
                if (show) {
                    message = "";//prepareMessage(plan);
                    message += "\n" + String.format(lb.get(HUMIDITY_1), analyses.getHumidity());
                    message += "\n" + String.format(lb.get(PROTEIN), analyses.getProtein());
                    message += "\n" + String.format(lb.get(CELLULOSE), analyses.getCellulose());
                    message += "\n" + String.format(lb.get(OILINESS), analyses.getOiliness());

                    sendMessage(setting.getTelegramId(), message, null);
                }
            }
        }
    }
    public void oilAnalysesShow(Transportation transportation, OilAnalyses analysesList) {
        boolean organoleptic = analysesList.isOrganoleptic();
        float color = analysesList.getColor();
        float acid = analysesList.getAcidValue();
        float peroxide = analysesList.getPeroxideValue();
        float phosphorus = analysesList.getPhosphorus();
        float humidity = analysesList.getHumidity();
        boolean soap = analysesList.isSoap();
        float wax = analysesList.getWax();

        String message;
        for (UserBotSetting setting : getSettings()){
            if (setting.isShow()) {
                Worker worker = setting.getWorker();
                boolean show = false;
                switch (setting.getTransport()) {
                    case my:
                        show = transportation.getCreator().getId() == worker.getId();
                        break;
                    case all:
                        show = true;
                        break;
                }
                if (show) {
                    message = "";//prepareMessage(plan);
                    message += "\n" + lb.get("oil.organoleptic") + ": " + (organoleptic ? lb.get("oil.organoleptic.match") : lb.get("oil.organoleptic.doesn't.match"));
                    message += "\n" + String.format(lb.get(COLOR), Math.round(color));
                    message += "\n" + String.format(lb.get(ACID), acid);
                    message += "\n" + String.format(lb.get(PEROXIDE), peroxide);
                    message += "\n" + String.format(lb.get(PHOSPHORUS), phosphorus);
                    message += "\n" + String.format(lb.get(HUMIDITY_1), humidity);
                    message += "\n" + String.format(lb.get(SOAP), (soap ? lb.get("oil.organoleptic.match") : lb.get("oil.organoleptic.doesn't.match")));
                    message += "\n" + String.format(lb.get(WAX), wax);

                    sendMessage(setting.getTelegramId(), message, null);
                }
            }
        }
    }
    public void extractionShow(ExtractionCrude crude){
        int turnNumber = crude.getTurn().getTurn().getNumber();
        String turnDate = DateUtil.prettyDate(Date.valueOf(crude.getTurn().getTurn().getDate().toLocalDateTime().toLocalDate()));
        String turnTime = crude.getTime().toLocalDateTime().toLocalTime().toString();
        final HashMap<String, String> messages = new HashMap<>();

        for (UserBotSetting setting : getSettings()) {
            if (setting.isExtraction() && setting.isShow()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    String message = lb.get(language, "extraction.title");
                    message += "\n" + String.format(lb.get(language, "extraction.turn"), turnNumber, turnDate, turnTime);
                    message += "\n" + String.format(lb.get(language, "extraction.humidity.income"), crude.getHumidityIncome());
                    message += "\n" + String.format(lb.get(language, "extraction.fraction"), crude.getFraction());
                    message += "\n" + String.format(lb.get(language, "extraction.dissolvent"), crude.getDissolvent());
                    message += "\n" + String.format(lb.get(language, "extraction.miscellas"), crude.getMiscellas());
                    message += "\n" + String.format(lb.get(language, "extraction.humidity"), crude.getHumidity());
                    //todo organoleptic
                    message += "\n" + String.format(lb.get(language, "extraction.oiliness"), crude.getGrease());
                    //message += "\n" + String.format(lb.get(language, "extraction.explosion.t"), crude.get)
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }

    public void vroShow(VROCrude crude, List<ForpressCake> cakes) {
        final int turn = crude.getTurn().getTurn().getNumber();
        String date = DateUtil.prettyDate(Date.valueOf(crude.getTime().toLocalDateTime().toLocalDate()));
        String time = crude.getTime().toLocalDateTime().toLocalTime().toString();
        final HashMap<String, String> messages = new HashMap<>();

        for (UserBotSetting setting : getSettings()) {
            if(setting.isVro() && setting.isShow()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = lb.get(language, "notification.vro.title");

                    message += "\n" + String.format(lb.get(language, "extraction.turn"), turn, date, time);
                    message += "\n" + lb.get(language, "notification.vro.before");
                    message += "\n" + String.format(lb.get(language, "notification.vro.humidity"), crude.getHumidityBefore());
                    message += "\n" + String.format(lb.get(language, "notification.vro.soreness"), crude.getSorenessBefore());
                    message += "\n" + lb.get(language, "notification.vro.after");
                    message += "\n" + String.format(lb.get(language, "notification.vro.humidity"), crude.getHumidityAfter());
                    message += "\n" + String.format(lb.get(language, "notification.vro.soreness"), crude.getSorenessAfter());
                    message += "\n" + String.format(lb.get(language, "notification.vro.kernel.offset.2"), crude.getKernelOffset());
                    message += "\n" + String.format(lb.get(language, "notification.vro.huskiness.2"), crude.getHuskiness());
                    message += "\n" + String.format(lb.get(language, "notification.vro.pulp.1"), crude.getPulpHumidity1());
                    message += "\n" + String.format(lb.get(language, "notification.vro.pulp.2"), crude.getPulpHumidity2());

                    message += "\n";
                    if (cakes.size() > 0) {
                        message += "\n" + lb.get(language, "notification.forpress.cake.title");
                        for (ForpressCake cake : cakes) {
                            message += "\n*" + cake.getForpress().getName() + "*";
                            message += "\n\t        " + String.format(lb.get(language, "bot.notificator.humidity"), cake.getHumidity());
                            message += "\n\t        " + String.format(lb.get(language, "bot.notificator.oiliness"), cake.getOiliness());
                        }
                    }
                    messages.put(language, message);
                }
                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }

    String prepareMessage(Transportation transportation){
        DealType type = transportation.getType();
        String vehicle = transportation.getVehicle() != null ? transportation.getVehicle().getValue() : lb.get(NO_DATA);
        String driver = transportation.getDriver() != null ? transportation.getDriver().getPerson().getValue() : lb.get(NO_DATA);
        String organisation = transportation.getCounterparty().getValue();
        String product = transportation.getProduct().getName();
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
    private void sendMessage(long telegramId, String message, ReplyKeyboard keyboard) {
        telegramBot.sendMsg(telegramId, message, keyboard);
    }
    public void extractionShow(StorageProtein storageProtein) {
        String storage = storageProtein.getStorage().getName();
        String time = storageProtein.getTime().toLocalDateTime().toLocalTime().toString();
        final HashMap<String, String> messages = new HashMap<>();
        for (UserBotSetting setting : getSettings()) {
            if (setting.isShow() && setting.isExtraction()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = String.format(lb.get(language, "extraction.storage.protein.title"), storage, time);
                    message += "\n" + String.format(lb.get(language, "extraction.storage.protein"), storageProtein.getProtein());
                    message += "\n" + String.format(lb.get(language, "extraction.storage.humidity"), storageProtein.getHumidity());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }
    public void extractionShow(ExtractionOIl oil) {
        final int turn = oil.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(oil.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();
        for (UserBotSetting setting : getSettings()) {
            if (setting.isShow() && setting.isExtraction()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = lb.get(language, "extraction.oil.title");
                    message += "\n" + String.format(lb.get(language, "extraction.oil.turn"), turn, turnDate);
                    message += "\n" + String.format(lb.get(language, "extraction.oil.humidity"), oil.getHumidity());
                    message += "\n" + String.format(lb.get(language, "extraction.oil.acid.value"), oil.getAcid());
                    message += "\n" + String.format(lb.get(language, "extraction.oil.peroxide.value"), oil.getPeroxide());
                    message += "\n" + String.format(lb.get(language, "extraction.oil.phosphorus.value"), oil.getPhosphorus());
                    message += "\n" + String.format(lb.get(language, "extraction.oil.explosion.t"), oil.getExplosionT());
                    messages.put(language, message);
                }


                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }
    public void extractionShow(TurnProtein turnProtein) {
        final int turn = turnProtein.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(turnProtein.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();

        for (UserBotSetting setting : getSettings()){
            if (setting.isShow() && setting.isExtraction()){
                final String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    String message = lb.get(language, "extraction.turn.protein.title");
                    message += "\n" + String.format(lb.get(language, "extraction.turn.protein.turn"), turn, turnDate);
                    message += "\n" + String.format(lb.get(language, "extraction.turn.protein.protein"), turnProtein.getProtein());
                    message += "\n" + String.format(lb.get(language, "extraction.turn.protein.humidity"), turnProtein.getHumidity());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }

    }
    public void extractionShow(TurnGrease turnGrease) {
        final int turn = turnGrease.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(turnGrease.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();
        for (UserBotSetting setting : getSettings()){
            if (setting.isShow() && setting.isExtraction()){
                final String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = lb.get(language, "extraction.turn.grease.title");
                    message += "\n" + String.format(lb.get(language, "extraction.turn.protein.turn"), turn, turnDate);
                    message += "\n" + String.format(lb.get(language, "extraction.turn.grease.grease"), turnGrease.getGrease());
                    message += "\n" + String.format(lb.get(language, "extraction.turn.protein.humidity"), turnGrease.getHumidity());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }
    public void extractionShow(StorageGrease storageGrease) {
        String storage = storageGrease.getStorage().getName();
        String time = storageGrease.getTime().toLocalDateTime().toLocalTime().toString();
        final HashMap<String, String> messages = new HashMap<>();
        for (UserBotSetting setting : getSettings()) {
            if (setting.isShow() && setting.isExtraction()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = String.format(lb.get(language, "extraction.storage.grease.title"),
                            storage, time);
                    message += "\n" + String.format(lb.get(language, "extraction.turn.grease"), storageGrease.getGrease());
                    message += "\n" + String.format(lb.get(language, "extraction.storage.humidity"), storageGrease.getHumidity());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }
    public void kpoShow(KPOPart part) {
        final HashMap<String, String> messages = new HashMap<>();
        for (UserBotSetting setting : getSettings()) {
            if(setting.isShow() && setting.isVro()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    String message = String.format(lb.get(language, "notification.kpo.title"), part.getNumber());
                    message += "\n" + String.format(lb.get(language, "notification.kpo.organoleptic"),
                            (part.isOrganoleptic() ?
                                    lb.get(language, "notification.kpo.organoleptic.yes") :
                                    lb.get(language, "notification.kpo.organoleptic.no")
                            ));
                    message += "\n" + String.format(lb.get(language, "notification.kpo.acid"), part.getAcid());
                    message += "\n" + String.format(lb.get(language, "notification.kpo.peroxide"), part.getPeroxide());
                    message += "\n" + String.format(lb.get(language, "notification.kpo.soap"),
                            (part.isSoap() ?
                                    lb.get(language, "notification.kpo.soap.yes") :
                                    lb.get(language, "notification.kpo.soap.no")
                            ));
                    messages.put(language, message);
                }
                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }

    public void storagesShow(OilAnalyses oilAnalyses) {

    }
    final InlineKeyboardMarkup registrationKeyboard = new InlineKeyboardMarkup();
    final List<List<InlineKeyboardButton>> list = new ArrayList<>();
    final List<InlineKeyboardButton> buttons = new ArrayList<>();
    {
        list.add(buttons);
        registrationKeyboard.setKeyboard(list);
    }
    public void registrationShow(User user) {
        Admin admin = telegramBot.getAdmin();
        if (admin != null) {
            for (UserBotSetting setting : getSettings()) {
                if (setting.getWorker().getId() == admin.getWorker().getId() && setting.getTelegramId() > 0){
                    String language = setting.getLanguage();
                    String message = lb.get(language, "bot.user.registration.title");
                    message += "\n" + String.format(lb.get(language, "bot.user.registration.registrator"), user.getRegistrator().getValue());
                    message += "\n" + lb.get(language, "bot.user.registration.text");
                    message += "\n" + String.format(lb.get(language, "bot.user.registration.user"), user.getWorker().getValue());
                    message += "\n" + String.format(lb.get(language, "bot.user.registration.role"), lb.get(language, user.getRole().toString()));
                    message += "\n" + String.format(lb.get(language, "bot.user.registration.email"), user.getEmail());

                    buttons.add(new InlineKeyboardButton().setText("Yes").setCallbackData(String.valueOf(user.getId())));
                    buttons.add(new InlineKeyboardButton().setText("No").setCallbackData(String.valueOf(user.getId())));
                    sendMessage(setting.getTelegramId(), message, registrationKeyboard);
                    break;
                }
            }
        }

    }


}
