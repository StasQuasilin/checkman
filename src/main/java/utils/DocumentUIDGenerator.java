package utils;

import entity.documents.DocumentUID;
import utils.hibernate.Hibernator;

import java.util.UUID;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class DocumentUIDGenerator {

    private static final Hibernator hibernator = Hibernator.getInstance();

    public static String generateUID(){
        String uid = UUID.randomUUID().toString();
        if(!check(uid)){
            return generateUID();
        }
        return uid;
    }
    private static boolean check(String uid){
        DocumentUID documentUID = hibernator.get(DocumentUID.class, "uid", uid);
        if (documentUID == null){
            documentUID = new DocumentUID(uid);
            hibernator.save(documentUID);
            return true;
        } else {
            return false;
        }
    }
}
