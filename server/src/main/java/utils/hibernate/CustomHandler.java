package utils.hibernate;

import constants.Constants;
import entity.transport.*;
import utils.DocumentUIDGenerator;
import utils.U;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler implements Constants{

    static dbDAO dao = dbDAOService.getDAO();


    public static void main(String[] args) {
        Hibernator instance = Hibernator.getInstance();

        for (DocumentNote note : dao.getObjects(DocumentNote.class)){
            Transportation transportation = note.getTransportation();
            if (!U.exist(transportation.getUid())){
                transportation.setUid(DocumentUIDGenerator.generateUID());
                dao.save(transportation);
            }
            note.setDocument(transportation.getUid());
            dao.save(note);
        }

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
