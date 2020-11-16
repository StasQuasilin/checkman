package ua.svasilina.spedition.constants;

public interface ApiLinks {
    String HOME = "http://10.10.10.201:3322/spedition-old";
//    String HOME = "http://134.249.155.33:32332/spedition";
    String LOGIN = HOME + "/sign/in";
    String API = "/api/v1";
    String REPORT_SAVE = HOME + API+ "/report/save";
    String GET_REPORTS = HOME + API + "/active/reports";
}
