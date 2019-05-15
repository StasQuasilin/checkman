package constants;

/**
 * Created by quasilin on 11.03.2019.
 */
public class Constants {
    public static final String ENCODE = "UTF-8";
    public static final String ID = "id";

    public static final String NAME = "name";
    public static final String KEY = "key";
    public static final String DATE = "date";
    public static final String DATE_TO = "dateTo";
    public static final String ORGANISATION_ID = "organisation_id";
    public static final String DEAL_ID = "dealId";
    public static final String PRODUCT_ID = "product_id";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    public static final String WORKER = "worker";
    public static final String UID = "uid";
    public static final String PASSWORD = "password";
    public static final String ORGANISATION = "organisation";
    public static final String TYPE = "type";
    public static final String VISIBILITY = "visibility";
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
    public static final String CONTRAGENT = "contragent";
    public static final String PRODUCT = "product";
    public static final String REALISATION = "realisation";
    public static final Object ADD = "add";


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

    public class Titles {
        public static final String DEAL_LIST = "title.deal.list";
        public static final String DEAL_SHOW = "title.deal.show";
        public static final String LOGISTIC_LIST = "title.logistic.list";
        public static final String TRANSPORT_LIST = "title.transport.list";
        public static final String TRANSPORT_SHOW = "title.transport.show";
        public static final String WEIGHT_LIST = "title.weight.list";
        public static final String WEIGHT_EDIT = "title.weight.edit";
        public static final String VEHICLE_DRIVER_INPUT = "title.vehicle.driver.input";
        public static final String VEHICLE_INPUT = "title.vehicle.edit";
        public static final String DRIVER_INPUT = "title.driver.edit";
        public static final String PROBE_LIST = "title.probe.list";
        public static final String SUBDIVISION_LIST = "title.subdivision.list";
        public static final String LABORATORY = "title.laboratory";
        public static final String SUN_EDIT = "title.sun.edit";
        public static final String OIL_EDIT = "title.oil.edit";
        public static final String CAKE_EDIT = "title.cake.edit";
        public static final String SUBDIVISION_LIST_EXTRACTION = SUBDIVISION_LIST + ".ex";
        public static final String SUBDIVISION_LIST_VRO = SUBDIVISION_LIST + ".vro";
        public static final String SUBDIVISION_LIST_KPO = SUBDIVISION_LIST + ".kpo";
        public static final String EXTRACTION_CRUDE = "title.extraction.crude.edit";
        public static final String EXTRACTION_RAW = "title.extraction.raw.edit";
        public static final String EXTRACTION_OIL = "title.extraction.oil.edit";
        public static final String DEAL_DELETE = "title.deal.delete";
        public static final String SUMMARY_LIST = "title.summary.list";
        public static final String SUMMARY_SHOW = "title.summary.show";
        public static final String PROBE_SUN_EDIT = "title.probe.sun";
        public static final String PROBE_OIL_EDIT = "title.probe.oil";
        public static final String PROBE_CAKE_EDIT = "title.probe.cake";
        public static final String SEAL_LIST = "title.seals";
        public static final String SEAL_EDIT = "title.seal.edit";
        public static final String DAILY_ANALYSES = "title.daily.analyses";
        public static final String TRANSPORT_ARCHIVE = "title.transport.archive";
        public static final String PERSONAL = "title.personal";
        public static final String ADMIN = "title.admin";
        public static final String WELCOME = "title.welcome";
        public static final String EXTRACTION_TURN_CRUDE = "title.extraction.turn.crude";
        public static final String EXTRACTION_TURN_PROTEIN = "title.extraction.turn.protein";
        public static final String EXTRACTION_STORAGE_PROTEIN = "title.extraction.storage.protein";

        public class Archive {
            public static final String DEAL = "title.archive";
        }
    }

    public class Vehicle {
        public static final String MODEL = "model";
        public static final String NUMBER = "number";
        public static final String TRAILER = "trailer";
        public static final String TRANSPORTER_ID = "transporter_id";
    }

    public class Person {
        public static final String SURNAME = "surname";
        public static final String FORENAME = "forename";
        public static final String PATRONYMIC = "patronymic";
        public static final String EMAIL = "email";
        public static final String ROLE = "role";
    }

    public class Weight {
        public static final String BRUTTO = "brutto";
        public static final String TARA = "tara";
    }

    public class Sun {
        public static final String OILINESS = "oiliness";
        public static final String HUMIDITY = "humidity";
        public static final String SORENESS = "soreness";
        public static final String OIL_IMPURITY = "oilImpurity";
        public static final String ACID_VALUE = "acidValue";
    }

    public class Oil {
        public static final String ORGANOLEPTIC = "organoleptic";
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
