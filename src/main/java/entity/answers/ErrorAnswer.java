package entity.answers;

import constants.Constants;

/**
 * Created by szpt_user045 on 19.02.2019.
 */
public class ErrorAnswer extends Answer {

    public ErrorAnswer(String msg) {
        add(Constants.MSG, msg);
    }

    @Override
    public String status() {
        return Constants.ERROR;
    }
}
