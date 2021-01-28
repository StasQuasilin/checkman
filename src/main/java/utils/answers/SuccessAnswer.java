package utils.answers;

import constants.Constants;
import entity.answers.Answer;

/**
 * Created by szpt_user045 on 19.02.2019.
 */
public class SuccessAnswer extends Answer {

    public SuccessAnswer() {}
    public SuccessAnswer(String key, Object value) {
        add(key, value);
    }

    @Override
    public String status() {
        return Constants.SUCCESS;
    }
}
