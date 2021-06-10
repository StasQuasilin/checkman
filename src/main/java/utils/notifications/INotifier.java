package utils.notifications;

public interface INotifier {
    void sendMessage(long chatId, String message);
}
