package utils.answers;

import constants.Keys;

public class ErrorAnswer extends Answer {

    public ErrorAnswer(String reason) {
        addAttribute(Keys.REASON, reason);
    }

    @Override
    String getStatus() {
        return Keys.ERROR;
    }
}
