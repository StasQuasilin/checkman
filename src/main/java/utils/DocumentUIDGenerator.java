package utils;

import entity.documents.DocumentUID;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.UUID;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class DocumentUIDGenerator {

    static dbDAO dao = dbDAOService.getDAO();

    public static String generateUID(){
        String uid = UUID.randomUUID().toString();
        if(!check(uid)){
            return generateUID();
        }
        return uid;
    }
    private static boolean check(String uid){
        DocumentUID documentUID = dao.getDocumentUID(uid);
        if (documentUID == null){
            documentUID = new DocumentUID(uid);
            dao.save(documentUID);
            return true;
        } else {
            return false;
        }
    }
}
