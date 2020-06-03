package api.socket.handlers;

import api.socket.SubscribeType;
import entity.Report;
import org.json.simple.JSONArray;
import utils.hibernate.dao.ReportDAO;

public class ReportHandler extends Handler {
    private final ReportDAO reportDAO = new ReportDAO();

    public ReportHandler(SubscribeType subscribeType) {
        super(subscribeType);
    }

    @Override
    public Object getData() {
        JSONArray array = new JSONArray();
        for (Report report : reportDAO.getReports()){
            array.add(report.toSimpleJson());
        }
        return array;
    }
}
