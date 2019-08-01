package ua.quasilin.chekmanszpt.entity;

import ua.quasilin.chekmanszpt.utils.LoginStatus;

/**
 * Created by szpt_user045 on 01.08.2019.
 */

public class LoginAnswer {
    private final LoginStatus status;
    private String message;

    public LoginAnswer(LoginStatus status) {
        this.status = status;
    }

    public LoginAnswer(LoginStatus status, String message) {
        this(status);
        this.message = message;
    }

    public LoginStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
