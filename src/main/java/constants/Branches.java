package constants;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class Branches {
    public class UI{

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
        public static final String DEPARTMENT_LIST = "/laboratory/turn.j";
        public static final String LABORATORY = "/laboratory.j";
        public static final String LABORATORY_BUY = LABORATORY + "?type=buy";
        public static final String LABORATORY_SELL = LABORATORY + "?type=sell";
        public static final String REFERENCES = "/references.j";
        public static final String ADMIN = "/admin.j";
        public static final String EDIT_PROBE = "/laboratory/probe/edit.j";
        public static final String PROBE_SHOW = "/laboratory/probe/show.j";
    }
    public class API{
        public static final String API = "/api";
        public static final String DEAL_SAVE =          API + "/deal/save.j";
        public static final String DEAL_DELETE =        API + "/deal/delete.j";
        public static final String SIGN_IN =            API + "/sign/in";
        public static final String SIGN_UP =            API + "/sign/up";
        public static final String DEAL_LIST =          API + "/deal/list.j";
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
        public static final String LABORATORY_LIST =    API + "/laboratory/list.j";


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
        }

        public class Deal {
            public static final String DEAL = "/deal";
            public static final String EDIT_PRODUCT = DEAL + "/product/edit";
            public static final String DELETE_PRODUCT = DEAL + "/product/delete";
        }
    }
}
