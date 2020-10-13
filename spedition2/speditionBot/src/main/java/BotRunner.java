import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class BotRunner {

    public static void main(String[] args) {
        System.out.println("Run application...");
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
            botsApi.registerBot(new BotLPC(botOptions, true));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
