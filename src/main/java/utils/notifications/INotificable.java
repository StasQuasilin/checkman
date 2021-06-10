package utils.notifications;

public interface INotificable {
    String prepareMessage();
    NotificationType getType();
}
