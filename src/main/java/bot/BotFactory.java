package bot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import utils.PropertyReader;

import java.io.IOException;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class BotFactory {

    static {
        ApiContextInitializer.init();
    }

    private static IBot bot;
    static TelegramBotsApi telegramBotsApi;
    static Thread botThread;
    private static final String TOKEN = "692851073:AAH2bDUXli8lgnAqeeCol-dCPGa2k_GDDTc";
    private static final String NAME = "Baklagan";

    public static IBot getBot() {
        return bot;
    }

    public static void init() throws IOException {
        if (bot == null) {
//            PropertyReader reader = new PropertyReader(System.getProperty("user.home") + "\\bot_token.txt");
            botThread = new Thread(() -> {
                bot = new TelegramBot(TOKEN, NAME);
                telegramBotsApi = new TelegramBotsApi();
                try {
                    telegramBotsApi.registerBot(bot);
                } catch (TelegramApiRequestException e) {
                    e.printStackTrace();
                }
            });
            botThread.start();
        }
    }

    public static void shutdown() {
        if (botThread != null && botThread.isAlive()) {
            botThread.interrupt();
        }
    }
}
