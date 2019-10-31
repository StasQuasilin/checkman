package utils.storages;

import constants.Constants;
import entity.storages.StorageDocumentType;
import entity.transport.Transportation;
import entity.weight.Weight;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class DocumentTypeUtil {
    StorageDocumentType getType(Weight weight){
        return StorageDocumentType.weight;
    }

}
