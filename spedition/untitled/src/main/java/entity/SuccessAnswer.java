package entity;

import constants.Keys;

public class SuccessAnswer extends ServerAnswer{

    @Override
    String getStatus() {
        return Keys.SUCCESS;
    }
}
