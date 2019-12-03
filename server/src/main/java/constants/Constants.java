package constants;

/**
 * Created by quasilin on 11.03.2019.
 */
public interface Constants {
    public static final String ENCODING = "UTF-8";
    public static final String ID = "id";

    public static final String NAME = "name";
    public static final String KEY = "key";
    public static final String DATE = "date";
    public static final String DATE_TO = "dateTo";
    public static final String DEAL_ID = "dealId";
    String DEAL = "deal";
    String DEALS = "deals";
    public static final String PRODUCT_ID = "product_id";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    public static final String WORKER = "worker";
    public static final String UID = "uid";
    public static final String PASSWORD = "password";
    public static final String ORGANISATION = "organisation";
    public static final String TYPE = "type";
    public static final String PLAN = "plan";
    public static final String CUSTOMER = "customer";
    public static final String TRANSPORTATION_ID = "transportation_id";
    public static final String DRIVER_ID = "driver_id";
    public static final String VEHICLE_ID = "vehicle_id";
    public static final String WEIGHTS = "weights";
    public static final String COPY = "copy";
    public static final String HASH = "hash";
    public static final String CREATOR = "creator";
    public static final String UNIT = "unit";
    public static final String PRODUCT = "product";
    public static final String REALISATION = "realisation";
    public static final Object ADD = "add";
    public static final String TITLE = "title";
    public static final String SAVE = "save";
    public static final String MODAL_CONTENT = "modalContent";
    public static final String PROBE = "probe";
    public static final String FIND_MANGER = "findManager";
    public static final String FIND_ORGANISATION = "findOrganisation";
    String IS_NEW = "isNew";
    String EDIT_ADDRESS = "editAddress";
    String FIND_PRODUCT = "findProduct";
    String FIND_LOAD_ADDRESS = "findLoadAddress";
    String EDIT_PRODUCT = "editProduct";
    String PARSE_PRODUCT = "parseProduct";
    String FIND_DRIVER = "findDriver";
    String COUNTERPARTY = "counterparty";
    String ADDRESS = "address";
    String CITY = "city";
    String STREET = "street";
    String BUILD = "build";
    String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String CONTEXT = "context";
    public static final String TRANSPORTATION = "transportation";
    public static final String VEHICLE = "vehicle";
    public static final String DRIVER = "driver";
    public static final String WEIGHT = "weight";
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String COMMA = ",";
    public static final String SLASH = "/";
    public static final String TELEGRAM_ID = "telegramId";
    public static final String STORAGE = "storage";
    public static final String STORAGE_PRODUCTS = "storageProducts";
    public static final String BRUTTO = "brutto";
    public static final String TARA = "tara";
    public static final String WEIGHT_BRUTTO = WEIGHT + SLASH + BRUTTO;
    public static final String WEIGHT_TARA = WEIGHT + SLASH + TARA;
    public static final String DOCUMENT = "document";
    public static final String SHIPPER = "shipper";
    public static final String AMOUNT = "amount";
    public static final String TURN = "turn";
    public static final String ARCHIVE = "archive";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String NUMBER = "number";
    public static final String PRODUCTS = "products";
    public static final String DONE = "done";
    public static final String ANALYSES = "analyses";
    public static final String VALUE = "value";
    public static final String LICENSE = "license";
    public static final String TRUCK = "truck";
    public static final String MODEL = "model";
    public static final String TRAILER = "trailer";
    public static final String OWNER = "owner";
    public static final String TRANSPORTER = "transporter";
    public static final String STATUS = "status";
    public static final String NETTO = "netto";
    public static final String ANALYSES_TYPE = "analysesType";
    public static final String REGISTERED = "registered";
    public static final String TIME_IN = "timeIn";
    public static final String TIME_OUT = "timeOut";
    public static final String MANAGER = "manager";
    public static final String PERSON = "person";
    public static final String NOTE = "note";
    public static final String NOTES = "notes";
    public static final String SURNAME = "surname";
    public static final String FORENAME = "forename";
    public static final String PATRONYMIC = "patronymic";
    public static final String PHONES = "phones";
    public static final String EMPTY_JSON = "{}";
    public static final String TRANSPORTATIONS = TRANSPORTATION + "s";

    public static class Languages {
        public static final String CREATE_DOCUMENT = "document.create";
        public static final String EDIT_DOCUMENT = "document.edit";
        public static final String DATE_DOCUMENT = "document.date";
        public static final String DATE_TO_DOCUMENT = "document.date.to";
        public static final String ORGANISATION_DOCUMENT = "document.organisation";
        public static final String DEAL_PRODUCT = "document.product";
        public static final String DOCUMENT_QUANTITY = "document.quantity";
        public static final String DOCUMENT_PRICE = "document.price";
        public static final String DEAL_EDIT = "deal.edit";
        public static final String DEAL_CREATE = "deal.create";
        public static final String WRONG_PASSWORD = "wrong.password";
        public static final String NO_USER = "user.not.found";
        public static final String DEAL_COPY = "deal.copy";
    }

    public static final String EMAIL = "email";
    public static final String ROLE = "role";

    public class Sun {
        public static final String OILINESS = "oiliness";

        public static final String HUMIDITY_1 = "humidity1";
        public static final String HUMIDITY_2 = "humidity2";
        public static final String SORENESS = "soreness";
        public static final String OIL_IMPURITY = "oilImpurity";
        public static final String ACID_VALUE = "acidValue";
    }

    public class Oil {
        public static final String ORGANOLEPTIC = "organoleptic";
        public static final String HUMIDITY = "humidity";
        public static final String COLOR = "color";
        public static final String ACID_VALUE = "acidValue";
        public static final String PEROXIDE_VALUE = "peroxideValue";
        public static final String PHOSPHORUS = "phosphorus";
        public static final String SOAP = "soap";
        public static final String WAX = "wax";
    }

    public class Cake {
        public static final String PROTEIN = "protein";
        public static final String CELLULOSE = "cellulose";
    }
}
