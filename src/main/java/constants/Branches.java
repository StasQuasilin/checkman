package constants;

import api.transport.TransportDirection;
import controllers.archive.ArchiveType;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class Branches {
    public static final String PING = "/ping";
    public static final String QR_GENERATOR = "/qr";

    public static class UI{

        public static final String SING_IN = "/login";
        public static final String APPLICATION = "/app";
        public static final String DEAL_LIST = "/deals/list.j";
        public static final String DEAL_BUY = DEAL_LIST + "?type=buy";
        public static final String DEAL_SELL = DEAL_LIST + "?type=sell";
        public static final String DEAL_SHOW = "/deal/show.j";
        public static final String DEAL_EDIT = "/deal/edit.j";
        public static final String LOAD_PLAN = "/deals/load/plan.j";
        public static final String LOGISTIC_LIST = "/logistic/list.j";
        public static final String TRANSPORT_LIST = "/transport/list.j";
        public static final String TRANSPORT_SHOW = "/transport/show.j";
        public static final String TRANSPORT_EDIT = "/transport/edit.j";
        public static final String WEIGHT_LIST = "/weight/list.j";
        public static final String WEIGHT_EDIT = "/weight/edit.j";
        public static final String WEIGHT_ADD = "/weight/add.j";

        public static final String HOME = APPLICATION;
        public static final String EDIT_VEHICLE = "/vehicle/modal.j";
        public static final String EDIT_DRIVER = "/driver/modal.j";
        public static final String VEHICLE_DRIVER_MODAL = "/vehicle/driver/modal.j";
        public static final String PROBE_LIST = "/laboratory/probe.j";
        public static final String SUBDIVISION_LIST = "/laboratory/turn.j";
        public static final String LABORATORY = "/laboratory.j";
        public static final String LABORATORY_BUY = LABORATORY + "?type=buy";
        public static final String LABORATORY_SELL = LABORATORY + "?type=sell";
        public static final String REFERENCES = "/references.j";
        public static final String ADMIN = "/admin.j";
        public static final String PROBE_EDIT = "/laboratory/probe/edit.j";
        public static final String PROBE_SHOW = "/laboratory/probe/show.j";
        public static final String LABORATORY_EDIT = "/laboratory/show.j";
        public static final String DEAL_DELETE = "/deal/delete.j";
        public static final String SUMMARY_LIST = "/summary/list.j";
        public static final String SUMMARY_SHOW = "/summary/show.j";
        public static final String SEAL_LIST = "/seals/list.j";
        public static final String SEAL_CREATE = "/seals/edit.j";
        public static final String DEAL_ARCHIVE = "/archive/deal.j";
        public static final String BUY_ARCHIVE = DEAL_ARCHIVE + "?type=buy";
        public static final String SELL_ARCHIVE = DEAL_ARCHIVE + "?type=sell";
        public static final String TRANSPORT_ARCHIVE = "/archive/transport.j";
        public static final String SUMMARY_ARCHIVE = TRANSPORT_ARCHIVE + "?type=" + ArchiveType.summary.toString();
        public static final String LOGISTIC_ARCHIVE = TRANSPORT_ARCHIVE + "?type=" + ArchiveType.logistic.toString();
        public static final String WEIGHT_ARCHIVE = TRANSPORT_ARCHIVE + "?type=" + ArchiveType.weight.toString();
        public static final String LABORATORY_BUY_ARCHIVE = TRANSPORT_ARCHIVE + "?type=" + ArchiveType.laboratory_buy.toString();
        public static final String LABORATORY_SELL_ARCHIVE = TRANSPORT_ARCHIVE + "?type=" + ArchiveType.laboratory_sell.toString();
        public static final String ARCHIVE_SHOW = "/archive/show/";
        public static final String ARCHIVE_SHOW_SUMMARY = ARCHIVE_SHOW + "/" + ArchiveType.summary.toString() + ".j";
        public static final String PERSONAL = "/personal.j";
        public static final String WELCOME = "/welcome.j";
        public static final String WAYBILL_PRINT = "/weight/print.j";
        public static final String WEIGHT_CANCEL = "/weight/cancel.j";
        public static final String RAIL_LIST = "/rail/list.j";
        public static final String RAIL_ARCHIVE = "/rail/archive.j";
        public static final String RAIL_EDIT = "/rail/edit.j";
        public static final String LABORATORY_TURNS = "/laboratory/turns.j";
        public static final String LABORATORY_TURNS_EDIT = "/laboratory/turns/edit.j";
        public static final String LABORATORY_PRINT = "/laboratory/crude/monthly/print.j";
        public static final String LABORATORY_STORAGES = "/laboratory/storages.j";
        public static final String LABORATORY_STORAGE_EDIT = "/laboratory/storages/edit.j";
        public static final String FORGOT = "/forgot";
        public static final String FEEDBACK = "/feedback";
        public static final String DEAL_SHOW_NEW = DEAL_SHOW + "_v2";
        public static final String NOTES_LIST = "/transportation/notes";
        public static final String CHANGE_PASSWORD = "/personal/change/password.j";
        public static final String EXTRACTION_CRUDE_REMOVE = "/laboratory/crude/remove.j";
        public static final String LABORATORY_PRINT_OPTIONS = "/laboratory/print/optins.j";
        public static final String USER_LIST = "/admin/user/list.j";
        public static final String MANUFACTURE_REPORT = "/manufacture/reports.j";
        public static final String MANUFACTURE_REPORT_EDIT = "/manufacture/report/edit.j";
        public static final String ARCHIVE_WEIGHT_SHOW = "/archive/weight/show.j";
        public static final String STORAGE_LIST = "/storages.j";
        public static final String WAREHOUSING_LIST = "/warehousing.j";
        public static final String WAREHOUSING_ARCHIVE = TRANSPORT_ARCHIVE + "?type=" + ArchiveType.warehousing.toString();
        public static final String WAREHOUSING_EDIT = "/warehousing/edit.j";
        public static final String STORAGE_EDIT = "/storage/edit.j";
        public static final String REPORTS = "/reports.j";
        public static final String USER_REGISTRATION = "/admin/registration.j";
        public static final String ORGANISATION_COLLAPSE = "/collapse/organisations.j";
        public static final String FORMATTING_TEST = "/test/formatting.j";
        public static final String SUMMARY_PLAN_PRINT = "/summary/print.j";
        public static final String PRINT_ON_TERRITORY = "/transport/on/territory/print.j";
        public static final String TRANSPORT_INCOME = "/transport/income/print.j";
        public static final String STORAGE_PRODUCT_REPLACE = "/storage/stock/replace.j";
        public static final String MANUFACTURE_REPORT_PREVIEW = "/manufacture/report/view.j";
        public static final String DRIVER_COLLAPSE = "/drivers/collapse.j";
        public static final String TRANSPORT_CARRIAGES = "/transport/carriages.j";
        public static final String CONTRACT_LIST = "/contracts.j";
        public static final String RETAIL_EDIT = "/retail/edit.j";
        public static final String ADDRESS_EDIT = "/address/edit.j";
        public static final String EDIT_PRODUCT = "/edit/product.j";
        public static final String CONTRACT_EDIT ="/contract.edit.j" ;
        public static final String RETAIL_LIST = "/retail/list.j";
        public static final String MANUFACTURE_ANALYSES = "/manufacture/analyses.j";
        public static final String STOP_LIST = "/stop/list.j";
        public static final String STOP_REPORT = "/stop/report.j";
        public static final String RETAIL_PRINT = "/retail/print.j";
        public static final String STORAGE_PRODUCT_CORRECTION = "/storage/product/correction.j";
        public static final String PROTOCOL_LIST = "/statestandards.j";
        public static final String PROTOCOL_EDIT = "/statestandard/edit.j";
        public static final String LABORATORY_CARRIAGES = "/laboratory/carriages.j";
        public static final String TRUCK_INFO = "/truck/info.j";
        public static final String LABORATORY_SHOW = "/laboratory/hsow.j";
        public static final String CHECK = "/check.j";
        public static final String RETAIL_REMOVE = "/retail/remove.j";
        public static final String MEAL_GRANULES = "/extraction/meal/granules.j";
        public static final String TRANSPORT_PER_MONTH = "/transport/per/month.j";
        public static final String SEAL_FIND = "/seal/find.j";
        public static final String SEAL_OFF = "/seal/off.j";
        public static final String SEAL_SHOW = "/seal/show.j";
        public static final String UNLOAD_STATISTIC_LIST = "/statistic/unloads.j";
        public static final String TRANSPORT_ADD_FAST = "/transport/add/fast.j";
        public static final String BOARD = "/board.j";
        public static final String BOARD_EDIT = "/board/edit.j";
        public static final String EDIT_REPORT = "/report/edit.j";
        public static final String ROUND_REPORTS = "/round/reports/list.j";
        public static final String CUSTOM_TIME = "/transportation/custom-time.j";
        public static final String USER_PAGE = "/user/page.j";
        public static final String TRANSPORT_COST = "/transport/cost.j";
        public static final String SEALS_REMOVE = "/seals/remove.j";
        public static final String SELECT_COUNTERPARTY = "/transportation/counterparty/select.j";
        public static final String TRAILER_EDIT = "/trailer/edit.j";
        public static final String CHANGE_VIEW = "/change/view.j";
        public static final String NOTE_EDIT = "/transportation/note/edit.j";


        public static class Extraction {
            public static final String CRUDE_EDIT = "/laboratory/extraction/crude.j";
            public static final String RAW_EDIT = "/laboratory/extraction/raw.j";
            public static final String OIL_EDIT = "/laboratory/extraction/oil.j";
            public static final String TURN_CRUDE_EDIT = "/laboratory/extraction/turn/crude.j";
            public static final String TURN_PROTEIN = "/laboratory/extraction/turn/protein.j";
            public static final String STORAGE_PROTEIN = "/laboratory/extraction/storage/protein.j";
            public static final String TURN_GREASE = "/laboratory/extraction/turn/grease.j";
            public static final String STORAGE_GREASE = "/laboratory/extraction/storage/grease.j";
            public static final String DAILY_REPORT_PRINT = "/laboratory/extraction/daily/print.j";
            public static final String TURN_CELLULOSE = "/cellulose.j";
        }

        public static class VRO {
            public static final String CRUDE_EDIT = "/laboratory/vro/crude.j";
            public static final String OIL_EDIT = "/laboratory/vro/oil.j";
            public static final String DAILY_EDIT = "/laboratory/vro/daily.j";
            public static final String OIL_MASS_FRACTION = "/laboratory/vro/oil/mass/fraction.j";
            public static final String OIL_MASS_FRACTION_DRY = "/laboratory/vro/oil/mass/fraction/dry.j";
            public static final String DAILY_REPORT_PRINT = "/laboratory/vro/daily/report/print.j";
            public static final String GRANULES = "/laboratory/vro/daily/granules/edit.j";
            public static final String SUN_PROTEIN = "/laboratory/vro/sun/protein.j";
        }

        public static class KPO {
            public static final String PART_EDIT = "/laboratory/kpo/edit.j";
        }

        public static class References {
            public static final String DRIVER_EDIT = "/driver/edit.j";
            public static final String ORGANISATION_EDIT = "/organisation/edit.j";
            public static final String PRODUCT_EDIT = "/product/edit.j";
            public static final String GROUP_PRODUCT_EDIT = "/product/group/edit";
            public static final String FIND_CONTRACTS = "/find/contracts.j";
        }

        public static class Transportation {
            public static final String DEAL_EDIT = "/transportation/deal/edit.j";
        }
    }
    public static class API{
        public static final String API = "/api/v1";
        public static final String DEAL_SAVE =          API + "/deal/save";
        public static final String DEAL_DELETE =        API + "/deal/delete";
        public static final String SIGN_IN =            "/a/sign/in";
        public static final String SIGN_UP =            API + "/sign/up";
        public static final String DEAL_LIST =          API + "/deal/list";
        public static final String DEAL_LIST_BUY =      DEAL_LIST + "?type=buy";
        public static final String DEAL_LIST_SELL =     DEAL_LIST + "?type=sell";
        public static final String PLAN_LIST =          API + "/load/plan/list";
        public static final String PLAN_LIST_SAVE =     API + "/load/plan/save";
        public static final String LOGISTIC_LIST =      API + ".logistic.list";
        public static final String LOGISTIC_SAVE =      API + "/logistic/save";
        public static final String TRANSPORT_LIST =   API + "/transport/list";
        public static final String TRANSPORT_TIME =     API + "/transport/time";
        public static final String TRANSPORT_TIME_IN =  TRANSPORT_TIME + "?dir=in";
        public static final String TRANSPORT_TIME_OUT = TRANSPORT_TIME + "?dir=out";
        public static final String WEIGHT_LIST =        API + "/weight/list";
        public static final String SAVE_TRANSPORTATION_VEHICLE = API + "/transportation/save/vehicle";
        public static final String SAVE_TRANSPORTATION_DRIVER = API + "/transportation/save/driver";
        public static final String SAVE_WEIGHT =        API + "/weight/save";
        public static final String PROBE_LIST =         API + "/laboratory/probe/list";
        public static final String LABORATORY_LIST =    API + "/laboratory/list";
        public static final String LABORATORY_SAVE_SUN = API + "/laboratory/save/sun";
        public static final String LABORATORY_SAVE_OIL = API + "/laboratory/save/oil";
        public static final String LABORATORY_SAVE_CAKE = API + "/laboratory/save/cake";
        public static final String SUMMARY_SHOW =        API + "/summary/show";
        public static final String PROBE_SUN_SAVE =     API + "/probe/sun/save";
        public static final String PROBE_OIL_SAVE =     API + "/probe/oil/save";
        public static final String PROBE_CAKE_SAVE =     API + "/probe/cake/save";
        public static final String EXTRACTION_CRUDE_EDIT = API + "/extraction/crude/edit";
        public static final String EXTRACTION_RAW_EDIT = API + "/extraction/raw/edit";
        public static final String EXTRACTION_OIL_EDIT = API + "/extraction/oil/edit";
        public static final String SEAL_SAVE = "/seal/save";
        public static final String SEALS_FREE_FIND =     API + "/seals/free/find";
        public static final String SEAL_PUT =       API + "/transportation/seal/put";
        public static final String SEAL_REMOVE =    API + "/transportation/seal/remove";
        public static final String EXTRACTION_LIST = API + "/laboratory/extraction/list";
        public static final String VRO_LIST =       API + "/laboratory/vro/list";
        public static final String VRO_CRUDE_EDIT = API + "/laboratory/vro/crude/edit";
        public static final String VRO_OIL_EDIT = API + "/laboratory/vro/oil/edit";
        public static final String VRO_DAILY_EDIT = API + "/laboratory/vro/daily/edit";
        public static final String TRANSPORT_ARCHIVE = API + "/archive/transport";
        public static final String CHANGE_PASSWORD = API + "/personal/change/password";
        public static final String BOT_UID =            API + "/bot/uid/generator";
        public static final String USER_BOT_SETTINGS =       API + "/bot/user/settings";
        public static final String FIND_DEALS =         API + "/deal/find";
        public static final String PLAN_LIST_ADD =      API + "/plan/add";
        public static final String BOT_SETTINGS =       API + "/bot/settings";
        public static final String BOT_STATUS =         API + "/bot/status";
        public static final String WEIGHT_CANCEL =      API + "/weight/cancel";
        public static final String CHANGE_DATE =        API + "/logistic/change/date";
        public static final String EXTRACTION_TURN_CRUDE_EDIT = API + "/laboratory/extraction/turn/crude/edit";
        public static final String EXTRACTION_TURN_PROTEIN_EDIT = API + "/laboratory/extraction/turn/protein/edit";
        public static final String EXTRACTION_STORAGE_PROTEIN_EDIT = "/laboratory/extraction/storage/protein/edit";
        public static final String EXTRACTION_TURN_GREASE_EDIT = API + "/laboratory/extraction/turn/grease/edit";
        public static final String EXTRACTION_STORAGE_GREASE_EDIT = API + "/laboratory/extraction/storage/grease/edit";
        public static final String EXTRACTION_DAILY_REPORT_PRINT = API + "/laboratory/extraction/daily/report/print";
        public static final String OIL_MASS_FRACTION = API + "/laboratory/vro/oil/mass/fraction";
        public static final String OIL_MASS_FRACTION_DRY = OIL_MASS_FRACTION + "/dry";
        public static final String DELETE_OIL_MASS_FRACTION_DRY = OIL_MASS_FRACTION_DRY + "/delete";
        public static final String KPO_PART_EDIT = API + "/laboratory/kpo/part/edit";
        public static final String KPO_PART_DELETE = KPO_PART_EDIT + "/delete";
        public static final String KPO_PART_LIST = API + "/laboratory/kpo/list/update";
        public static final String RAIL_LIST = API + "/rails/list";
        public static final String RAILS_SAVE = API + "/rails/edit";
        public static final String VRO_DAILY_REPORT_PRINT = API + "/laboratory/vro/daily/report/print";
        public static final String LABORATORY_TURN_EDIT = API + "/laboratory/turn/edit";
        public static final String LABORATORY_TURN_LIST = API + "/laboratory/turn/list";
        public static final String LABORATORY_MONTHLY_REPORT = API + "/laboratory/monthly/report/print";
        public static final String LABORATORY_STORAGE_LIST = API + "/laboratory/storages/list";
        public static final String LABORATORY_STORAGE_EDIT = API + "/laboratory/storage/edit";
        public static final String LABORATORY_SUN_PRINT = API + "/laboratory/sun/print";
        public static final String FRIENDLY_REGISTRATION = API + "/friendly/registration";
        public static final String LABORATORY_OIL_PRINT = API + "/laboratory/oil/print";
        public static final String LABORATORY_MEAL_PRINT = API + "/laboratory/meal/print";
        public static final String PASSWORD_RESTORE = "/a/password/restore";
        public static final String SEAL_UPDATE = API + "/seals/list";
        public static final String FEEDBACK_EDIT = API + "/feedback/edit";
        public static final String CHANGE_LANGUAGE = API + "/personal/language";
        public static final String SAVE_NOTE = API + "/transportation/note/save";
        public static final String REMOVE_NOTE = API + "/transportation/note/remove";
        public static final String TRANSPORT_NOTES_LIST = API + "/transport/notes";
        public static final String SUBSCRIBER = "/api/subscriber";
        public static final String PARSE_VEHICLE = API + "/vehicle/parse";
        public static final String PARSE_PERSON = API + "/person/parse";
        public static final String REMOVE_PLAN = API + "/plan/remove";
        public static final String PARSE_AND_PUT_VEHICLE = API + "/parse/put/vehicle";
        public static final String PARSE_AND_PUT_DRIVER = API + "/parse/put/driver";
        public static final String CHAT_SEND = API + "/chat/send";
        public static final String GET_CHAT = API + "/chat/get";
        public static final String EXTRACTION_CRUDE_REMOVE = API + "/extraction/crude/remove";
        public static final String ARCHIVE_LOAD_PLAN = API + "/archive/loadplan";
        public static final String ARCHIVE_TRANSPORTATION = API + "/archive/transportation";
        public static final String ARCHIVE_DEAL = API + "/archive/deal";
        public static final String TRANSPORT_REGISTRATION = API + "/transport/registration";
        public static final String LEAVE_CHAT = API + "/chat/leave";
        public static final String REMOVE_CHAT = API + "/chat/remove";
        public static final String RENAME_CHAT = API + "/chat/rename";
        public static final String CHANGE_OFFICE = API + "/change/office";
        public static final String SAVE_MANUFACTURE_REPORT = API + "/manufacture/report/edit";
        public static final String ARCHIVE_FIND = API + "/archive/find";
        public static final String USER_BOT_DELETE = API + "/user/bot/delete";
        public static final String STORAGE_EDIT = API + "/storage/edit";
        public static final String WAREHOUSING_EDIT = API + "/warehousing/edit";
        public static final String REPORT_BUILDER = API + "/report/builder";
        public static final String ORGANISATION_COLLAPSE = API + "/organisation/collapse";
        public static final String FORMATTING_TEST = API + "/formatting/test";
        public static final String VRO_GRANULES_EDIT = API + "/granules/edit";
        public static final String VRO_SUN_PROTEIN = API + "/vro/sun/protein";
        public static final String EXTRACTION_TURN_CELLULOSE = API + "/cellulose/edit";
        public static final String ON_TERRITORY_PRINT = API + "/transport/on/territory";
        public static final String INCOME_TRANSPORT_PRINT = API + "/transport/income/print";
        public static final String REMOVE_TRANSPORT_TIME = API + "/transport/remove/time";
        public static final String STORAGE_STOCKS = API + "/storage/stocks";
        public static final String STORAGE_PRODUCT_REPLACE = API + "/storage/product/replace";
        public static final String MANUFACTURE_REPORT_SEND = API + "/manufacture/report/send";
        public static final String MANUFACTURE_REPORT_REMOVE = "/manufacture/report/remove.j";
        public static final String DRIVERS_COLLAPSE = API + "/drivers/collapse";
        public static final String TRANSPORT_CARRIAGE = API + "/transport/carriage";
        public static final String PARSE_TRAILER = API + "/trailer/parse";
        public static final String RETAIL_EDIT = API + "/retail/edit";
        public static final String FIND_PRODUCT = API + "/references/product/find";
        public static final String PARSE_PRODUCT = API + "/references/parse/product";
        public static final String PRODUCT_EDIT = API + "/references/product/edit";
        public static final String EDIT_CONTRACT = API + "/contracts/edit";
        public static final String MANUFACTURE_ANALYSE = API + "/manufacture/analyses";
        public static final String FIND_LABORATORY = API + "/laboratory/find";
        public static final String RETAIL_WAYBILL_PRINT = API + "/retail/waybill/print";
        public static final String EDIT_STOP_REPORT = API + "/stop/report/edit";
        public static final String RETAIL_PRINT = API + "/retail/print";
        public static final String FIND_PRICE = API + "/find/price";
        public static final String PRODUCT_STOCK_CORRECTION = API + "/product/stock/correction";
        public static final String PHONE_EDIT = API + "/references/phone/edit";
        public static final String PHONE_REMOVE = API + "/references/phone/remove";
        public static final String PROTOCOL_EDIT = API + "/standard/edit";
        public static final String LABORATORY_CARRIAGE = API + "/laboratory/carriage";
        public static final String CHECK = API + "/check";
        public static final String RETAIL_REMOVE = API + "/retail/remove";
        public static final String RETAIL_ARCHIVE = API + "/retail/archive";
        public static final String STORAGE_STOCKS_DETAILS = API + "/stocks/details";
        public static final String MEAL_GRANULES = API + "/extraction/meal/granules";
        public static final String TRANSPORT_PER_MONTH = API + "/transport/per/month";
        public static final String PROBE_FIND = API + "/laboratory/probe/find";
        public static final String FIND_EXTRACTION = API + "/laboratory/extraction/find";
        public static final String FIND_VRO = API + "/laboratory/vro/find";
        public static final String WAYBILL_PRINT = API + "/waybill/print";
        public static final String FIND_ORGANISATION_TYPE = API + "/find/organsiation/type";
        public static final String ORGANISATION_INFO = API + "/organisation/info";
        public static final String FIND_SEALS = API + "/seal/find";
        public static final String GET_STATISTIC = API + "/statistic/unload";
        public static final String DELETE_VEHICLE = API + "/vehicle/delete";
        public static final String ADDRESS_REMOVE = API + "/address/remove";
        public static final String TRANSPORTATION_SAVE_FAST = API + "/transportation/save/fast";
        public static final String BOARD_EDIT = API + "/board/edit";
        public static final String BOARD_REMOVE = API + "/board/remove";
        public static final String EXTRACTION_TURN_CELLULOSE_REMOVE = API + "/extraction/turn/cellulose/remove";
        public static final String REPORT_EDIT = API + "/report/edit";
        public static final String CODE_VALID = API + "/code/valid";
        public static final String SAVE_CUSTOM_TIME = API + "/custom/time";
        public static final String SAVE_USER_DATA = API + "/save/user";
        public static final String USER_DELETE = API + "/delete/user";
        public static final String TRANSPORT_COST = API + "/transport/cost";
        public static final String SEALS_REMOVE = API + "/seals/remove";
        public static final String INTEREST_SAVE = API + "/interest/save";
        public static final String KILL_SESSION = API + "/kill/session";
        public static final String TRAILER_EDIT = API + "/trailer/edit";
        public static final String DEAL_TRANSPORTATIONS = API + "/deal/transportations";
        public static final String CHANGE_ROLE = API + "/change/role";
        public static final String NOTE_EDIT = API + "/transportation/note/edit";

        public static class References {
            public static final String API = Branches.API.API + "/references";
            public static final String PARSE_ORGANISATION = API + "/organisation/parse";
            public static final String EDIT_ORGANISATION =  API + "/organisation/edit";
            public static final String FIND_ORGANISATION =  API + "/organisation/find";
            public static final String DRIVER_LIST =        API + "/driver/list";
            public static final String TERRITORY_INFO =     API + "/info";
            public static final String FIND_USER =          API + "/user/find";
            public static final String FIND_DRIVER =        API + "/driver/find";
            public static final String FIND_VEHICLE =       API + "/vehicle/find";
            public static final String SAVE_VEHICLE =       API + "/vehicle/save";
            public static final String SAVE_DRIVER =        API + "/driver/save";
            public static final String FIND_WORKER =       "/a/worker/find";
            public static final String ORGANISATION_LIST =  API + "/organisation/list";
            public static final String DRIVER_EDIT =        API + "/driver/edit";
            public static final String ORGANISATION_EDIT = API + "/organisation/edit";
            public static final String FIND_TRAILER = API + "/trailer/find";
            public static final String PARSE_TRAILER = API;
            public static final String ADDRESS_EDIT = API + "/address/edit";
            public static final String FIND_LOAD_ADDRESS = API + "/references/find/load/address";
            public static final String PRODUCT_LIST = API + "/references/product/list";
            public static final String FIND_PRODUCT_GROUP = API + "/references/find/group/product";
            public static final String PRODUCT_GROUP_EDIT = API + "/references/product/group/edit";
            public static final String PARSE_PRODUCT_GROUP = API + "/references/parse/product/group";
            public static final String FIND_CONTRACTS = API + "/references/find/contracts";
            public static final String VEHICLE_LIST = API + "/references/vehicles";
        }

        public static class Deal {
            public static final String DEAL = "/deal";
            public static final String EDIT_PRODUCT = DEAL + "/product/edit";
            public static final String DELETE_PRODUCT = DEAL + "/product/delete";
        }

        public static class Archive {
            public static final String DEALS = "/api/archive/deals";
        }

        public static class Transportation {
            public static final String DEAL_EDIT = API + "/transportation/deal/edit";
        }
    }

    public static class ShortCuts {
        public static final String SHORT_CUTS = "/api/short";
        public static final String UPDATE = SHORT_CUTS + "/update";
        public static final String MANAGER = SHORT_CUTS + "/manager";

    }

    public static class Sign {
        public static final String LOGOUT = "/api/logout";
    }
}
