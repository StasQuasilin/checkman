package utils.answers;

import constants.Constants;
import entity.answers.IAnswer;
import org.json.simple.JSONObject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.Map;

/**
 * Created by szpt_user045 on 19.02.2019.
 */
public class SuccessAnswer extends IAnswer {

    public SuccessAnswer() {}
    public SuccessAnswer(String key, Object value) {
        add(key, value);
    }

    @Override
    public String status() {
        return Constants.SUCCESS;
    }
}
