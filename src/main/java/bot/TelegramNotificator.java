package bot;

import constants.Constants;
import entity.*;
import entity.bot.NotifyStatus;
import entity.bot.UserNotificationSetting;
import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.probes.SunProbe;
import entity.laboratory.storages.StorageAnalyses;
import entity.laboratory.subdivisions.extraction.*;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.laboratory.subdivisions.vro.*;
import entity.production.Turn;
import entity.products.Product;
import entity.products.ProductProperty;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import entity.transport.Transportation;
import entity.warehousing.StopReport;
import entity.weight.RoundReport;
import entity.weight.SubdivisionReport;
import entity.weight.Unit;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.DateUtil;
import utils.LanguageBase;
import utils.U;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by szpt_user045 on 17.04.2019.
 */
public class TelegramNotificator extends INotificator {

    private static final String TRANSPORT_IN = "bot.notificator.transport.in";
    private static final String NETTO = "bot.notificator.netto";
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

    public TelegramNotificator(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void transportShow(Transportation transportation){
        String message;
        for (UserNotificationSetting setting : getSettings()){
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
                    sendMessage(setting.getTelegramId(), message);
                }
            }
        }
    }

    public void transportRegistration(Transportation transportation){
        transportRegistration(transportation, lb.get(TRANSPORT_REGISTRATION));
    }
    //DONE
    public void transportInto(Transportation transportation){
        transportRegistration(transportation, lb.get(TRANSPORT_INTO));
    }
    //DONE
    public void transportRegistration(Transportation transportation, String messageFormat) {
        DealType type = transportation.getType();
        String action = lb.get("_" + type.toString()).toLowerCase();

        Product product = transportation.getDeal().getProduct();
        ProductProperty productProperty = dao.getProductProperty(product, type.toString());
        String productName = (productProperty != null ? productProperty.getValue() : product.getName()).toLowerCase();
        String driver = transportation.getDriver() != null ? transportation.getDriver().getPerson().getValue() : "--";

        String organisation = transportation.getDeal().getOrganisation().getValue();
        String message = String.format(messageFormat, action, productName, driver, organisation);
        getSettings().stream().filter(setting -> setting.isShow() && setting.getTransport() != NotifyStatus.off).forEach(setting -> {
            sendMessage(setting.getTelegramId(), message);
        });
    }
    //DONE
    public void weightShow(Transportation transportation) {

        DealType type = transportation.getType();
        String action = lb.get("_" + type + ".do");
        String driver = "--";
        if (transportation.getDriver() !=null){
            driver = transportation.getDriver().getPerson().getValue();
        }
        String organisation = transportation.getDeal().getOrganisation().getValue();
        String product = transportation.getDeal().getProduct().getName();
        final float net = transportation.getWeight().getNetto();
        String message = String.format(lb.get(TRANSPORTATION_WEIGHT), action, driver, organisation, product, net);
        for (UserNotificationSetting setting : getSettings()){
            if (setting.isShow() && setting.getWeight() == NotifyStatus.all){
                sendMessage(setting.getTelegramId(), message);
            }
        }
    }

    public void sunAnalysesShow(Transportation transportation, SunAnalyses analyses, float correction) {
        HashMap<String, String> messages = new HashMap<>();
        for (UserNotificationSetting setting : getSettings()){
            if (setting.isShow()) {
                boolean show = setting.getAnalyses() == NotifyStatus.all;
                if (show) {
                    String language = setting.getLanguage();
                    if (!messages.containsKey(language)){
                        messages.put(language, getMessage(transportation, analyses, correction, language));
                    }
                    sendMessage(setting.getTelegramId(), messages.get(language));
                }
            }
        }
    }

    public void sunProbe(SunProbe probe) {
        HashMap<String, String> messages = new HashMap<>();
        for (UserNotificationSetting setting : getSettings()){
            if (setting.isShow()) {
                boolean show = setting.getAnalyses() == NotifyStatus.all;
                if (show) {
                    String language = setting.getLanguage();
                    if (!messages.containsKey(language)){
                        messages.put(language, getMessage(probe, language));
                    }
                    sendMessage(setting.getTelegramId(), messages.get(language));
                }
            }
        }
    }

    String getMessage(SunProbe probe, String language){

        return STAR + lb.get(language, "telegram.sun.probe") + STAR + NEW_LINE + NEW_LINE +
                lb.get(language, "deal.organisation") + COLON + SPACE + probe.getOrganisation() + NEW_LINE +
                lb.get(language, "deal.manager") + COLON + SPACE + probe.getManager() + NEW_LINE +
                (probe.getAnalyses().isContamination() ? ATTENTION + lb.get(language, "sun.contamination") : "") +
                String.format(lb.get(language, "bot.notificator.humidity"), probe.getAnalyses().getHumidity1()) + NEW_LINE +
                String.format(lb.get(language, "bot.notificator.soreness"), probe.getAnalyses().getSoreness()) + NEW_LINE +
                String.format(lb.get(language, "bot.notificator.oiliness"), probe.getAnalyses().getOiliness()) + NEW_LINE +
                String.format(lb.get(language, "bot.notificator.oil.impurity"), probe.getAnalyses().getOilImpurity()) + NEW_LINE +
                String.format(lb.get(language, "bot.notificator.oil.acid"), probe.getAnalyses().getAcidValue()) + NEW_LINE;


    }

    public static final String ATTENTION = "";//"‼";
    public void cakeAnalysesShow(Transportation transportation, MealAnalyses analyses) {
        HashMap<String, String> messages = new HashMap<>();
        for (UserNotificationSetting setting : getSettings()){
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
                    sendMessage(setting.getTelegramId(), messages.get(language));
                }
            }
        }

        messages.clear();
    }
    public void oilAnalysesShow(Transportation transportation, OilAnalyses analyses) {

        HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting setting : getSettings()){
            if (setting.isShow()) {
                boolean show = setting.getAnalyses() == NotifyStatus.all;
                if (show) {
                    String language = setting.getLanguage();
                    if (!messages.containsKey(language)){

                        messages.put(language, getMessage(transportation, analyses, language));
                    }

                    sendMessage(setting.getTelegramId(), messages.get(language));
                }
            }
        }

        messages.clear();
    }

    public void extractionShow(ExtractionCrude crude){

        final HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting setting : getSettings()) {
            if (setting.isExtraction() && setting.isShow()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    messages.put(language, getMessage(crude, language));
                }

                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }

        messages.clear();
    }

    public void vroShow(VROCrude crude, List<ForpressCake> cakes) {
        Turn turn = crude.getTurn().getTurn();
        final int turnNumber = turn.getNumber();
        String date = DateUtil.prettyDate(Date.valueOf(turn.getDate().toLocalDateTime().toLocalDate()));
        String time = crude.getTime().toLocalDateTime().toLocalTime().toString();
        final HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting setting : getSettings()) {
            if(setting.isVro() && setting.isShow()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    StringBuilder message = new StringBuilder(lb.get(language, "notification.vro.title"));
                    message.append(NEW_LINE).append(String.format(lb.get(language, "extraction.turn"), turnNumber, date, time));
                    if (crude.getDryOiliness() > 0) {
                        message.append(NEW_LINE).append(String.format(lb.get(language, "notification.dryOiliness"), crude.getDryOiliness()));
                    }
                    message.append(NEW_LINE).append(NEW_LINE).append(lb.get(language, "notification.vro.before"));
                    message.append(NEW_LINE).append(String.format(lb.get(language, "notification.vro.humidity"), crude.getHumidityBefore()));
                    message.append(NEW_LINE).append(String.format(lb.get(language, "notification.vro.soreness"), crude.getSorenessBefore()));
                    message.append(NEW_LINE).append(NEW_LINE).append(lb.get(language, "notification.vro.after"));
                    message.append(NEW_LINE).append(String.format(lb.get(language, "notification.vro.humidity"), crude.getHumidityAfter()));
                    message.append(NEW_LINE).append(String.format(lb.get(language, "notification.vro.soreness"), crude.getSorenessAfter()));
                    message.append(NEW_LINE).append(NEW_LINE).append(String.format(lb.get(language, "notification.vro.kernel.offset.2"), crude.getKernelOffset()));
                    message.append(NEW_LINE).append(String.format(lb.get(language, "notification.vro.huskiness.2"), crude.getHuskiness()));
                    message.append(NEW_LINE).append(String.format(lb.get(language, "notification.vro.pulp.1"), crude.getPulpHumidity1()));
                    message.append(NEW_LINE).append(String.format(lb.get(language, "notification.vro.pulp.2"), crude.getPulpHumidity2()));

                    message.append(NEW_LINE);
                    if (cakes.size() > 0) {
                        message.append(NEW_LINE).append(lb.get(language, "notification.forpress.cake.title"));
                        for (ForpressCake cake : cakes) {
                            message.append("\n*").append(cake.getForpress().getName()).append("*");
                            message.append("\n\t        ").append(String.format(lb.get(language, "bot.notificator.humidity"), cake.getHumidity()));
                            message.append("\n\t        ").append(String.format(lb.get(language, "bot.notificator.oiliness"), cake.getOiliness()));
                        }
                    }
                    messages.put(language, message.toString());
                }
                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }


    private Collection<UserNotificationSetting> getSettings() {
        return telegramBot.getBotSettings().get();
    }
    private Message sendMessage(long telegramId, String message) {
        telegramBot.sendMessage(telegramId, message);
        return null;
    }
    public void extractionShow(StorageProtein storageProtein) {
        String storage = storageProtein.getStorage().getName();
        String time = storageProtein.getTime().toLocalDateTime().toLocalTime().toString();
        final HashMap<String, String> messages = new HashMap<>();
        for (UserNotificationSetting setting : getSettings()) {
            if (setting.isShow() && setting.isExtraction()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = String.format(lb.get(language, "extraction.storage.protein.title"), storage, time);
                    message += NEW_LINE + String.format(lb.get(language, "extraction.storage.protein"), storageProtein.getProtein());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.storage.humidity"), storageProtein.getHumidity());
                    message += NEW_LINE + String.format(lb.get(language, DRY_PROTEIN), storageProtein.DryRecalculation());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }
    public void extractionShow(ExtractionOIl oil) {
        final int turn = oil.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(oil.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();
        for (UserNotificationSetting setting : getSettings()) {
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


                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }
    public void extractionShow(TurnProtein turnProtein) {
        final int turn = turnProtein.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(turnProtein.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting setting : getSettings()){
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

                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }

    }
    public void extractionShow(TurnGrease turnGrease) {
        final int turn = turnGrease.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(turnGrease.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();
        for (UserNotificationSetting setting : getSettings()){
            if (setting.isShow() && setting.isExtraction()){
                final String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = lb.get(language, "extraction.turn.grease.title");
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.turn"), turn, turnDate);
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.grease.grease"), turnGrease.getGrease());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.protein.humidity"), turnGrease.getHumidity());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }
    public void extractionShow(StorageGrease storageGrease) {
        String storage = storageGrease.getStorage().getName();
        String time = storageGrease.getTime().toLocalDateTime().toLocalTime().toString();
        final HashMap<String, String> messages = new HashMap<>();
        for (UserNotificationSetting setting : getSettings()) {
            if (setting.isShow() && setting.isExtraction()) {
                String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = String.format(lb.get(language, "extraction.storage.grease.title"),
                            storage, time);
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn.grease.grease"), storageGrease.getGrease());
                    message += NEW_LINE + String.format(lb.get(language, "extraction.storage.humidity"), storageGrease.getHumidity());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }
    public void kpoShow(KPOPart part) {
        final HashMap<String, String> messages = new HashMap<>();
        for (UserNotificationSetting setting : getSettings()) {
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
                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }

    public void storagesShow(StorageAnalyses analyses) {
        HashMap<String, String> messages = new HashMap<>();
        for (UserNotificationSetting setting : getSettings()){
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

                sendMessage(setting.getTelegramId(), messages.get(language));
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
            for (UserNotificationSetting setting : getSettings()) {
                if (setting.getWorker().getId() == admin.getWorker().getId() && setting.getTelegramId() > 0){
                    String language = setting.getLanguage();
                    String message = lb.get(language, "bot.user.registration.title");
                    message += NEW_LINE + lb.get(language, "bot.user.registration.text");
                    message += NEW_LINE + String.format(lb.get(language, "bot.user.registration.user"), user.getWorker().getValue());
                    message += NEW_LINE + String.format(lb.get(language, "bot.user.registration.role"), lb.get(language, user.getRole().toString()));

                    buttons.add(new InlineKeyboardButton().setText("Yes").setCallbackData(String.valueOf(user.getId())));
                    buttons.add(new InlineKeyboardButton().setText("No").setCallbackData(String.valueOf(user.getId())));
                    sendMessage(setting.getTelegramId(), message);
                    break;
                }
            }
        }
    }

    public static final String HYPHEN = " - ";
    public static final String SPACE = " ";
    public static final String SEMICOLON = "; ";
    public static final String COLON = ": ";
    public static final String NEW_LINE = "\n";
    public static final String UNDERCORE = "_";
    public static final String FORMAT = "%1$,.3f";
    public static final String STAR = "*";

    public void manufactureReportShow(ManufactureReport manufactureReport, ArrayList<Message> list) {

        HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting setting : getSettings()){
            if (setting.isShow() && setting.isManufactureReports()){
                String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    StringBuilder builder = new StringBuilder();

                    builder.append(DateUtil.prettyDate(manufactureReport.getTurn().getDate()));
                    builder.append(SPACE).append(manufactureReport.getTurn().getNumber());
                    builder.append(SPACE).append(lb.get(language, TURN));
                    builder.append(NEW_LINE);

                    U.sort(manufactureReport.getFields());
                    ReportFieldCategory category = null;
                    manufactureReport.getFields().sort((o1, o2) -> o1.getIndex() - o2.getIndex());
                    for (ReportField reportField : manufactureReport.getFields()){
                        if (!U.equals(reportField.getCategory(), category)) {
                            category = reportField.getCategory();
                            if (category != null && U.exist(category.getTitle())) {
                                builder.append(NEW_LINE);
                                builder.append(STAR).append(category.getTitle()).append(STAR);
                                if (category.isSummary()){
                                    ArrayList<ReportField> categoryFields = getCategoryFields(category.getId(), manufactureReport.getFields());
                                    builder.append(COLON);
                                    for (Unit unit : getUnits(categoryFields)){
                                        builder.append(String.format(FORMAT, getSummary(unit, categoryFields)));
                                        builder.append(SPACE).append(unit.getName()).append(SPACE);
                                    }
                                }
                                builder.append(NEW_LINE);
                            }
                        }
                        builder.append(reportField.getTitle());
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
                        sendMessage(setting.getTelegramId(), messages.get(language))
                );
            }
        }
    }

    private float getSummary(Unit unit, ArrayList<ReportField> categoryFields) {
        float sum = 0;
        for (ReportField rf : categoryFields){
            if (rf.getUnit().equals(unit)){
                sum += rf.getValue();
            }
        }
        return sum;
    }

    private ArrayList<Unit> getUnits(ArrayList<ReportField> categoryFields) {
        ArrayList<Unit> units = new ArrayList<>();
        for (ReportField rf : categoryFields){
            if (!units.contains(rf.getUnit())){
                units.add(rf.getUnit());
            }
        }
        return units;
    }

    private ArrayList<ReportField> getCategoryFields(int id, List<ReportField> fields) {
        ArrayList<ReportField> categoryFields = new ArrayList<>();
        for (ReportField rf : fields){
            ReportFieldCategory category = rf.getCategory();
            if (category != null && category.getId() == id){
                categoryFields.add(rf);
            }
        }
        return categoryFields;
    }

    public void send(long telegramId, String text) {
        sendMessage(telegramId, text);
    }

    public void showLoadTime(Transportation transportation) {
        HashMap<String, String> messages = new HashMap<>();
        DealType type = transportation.getType();
        String action = lb.get("_" + type.toString()).toLowerCase();

        Product product = transportation.getProduct();
        ProductProperty productProperty = dao.getProductProperty(product, type.toString());
        String productName = (productProperty != null ? productProperty.getValue() : product.getName()).toLowerCase();
        String driver = transportation.getDriver() != null ? transportation.getDriver().getPerson().getValue() : "--";

        for (UserNotificationSetting setting : getSettings()){
            if (setting.isShow() && setting.getWeight() == NotifyStatus.all){
                String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    messages.put(language, String.format("На %1s *%2s*", action, productName) + NEW_LINE +
                            String.format("став водій *%s*", driver) + NEW_LINE +
                                    String.format("Контрагент: *%s*", transportation.getCounterparty().getValue())
                    );
                }
                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }

    public void show(VROOil oil) {
        final int turn = oil.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(oil.getTurn().getTurn().getDate());
        HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting settings : getSettings()){
            if (settings.isShow() && settings.getAnalyses() == NotifyStatus.all){
                String language = settings.getLanguage();
                if (!messages.containsKey(language)){
                    String message = lb.get(language, "vro.oil.title");
                    message += NEW_LINE + String.format(lb.get(language, "extraction.oil.turn"), turn, turnDate);
                    message += NEW_LINE + String.format(lb.get(language,  "bot.notificator.oil.acid"), oil.getAcid());
                    message += NEW_LINE + String.format(lb.get(language, "notification.kpo.peroxide"), oil.getPeroxide());
                    message += NEW_LINE + String.format(lb.get(language, "notificator.kpo.phosphorus"), oil.getPhosphorus());
                    message += NEW_LINE + String.format(lb.get(language, "bot.notificator.color"), oil.getColor());
                    message += NEW_LINE + String.format(lb.get(language, "bot.extraction.oil.humidity"), oil.getHumidity());

                    messages.put(language, message);
                }
                sendMessage(settings.getTelegramId(), messages.get(language));
            }
        }
        messages.clear();
    }

    public void show(GranulesAnalyses analyses) {
        final String turnDate = DateUtil.prettyDate(Date.valueOf(analyses.getTurn().getTurn().getDate().toLocalDateTime().toLocalDate()));
        HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting setting : getSettings()){
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
                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }

    public void show(MealGranules analyses) {
        int turnNumber = analyses.getTurn().getTurn().getNumber();
        String turnTime = analyses.getCreateTime().getTime().toLocalDateTime().toLocalTime().toString().substring(0, 5);
        final String turnDate = DateUtil.prettyDate(Date.valueOf(analyses.getTurn().getTurn().getDate().toLocalDateTime().toLocalDate()));
        HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting setting : getSettings()){
            if (setting.isShow() && setting.getAnalyses() == NotifyStatus.all){
                String language = setting.getLanguage();
                if (!messages.containsKey(language)) {
                    String message = lb.get(language, "extraction.granules.title");
                    message += NEW_LINE + String.format(lb.get(language, "extraction.turn"), turnNumber, turnDate, turnTime);
                    message += NEW_LINE + String.format(lb.get(language, "notificator.granules.scree"), analyses.getScree());
                    message += NEW_LINE + String.format(lb.get(language, "bot.notificator.granules.density"), analyses.getDensity());
                    message += NEW_LINE + String.format(lb.get(language, HUMIDITY), analyses.getHumidity());
                    message += NEW_LINE + String.format(lb.get(language, "bot.notificator.granules.length"), analyses.getLength());
                    message += NEW_LINE + String.format(lb.get(language, "bot.notificator.granules.diameter"), analyses.getDiameter());
                    messages.put(language, message);
                }
                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }

    public void show(SunProtein sunProtein) {
        final int turn = sunProtein.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(sunProtein.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting setting : getSettings()){
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

                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }

    }

    public void show(TurnCellulose turnCellulose) {
        final int turn = turnCellulose.getTurn().getTurn().getNumber();
        final String turnDate = DateUtil.prettyDate(turnCellulose.getTurn().getTurn().getDate());
        final HashMap<String, String> messages = new HashMap<>();

        for (UserNotificationSetting setting : getSettings()){
            if (setting.isShow() && setting.isVro()){
                final String language = setting.getLanguage();
                if (!messages.containsKey(language)){
                    String message = String.format(lb.get(language, "extraction.turn.protein.turn"), turn, turnDate) +
                        NEW_LINE + String.format(lb.get(language, "bot.notificator.raw.cellulose"), turnCellulose.getCellulose()) +
                        NEW_LINE + String.format(lb.get(language, "extraction.humidity"), turnCellulose.getHumidity()) +
                        NEW_LINE + String.format(lb.get(language, "extraction.turn.cellulose.dry"), turnCellulose.DryRecalculation());
                    messages.put(language, message);
                }

                sendMessage(setting.getTelegramId(), messages.get(language));
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

    public void alarm(StopReport report) {
        HashMap<String, String> messages = new HashMap<>();
        for(UserNotificationSetting setting : getSettings()){
            if(setting.isShow()){
                String language = setting.getLanguage();
                if(!messages.containsKey(language)){
                    String message = String.format(lb.get(language, "subdivision.stop"), report.getSubdivision().getName()) + NEW_LINE+
                            String.format(lb.get(language, "reason.stop"), report.getReason()) +NEW_LINE+
                            lb.get(language, "stop.delay") + COLON +
                            getDays(language, report.getDays()) + NEW_LINE +
                            getHours(language, report.getHours()) + SPACE +
                            String.format(lb.get(language, "notificator.minutes"), report.getMinutes());

                    messages.put(language, message);
                }
                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
        messages.clear();
    }

    String getDays(String language, int days){
        if (days > 0){
            if (days == 1){
                return lb.get(language, "1.day");
            } else if (days < 5){
                return String.format(lb.get(language, "2.days"), days);
            } else {
                return String.format(lb.get(language, "5.days"), days);
            }
        } else {
            return EMPTY;
        }
    }

    String getHours(String language, int hours){
        if (hours > 0) {
            if (hours == 1) {
                return lb.get(language, "1.hour");
            } else if (hours < 5) {
                return String.format(lb.get(language, "2.hours"), hours);
            } else {
                return String.format(lb.get(language, "5.hours"), hours);
            }
        } else {
            return EMPTY;
        }

    }

    static final String ALERT = "‼️";
    static String prettyLocalDateTime(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public void sendReport(RoundReport report) {

        HashMap<String, String> messages = new HashMap<>();
        for(UserNotificationSetting setting : getSettings()){
            if (setting.isShow() && setting.isRoundReport()){
                String language = setting.getLanguage();
                if(!messages.containsKey(language)){
                    StringBuilder builder = new StringBuilder();

                    builder.append(String.format(lb.get(language, "telegram.report.head"),
                            prettyLocalDateTime(report.getTimestamp().toLocalDateTime())));
                    builder.append(NEW_LINE);
                    for (SubdivisionReport r : report.sortedReports()){

                        builder.append(STAR).append(r.getSubdivision().getName()).append(STAR)
                                .append(SPACE).append(COLON);
                        if (r.isServiceability()){
                            builder.append(lb.get(language, "serviceability"));
                        } else {
                            builder.append(ALERT).append(lb.get(language, "not.serviceability"));
                        }
                        builder.append(NEW_LINE);
                        if (r.getSubdivision().isTehControl()){
                            if (r.isAdherence()){
                                builder.append(lb.get(language, "adherence"));
                            } else {
                                builder.append(ALERT).append(lb.get(language, "no.adherence"));
                            }
                            builder.append(NEW_LINE);
                        }
                        if (!r.isServiceability() || !r.isAdherence()){
                            builder.append(UNDERCORE).append(r.getNote()).append(UNDERCORE).append(NEW_LINE);
                        }
                    }
                    messages.put(language, builder.toString());
                }
                sendMessage(setting.getTelegramId(), messages.get(language));
            }
        }
    }
}
