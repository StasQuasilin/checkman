package entity;

import constants.Keys;

public class ErrorAnswer extends ServerAnswer{
    @Override
    String getStatus() {
        return Keys.ERROR;
    }
}
