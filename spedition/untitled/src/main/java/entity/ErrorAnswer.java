package entity;

import constants.Keys;

public class ErrorAnswer extends ServerAnswer{

    public ErrorAnswer(String message) {
        addParam(Keys.MESSAGE, message);
    }

    @Override
    String getStatus() {
        return Keys.ERROR;
    }
}
