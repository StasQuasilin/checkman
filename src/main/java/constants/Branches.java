package constants;

import controllers.archive.ArchiveType;
import org.glassfish.grizzly.utils.StringFilter;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class Branches {
    public static class UI{

        public static final String SING_IN = "/login";
        public static final String APPLICATION = "/application.j";
        public static final String DEAL_LIST = "/deals/list.j";
        public static final String DEAL_BUY = DEAL_LIST + "?type=buy";
        public static final String DEAL_SELL = DEAL_LIST + "?type=sell";
        public static final String DEAL_SHOW = "/deal/show.j";
        public static final String DEAL_EDIT = "/deal/edit.j";
        public static final String LOAD_PLAN = "/deals/load/plan.j";
        public static final String LOGISTIC_LIST = "/logistic/list.j";
        public static final String TRANSPORT_LIST = "/transport/list.j";
        public static final String TRANSPORT_SHOW = "/transport/show";
        public static final String TRANSPORT_EDIT = "/transport/edit.j";
        public static final String WEIGHT_LIST = "/weight/list.j";
        public static final String WEIGHT_EDIT = "/weight/edit.j";

        public static final String HOME = APPLICATION;
        public static final String VEHICLE_MODAL = "/vehicle/modal.j";
        public static final String DRIVER_MODAL = "/driver/modal.j";
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

        public class Extraction {
            public static final String CRUDE_EDIT = "/laboratory/extraction/crude.j";
            public static final String RAW_EDIT = "/laboratory/extraction/raw.j";
            public static final String OIL_EDIT = "/laboratory/extraction/oil.j";
        }

        public class VRO {
            public static final String CRUDE_EDIT = "/laboratory/vro/crude.j";
            public static final String OIL_EDIT = "/laboratory/vro/oil.j";
            public static final String DAILY_EDIT = "/laboratory/vro/daily.j";
        }
    }
    public class API{
        public static final String API = "/api";
        public static final String DEAL_SAVE =          API + "/deal/save";
        public static final String DEAL_DELETE =        API + "/deal/delete";
        public static final String SIGN_IN =            API + "/sign/in";
        public static final String SIGN_UP =            API + "/sign/up";
        public static final String DEAL_LIST =          API + "/deal/list";
        public static final String DEAL_LIST_BUY =      DEAL_LIST + "?type=buy";
        public static final String DEAL_LIST_SELL =     DEAL_LIST + "?type=sell";
        public static final String PLAN_LIST =          API + "/load/plan/list";
        public static final String PLAN_LIST_SAVE =     API + "/load/plan/save";
        public static final String LOGISTIC_LIST =      API + "/logistic/list";
        public static final String LOGISTIC_SAVE =      API + "/logistic/save";
        public static final String TRANSPORT_UPDATE =   API + "/transport/list";
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
        public static final String SEALS_FIND =     API + "/seals/find";
        public static final String SEAL_PUT =       API + "/transportation/seal/put";
        public static final String SEAL_REMOVE =    API + "/transportation/seal/remove";
        public static final String EXTRACTION_LIST = API + "/laboratory/extraction/list";
        public static final String VRO_LIST =       API + "/laboratory/vro/list";
        public static final String VRO_CRUDE_EDIT = API + "/laboratory/vro/crude/edit";
        public static final String VRO_OIL_EDIT = API + "/laboratory/vro/oil/edit";
        public static final String VRO_DAILY_EDIT = API + "/laboratory/vro/daily/edit";
        public static final String TRANSPORT_ARCHIVE = API + "/archive/transport";


        public class References {
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
            public static final String FIND_WORKER =       API + "/worker/find";
        }

        public class Deal {
            public static final String DEAL = "/deal";
            public static final String EDIT_PRODUCT = DEAL + "/product/edit";
            public static final String DELETE_PRODUCT = DEAL + "/product/delete";
        }

        public class Archive {
            public static final String DEALS = "/api/archive/deals";
        }
    }

    public class ShortCuts {
        public static final String SHORT_CUTS = "/api/short";
        public static final String UPDATE = SHORT_CUTS + "/update";
        public static final String MANAGER = SHORT_CUTS + "/manager";

    }

    public class Sign {
        public static final String LOGOUT = "/api/logout";
    }
}
