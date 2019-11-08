package entity.manufactoring;

import entity.reports.ManufactureReport;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 06.11.2019.
 */
@Entity
@Table(name = "report_messages")
public class ReportMessageLink {
    private int id;
    private ManufactureReport report;
    private long chatId;
    private int messageId;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "report")
    public ManufactureReport getReport() {
        return report;
    }
    public void setReport(ManufactureReport report) {
        this.report = report;
    }

    @Basic
    @Column(name = "chat")
    public long getChatId() {
        return chatId;
    }
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Basic
    @Column(name = "message")
    public int getMessageId() {
        return messageId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
