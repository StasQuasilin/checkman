package entity.answers;

import constants.Constants;

/**
 * Created by szpt_user045 on 19.02.2019.
 */
public class ErrorAnswer extends Answer {

    public ErrorAnswer(String msg, String text) {
        add(msg, text);
    }

    @Override
    public String status() {
        return Constants.ERROR;
    }
}
