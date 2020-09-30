package utils.answers;

import constants.Keys;

public class SuccessAnswer extends Answer {
    @Override
    String getStatus() {
        return Keys.SUCCESS;
    }
}
