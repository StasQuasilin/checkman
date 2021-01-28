package entity.notifications;

import entity.Worker;

public class SystemMessage {
    private int id;
    private Worker recipient;
    private String message;
    private String positiveAction;
    private String neutralAction;
    private String negativeAction;
}
