package entity.bot;

import entity.Worker;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
@Entity
@Table(name = "bot_user_settings")
public class UserBotSetting {
    private int id;
    private long telegramId;
    private Worker worker;
    private boolean show;
    private NotifyStatus transport = NotifyStatus.off;
    private NotifyStatus weight = NotifyStatus.off;
    private NotifyStatus analyses = NotifyStatus.off;
    private boolean extraction;
    private boolean vro;
    private boolean kpo;


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "telegram_id")
    public long getTelegramId() {
        return telegramId;
    }
    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    @OneToOne
    @JoinColumn(name = "worker")
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Basic
    @Column(name = "show")
    public boolean isShow() {
        return show;
    }
    public void setShow(boolean show) {
        this.show = show;
    }

    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "transport")
    public NotifyStatus getTransport() {
        return transport;
    }
    public void setTransport(NotifyStatus transport) {
        this.transport = transport;
    }

    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "weight")
    public NotifyStatus getWeight() {
        return weight;
    }
    public void setWeight(NotifyStatus weight) {
        this.weight = weight;
    }

    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "analyses")
    public NotifyStatus getAnalyses() {
        return analyses;
    }
    public void setAnalyses(NotifyStatus analyses) {
        this.analyses = analyses;
    }

    @Basic
    @Column(name = "extraction")
    public boolean isExtraction() {
        return extraction;
    }
    public void setExtraction(boolean extraction) {
        this.extraction = extraction;
    }

    @Basic
    @Column(name = "vro")
    public boolean isVro() {
        return vro;
    }
    public void setVro(boolean vro) {
        this.vro = vro;
    }

    @Basic
    @Column(name = "kpo")
    public boolean isKpo() {
        return kpo;
    }
    public void setKpo(boolean kpo) {
        this.kpo = kpo;
    }


}
