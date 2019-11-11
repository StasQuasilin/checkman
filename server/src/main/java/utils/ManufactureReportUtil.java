package utils;

import bot.Notificator;
import entity.manufactoring.ReportMessageLink;
import entity.reports.ManufactureReport;
import org.telegram.telegrambots.meta.api.objects.Message;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.11.2019.
 */
public class ManufactureReportUtil {

    private final dbDAO dao = dbDAOService.getDAO();

    public void removeChatMessages(ManufactureReport report, Notificator notificator) {
        if (report != null) {
            HashMap<String, Object> param = new HashMap<>();
            param.put("report", report.getId());

            for (ReportMessageLink link : dao.getObjectsByParams(ReportMessageLink.class, param)) {
                notificator.removeMessage(link.getChatId(), link.getMessageId());
                dao.remove(link);
            }
        }
    }

    public void saveMessages(ManufactureReport report, ArrayList<Message> messages) {
        for (Message message : messages) {
            if (message.getChat() != null) {
                System.out.println(message.getChat().getId());
                ReportMessageLink messageLink = new ReportMessageLink();
                messageLink.setReport(report);

                messageLink.setChatId(message.getChat().getId());
                messageLink.setMessageId(message.getMessageId());
                dao.save(messageLink);
            }
        }
    }
}
