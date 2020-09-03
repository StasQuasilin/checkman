package utils.answer;

import static constants.Keys.ERROR;

public class ErrorAnswer extends Answer {
    @Override
    String getStatus() {
        return ERROR;
    }
}
