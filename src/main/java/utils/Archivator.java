package utils;

import entity.documents.Deal;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import utils.hibernate.DateContainers.LE;
import utils.hibernate.dao.DealDAO;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 17.07.2019.
 */
public final class Archivator {

    final static Logger log = Logger.getLogger(Archivator.class);
    final static dbDAO dao = dbDAOService.getDAO();
    final static DealDAO dealDao = new DealDAO();
    final static UpdateUtil updateUtil = new UpdateUtil();

    public static void init(){
        next();
    }

    private static final DealUtil dealUtil = new DealUtil();

    private static void check() {
        Date date = Date.valueOf(LocalDate.now().minusDays(1));
        LE lt = new LE(date);
        log.info("Check documents less or equals then " + date.toString());
        for (Transportation transportation : dao.getDone(Transportation.class, lt)){
            transportation.setArchive(true);
            dao.save(transportation);
            updateUtil.onRemove(transportation);
            log.info("\tArchive transportation " + transportation.getId());
        }
        date = Date.valueOf(LocalDate.now().minusMonths(1));
        lt = new LE(date);
        for (Deal deal : dealDao.getDealsAfter(lt)){
            dealUtil.removeDeal(deal);
            log.info("\tArchive deal " + deal.getId());
        }
    }

    static Timer timer;
    final static int HOURS = 12;
    final static int MILLISECONDS = HOURS * 60 * 60 * 1000;
    private static void next() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        LocalDateTime next = LocalDateTime.now().plusHours(HOURS);
        log.info("Next archive check at " + next.toString());
        timer = new Timer(MILLISECONDS, e -> check());
        timer.start();
    }
    public static void stop(){
        timer.stop();
    }
}
