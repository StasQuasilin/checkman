package bot;

import constants.Constants;
import entity.*;
import entity.bot.NotifyStatus;
import entity.bot.UserBotSetting;
import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.storages.StorageAnalyses;
import entity.laboratory.subdivisions.extraction.*;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.laboratory.subdivisions.vro.*;
import entity.products.Product;
import entity.products.ProductProperty;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import entity.transport.Transportation;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.DateUtil;
import utils.LanguageBase;
import utils.U;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Date;
import java.time.LocalDateTime;
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
    private static final String CELLULOSE = "bot.notificator.cellulose";
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
    private static final String TURN = "turn";
    private static final String ORGANOLEPTIC = "oil.organoleptic";
    private static final String MATCH = "oil.organoleptic.match";
    private static final String NO_MATCH = "oil.organoleptic.match";
    private static final String SOAP_HAVE = "notification.kpo.soap.yes";
    private static final String SOAP_NO = "notification.kpo.soap.no";
    private static final String CORRECTION = "notification.weight.correction";
    private static final String DRY_PROTEIN = "extraction.turn.protein.dry";
    private static final String WAX_HAVE = "notification.kpo.wax.yes";
    private static final String WAX_NO = "notification.kpo.wax.no";
    private static final String HUMIDITY = "bot.extraction.oil.humidity";
    private static final String EMPTY = Constants.EMPTY;

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
                        show = transportation.getManager().getId() == worker.getId();
                        break;
                    case all:
                        show = true;
                        break;
                }
                if (show && transportation.getTimeIn() != null && !transportation.isArchive()) {
                    String language = setting.getLanguage();
                    message = prepareMessage(transportation);
                    message += NEW_LINE + lb.get(language, TRANSPORT_IN);
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
        getSettings().stream().filter(setting -> setting.isShow() && setting.getTransport() != NotifyStatus.off).forEach(setting -> {
            sendMessage(setting.getTelegramId(), message, null);
        });
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
        String message = String.format(lb.get(TRANSPORTATION_WEIGHT), action, driver, organisation, product,
                transportation.getWeight().getBrutto() - transportation.getWeight().getTara()
        );
        for (UserBotSetting setting : getSettings()){
            if (setting.isShow() && setting.getWeight() == NotifyStatus.all){
                sendMessage(setting.getTelegramId(), message, null);
            }
        }
    }
    public void sunAnalysesShow(Transportation transportation, SunAnalyses analyses, float correction) {
        HashMap<String, String> messages = new HashMap<>();
        for (UserBotSetting setting : getSettings()){
            if (setting.isShow()) {
                boolean show = setting.getAnalyses() == NotifyStatus.all;
                if (show) {

                    String language = setting.getLanguage();
                    if (!messages.containsKey(language)){
                        String corr = correction > 0 ? String.format(lb.get(CORRECTION), correction) + NEW_LINE : "";
                        messages.put(language, prepareMessage(transportation) + NEW_LINE +
                            (analyses.isContamination() ? "❗ Заражено шкідниками ❗" : "") +
                            String.format(lb.get(HUMIDITY_1), analyses.getHumidity1()) + NEW_LINE +
                                (analyses.getHumidity2() > 0 ? String.format(lb.get(HUMIDITY_2), analyses.getHumidity2()) + NEW_LINE : "") +
                            String.format(lb.get(SORENESS), analyses.getSoreness()) + NEW_LINE +
                            String.format(lb.get(OILINESS), analyses.getOiliness()) + NEW_LINE +
                            String.format(lb.get(ACID), analyses.getAcidValue()) + NEW_LINE +
                            String.format(lb.get(OIL_IMPURITY), analyses.getOilImpurity()) + NEW_LINE +
                            corr + NEW_LINE
                        );
                    }
                    sendMessage(setting.getTelegramId(), messages.get(language), null);
                }
            }
        }
    }
    public static final String ATTENTION = "";//"‼";
    public void cakeAnalysesShow(Transportation transportation, MealAnalyses analyses) {
        HashMap<String, String> messages = new HashMap<>();
        for (UserBotSetting setting : getSettings()){
            if(setting.isShow()) {
                boolean show = setting.getAnalyses() == NotifyStatus.all;
                if (show) {
                    String language = setting.getLanguage();
                    if (!messages.containsKey(language)){
                        messages.put(language, prepareMessage(transportation) + NEW_LINE +
                                String.format(lb.get(HUMIDITY), analyses.getHumidity())  +
                                        (analyses.getHumidity() >= 11 ? ATTENTION : EMPTY) + NEW_LINE +
                                String.format(lb.get(PROTEIN), analyses.getProtein()) + NEW_LINE +
                                String.format(lb.get(CELLULOSE), analyses.getCellulose()) +
                                        (analyses.getCellulose() > 23 ? ATTENTION : EMPTY)+ NEW_LINE +
                                String.format(lb.get(OILINESS), analyses.getOiliness()) +
                                        (analyses.getOiliness() > 1.5f ? ATTENTION : EMPTY) + NEW_LINE +
                                String.format(lb.get(DRY_PROTEIN), analyses.DryRecalculation()) +
                                        (analyses.DryRecalculation() < 39 ? ATTENTION : EMPTY)

                        );
                    }
                    sendMessage(setting.getTelegramId(), messages.get(language), null);
                }
            }
        }

        messages.clear();
    }
    public void oilAnalysesShow(Transportation transportation, OilAnalyses analyses) {

        HashMap<String, String> messages = new HashMap<>();

        for (UserBotSetting setting : getSettings()){
            if (setting.isShow()) {
                boolean show = setting.getAnalyses() == NotifyStatus.all;
                if (show) {
                    String language = setting.getLanguage();
                    if (!messages.containsKey(language)){
                        StringBuilder builder = new StringBuilder();
                        builder.append(prepareMessage(transportation)).append(NEW_LINE);
                        builder.append(lb.get(ORGANOLEPTIC)).append(analyses.isOrganoleptic() ? lb.get(MATCH) : lb.get(NO_MATCH)).append(NEW_LINE);
                        builder.append(String.format(lb.get(COLOR), analyses.getColor())).append(NEW_LINE);
                        builder.append(String.format(lb.get(ACID), analyses.getAcidValue())).append(NEW_LINE);
                        builder.append(String.format(lb.get(PEROXIDE), analyses.getPeroxideValue())).append(NEW_LINE);
                        if (analyses.getPhosphorus() > 0) {
                            builder.append(String.format(lb.get(PHOSPHORUS), analyses.getPhosphorus())).append(NEW_LINE);
                        }
                        builder.append(String.format(lb.get(HUMIDITY), analyses.getHumidity())).append(NEW_LINE);
                        AnalysesType type = transportation.getProduct().getAnalysesType();

                        if (type == AnalysesType.raf) {
                            //todo wax
                            builder.append(String.format(lb.get(WAX), 
                                    analyses.isWaxB() ? lb.get(WAX_HAVE) : lb.get(WAX_NO))).append(NEW_LINE);
                            builder.append(String.format(lb.get(SOAP), 
                                    (analyses.isSoap() ? lb.get(SOAP_HAVE) : lb.get(SOAP_NO)))).append(NEW_LINE);
                        }
                        if (type == AnalysesType.oil && analyses.getExplosion() > 0){
                            builder.append(String.format(lb.get(language, "bot.notificator.explosion"), analyses.getExplosion()));
                        }
                        messages.put(language, builder.toString());
                    }

                    sendMessage(setting.getTelegramId(), messages.get(language), null);
                }
            }
        }

        messages.clear();
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
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn"), turnNumber, turnDate, turnTime);
                    message += NEW_LINE + String.format(lb.get(language, "extraction.humidity.income"), crude.getHumidityIncome());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oiliness.income"), crude.getOilinessIncome());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.fraction"), crude.getFraction());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.dissolvent"), crude.getDissolvent());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.miscellas"), crude.getMiscellas());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.humidity"), crude.getHumidity());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oiliness"), crude.getGrease());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oil.explosion.t"), crude.getExplosionTemperature());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }

        messages.clear();
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
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn"), turn, date, time);
                    message += NEW_LINE + lb.get(language, "notification.vro.before");
                    message += NEW_LINE + String.format(lb.get(language, "notification.vro.humidity"), crude.getHumidityBefore());
                    message += NEW_LINE + String.format(lb.get(language, "notification.vro.soreness"), crude.getSorenessBefore());
                    message += NEW_LINE + lb.get(language, "notification.vro.after");
                    message += NEW_LINE + String.format(lb.get(language, "notification.vro.humidity"), crude.getHumidityAfter());
                    message += NEW_LINE + String.format(lb.get(language, "notification.vro.soreness"), crude.getSorenessAfter());
                    message += NEW_LINE + String.format(lb.get(language, "notification.vro.kernel.offset.2"), crude.getKernelOffset());
                    message += NEW_LINE + String.format(lb.get(language, "notification.vro.huskiness.2"), crude.getHuskiness());
                    message += NEW_LINE + String.format(lb.get(language, "notification.vro.pulp.1"), crude.getPulpHumidity1());
                    message += NEW_LINE + String.format(lb.get(language, "notification.vro.pulp.2"), crude.getPulpHumidity2());

                    message += NEW_LINE;
                    if (cakes.size() > 0) {
                        message += NEW_LINE + lb.get(language, "notification.forpress.cake.title");
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
        String driver = transportation.getDriver() != null ? transportation.getDriver().getPerson().getValue() : lb.get(NO_DATA);
        String organisation = transportation.getCounterparty().getValue();
        String product = transportation.getProduct().getName();

        return String.format(lb.get(TRANSPORT), organisation, driver, product);
    }
    private Collection<UserBotSetting> getSettings() {
        return telegramBot.getBotSettings().get();
    }
    private Message sendMessage(long telegramId, String message, ReplyKeyboard keyboard) {
        try {
            return telegramBot.sendMsg(telegramId, message, keyboard);
        } catch (TelegramApiException ignore) {}
        return null;
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
                    message += NEW_LINE + String.format(lb.get(language, "extraction.storage.protein"), storageProtein.getProtein());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.storage.humidity"), storageProtein.getHumidity());
                    message += NEW_LINE + String.format(lb.get(language, DRY_PROTEIN), storageProtein.DryRecalculation());
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
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oil.turn"), turn, turnDate);
                    message += NEW_LINE + String.format(lb.get(language, "bot.extraction.oil.humidity"), oil.getHumidity());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oil.acid.value"), oil.getAcid());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oil.peroxide.value"), oil.getPeroxide());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oil.phosphorus.value"), oil.getPhosphorus());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oil.explosion.t"), oil.getExplosionT());
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
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.turn"), turn, turnDate);
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.protein"), turnProtein.getProtein());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.humidity"), turnProtein.getHumidity());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.nmr"), turnProtein.getNuclearGrease());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.dry"), turnProtein.DryRecalculation());
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
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.turn"), turn, turnDate);
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.grease.grease"), turnGrease.getGrease());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.humidity"), turnGrease.getHumidity());
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
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.grease.grease"), storageGrease.getGrease());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.storage.humidity"), storageGrease.getHumidity());
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
                    message += NEW_LINE + String.format(lb.get(language, "notification.kpo.organoleptic"),
                            (part.isOrganoleptic() ?
                                    lb.get(language, "notification.kpo.organoleptic.yes") :
                                    lb.get(language, "notification.kpo.organoleptic.no")
                            ));
                    message += NEW_LINE + String.format(lb.get(language, "bot.notificator.color"), part.getColor());
                    message += NEW_LINE + String.format(lb.get(language, "notification.kpo.acid"), part.getAcid());
                    message += NEW_LINE + String.format(lb.get(language, "notification.kpo.peroxide"), part.getPeroxide());
                    message += NEW_LINE + String.format(lb.get(WAX), part.isWax() ? lb.get(WAX_HAVE) : lb.get(WAX_NO));
                    message += NEW_LINE + String.format(lb.get(language, SOAP),
                            (part.isSoap() ?
                                    lb.get(language, SOAP_HAVE) :
                                    lb.get(language, SOAP_NO)
                            ));
                    messages.put(language, message);
                }
                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }

    public void storagesShow(StorageAnalyses analyses) {
        HashMap<String, String> messages = new HashMap<>();
        for (UserBotSetting setting : getSettings()){
            if (setting.isShow() && setting.getAnalyses() == NotifyStatus.all){
                String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    LocalDateTime dateTime = analyses.getDate().toLocalDateTime();
                    messages.put(language,
                            analyses.getStorage().getName() + NEW_LINE +
                                String.format("%1$s, Час %2$s", DateUtil.prettyDate(Date.valueOf(dateTime.toLocalDate())),
                                        dateTime.toLocalTime().toString().substring(0, 5)) + NEW_LINE +
                                    String.format(lb.get(language, "bot.notificator.phosphorus"), analyses.getOilAnalyses().getPhosphorus()) + NEW_LINE +
                                    String.format(lb.get(language, "bot.notificator.oil.acid"), analyses.getOilAnalyses().getAcidValue()) + NEW_LINE +
                                    String.format(lb.get(language, "notification.kpo.peroxide"), analyses.getOilAnalyses().getPeroxideValue()) + NEW_LINE +
                                    String.format(lb.get(language, "bot.notificator.color"), analyses.getOilAnalyses().getColor())
                            );
                }

                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }

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
                    message += NEW_LINE + String.format(lb.get(language, "bot.user.registration.registrator"), user.getRegistrator().getValue());
                    message += NEW_LINE + lb.get(language, "bot.user.registration.text");
                    message += NEW_LINE + String.format(lb.get(language, "bot.user.registration.user"), user.getWorker().getValue());
                    message += NEW_LINE + String.format(lb.get(language, "bot.user.registration.role"), lb.get(language, user.getRole().toString()));
                    message += NEW_LINE + String.format(lb.get(language, "bot.user.registration.email"), user.getEmail());

                    buttons.add(new InlineKeyboardButton().setText("Yes").setCallbackData(String.valueOf(user.getId())));
                    buttons.add(new InlineKeyboardButton().setText("No").setCallbackData(String.valueOf(user.getId())));
                    sendMessage(setting.getTelegramId(), message, registrationKeyboard);
                    break;
                }
            }
        }

    }

    public static final String HYPHEN = " - ";
    public static final String SPACE = " ";
    public static final String SEMICOLON = "; ";
    public static final String NEW_LINE = "\n";
    public static final String FORMAT = "%1$,.3f";
    public static final String STAR = "*";

    public void manufactureReportShow(ManufactureReport manufactureReport, ArrayList<Message> list) {

        HashMap<String, String> messages = new HashMap<>();

        for (UserBotSetting setting : getSettings()){
            if (setting.isShow() && setting.isReports()){
                String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    StringBuilder builder = new StringBuilder();

                    builder.append(DateUtil.prettyDate(manufactureReport.getTurn().getDate()));
                    builder.append(SPACE).append(manufactureReport.getTurn().getNumber());
                    builder.append(SPACE).append(lb.get(language, TURN));
                    builder.append(NEW_LINE);

                    U.sort(manufactureReport.getFields());
                    ReportFieldCategory category = null;
                    manufactureReport.getFields().sort(new Comparator<ReportField>() {
                        @Override
                        public int compare(ReportField o1, ReportField o2) {
                            return o1.getIndex() - o2.getIndex();
                        }
                    });
                    for (ReportField reportField : manufactureReport.getFields()){

                        if (!U.equals(reportField.getCategory(), category)){
                            category = reportField.getCategory();
                            if (category != null) {
                                builder.append(NEW_LINE);
                                builder.append(STAR).append(category.getTitle()).append(STAR).append(NEW_LINE);
                            }
                        }
                        if (U.exist(reportField.getTitle())){
                            builder.append(reportField.getTitle());
                        }

                        builder.append(HYPHEN).append(String.format(FORMAT, reportField.getValue()));
                        builder.append(SPACE).append(reportField.getUnit().getName());

                        if (U.exist(reportField.getComment())) {
                            builder.append(SEMICOLON).append(SPACE).append(reportField.getComment());
                        }
                        builder.append(NEW_LINE);
                    }

                    messages.put(language, builder.toString());
                }
                list.add(
                        sendMessage(setting.getTelegramId(), messages.get(language), null)
                );
            }
        }
    }

    public void send(long telegramId, String text) {
        sendMessage(telegramId, text, null);
    }

    public void showLoadTime(Transportation transportation) {
        HashMap<String, String> messages = new HashMap<>();
        DealType type = transportation.getType();
        String action = lb.get("_" + type.toString()).toLowerCase();

        Product product = transportation.getProduct();
        ProductProperty productProperty = dao.getProductProperty(product, type.toString());
        String productName = (productProperty != null ? productProperty.getValue() : product.getName()).toLowerCase();
        String driver = transportation.getDriver() != null ? transportation.getDriver().getPerson().getValue() : "--";

        for (UserBotSetting setting : getSettings()){
            if (setting.isShow() && setting.getWeight() == NotifyStatus.all){
                String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    messages.put(language, String.format("На %1s *%2s*", action, productName) + NEW_LINE +
                            String.format("став водій *%s*", driver) + NEW_LINE +
                                    String.format("Контрагент: *%s*", transportation.getCounterparty().getValue())
                    );
                }
                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }

    public void show(VROOil oil) {
        final int turn = oil.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(oil.getTurn().getTurn().getDate());
        HashMap<String, String> messages = new HashMap<>();

        for (UserBotSetting settings : getSettings()){
            if (settings.isShow() && settings.getAnalyses() == NotifyStatus.all){
                String language = settings.getLanguage();
                if (!messages.containsKey(language)){
                    String message = lb.get(language, "vro.oil.title");
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oil.turn"), turn, turnDate);
                    message += NEW_LINE + String.format(lb.get(language,  "bot.notificator.oil.acid"), oil.getAcid());
                    message += NEW_LINE + String.format(lb.get(language, "notification.kpo.peroxide"), oil.getPeroxide());
                    message += NEW_LINE + String.format(lb.get(language, "notificator.kpo.phosphorus"), oil.getPhosphorus());
                    message += NEW_LINE + String.format(lb.get(language, "bot.notificator.color"), oil.getColor());

                    messages.put(language, message);
                }
                sendMessage(settings.getTelegramId(), messages.get(language), null);
            }
        }
        messages.clear();
    }

    public void show(GranulesAnalyses analyses) {
        final String turnDate = DateUtil.prettyDate(Date.valueOf(analyses.getTurn().getTurn().getDate().toLocalDateTime().toLocalDate()));
        HashMap<String, String> messages = new HashMap<>();

        for (UserBotSetting setting : getSettings()){
            if (setting.isShow() && setting.getAnalyses() == NotifyStatus.all){
                String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = lb.get(language, "vro.granules.title");
                    message += NEW_LINE + String.format(lb.get(language, "vra.granules"), turnDate);
                    message += NEW_LINE + String.format(lb.get(language, "bot.notificator.granules.density"), analyses.getDensity());
                    message += NEW_LINE + String.format(lb.get(language, HUMIDITY), analyses.getHumidity());
                    message += NEW_LINE + String.format(lb.get(language, "notificator.granules.dust"), analyses.getDust());
                    message += NEW_LINE + (analyses.isMatch() ? lb.get(language, "match.dstu") : lb.get(language, "dsnt.match.dstu"));
                    messages.put(language, message);
                }
                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }

    public void show(SunProtein sunProtein) {
        final int turn = sunProtein.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(sunProtein.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();

        for (UserBotSetting setting : getSettings()){
            if (setting.isShow() && setting.isVro()){
                final String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    String message = lb.get(language, "vro.sun.protein.title") +
                        NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.turn"), turn, turnDate) +
                        NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.protein"), sunProtein.getProtein()) +
                        NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.humidity"), sunProtein.getHumidity()) +
                        NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.dry"), sunProtein.DryRecalculation());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }

    }

    public void show(TurnCellulose turnCellulose) {
        final int turn = turnCellulose.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(turnCellulose.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();

        for (UserBotSetting setting : getSettings()){
            if (setting.isShow() && setting.isVro()){
                final String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    String message = String.format(lb.get(language, "extraction.turn.protein.turn"), turn, turnDate) +
                        NEW_LINE + String.format(lb.get(language, "bot.notificator.raw.cellulose"), turnCellulose.getCellulose()) +
                        NEW_LINE + String.format(lb.get(language, "extraction.humidity"), turnCellulose.getHumidity()) +
                        NEW_LINE + String.format(lb.get(language, "extraction.turn.cellulose.dry"), turnCellulose.DryRecalculation());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language), null);
            }
        }
    }

    public void removeMessage(long chatId, int messageId) {
        try {
            telegramBot.remove(chatId, messageId);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
