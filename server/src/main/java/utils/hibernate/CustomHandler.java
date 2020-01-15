package utils.hibernate;

import entity.DealType;
import entity.Person;
import entity.User;
import entity.Worker;
import entity.deal.Contract;
import entity.documents.Shipper;
import entity.products.Product;
import entity.reports.ReportField;
import entity.reports.ReportFieldSettings;
import entity.storages.Storage;
import entity.storages.StoragePeriodPoint;
import entity.storages.StorageProduct;
import entity.transport.*;
import utils.DateUtil;
import utils.TurnDateTime;
import utils.U;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.GE;
import utils.hibernate.DateContainers.GT;
import utils.hibernate.DateContainers.LT;
import utils.storages.PointScale;
import utils.storages.StorageUtil;
import utils.transport.CollapseUtil;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {

    static dbDAO dao = dbDAOService.getDAO();


    public static void main(String[] args) {
        Hibernator instance = Hibernator.getInstance();
        HibernateSessionFactory.shutdown();
    }

    static String pretty(String number){
        StringBuilder builder = new StringBuilder();
        for (char c : number.toCharArray()){
            if (Character.isDigit(c) || Character.isLetter(c)){
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
