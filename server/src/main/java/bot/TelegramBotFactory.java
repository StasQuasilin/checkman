package bot;

import entity.bot.*;
import entity.bot.BotSettings;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import utils.hibernate.dbDAOService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class TelegramBotFactory {

    private static final Logger log = Logger.getLogger(TelegramBotFactory.class);


    static {
        ApiContextInitializer.init();
    }

    private static TelegramBot bot;
    static TelegramBotsApi telegramBotsApi;
    static Thread botThread;
    private static String token;
    private static String name;
    private static BotStatus status = BotStatus.stopped;
    private static TelegramNotificator telegramNotificator;
    private static BotSettings currentSettings;

    public static IBot getBot() {
        return bot;
    }

    public static String getToken() {
        return token;
    }
    public static void setToken(String token) {
        TelegramBotFactory.token = token;
    }

    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        TelegramBotFactory.name = name;
    }

    public static void start() throws IOException {

        if (bot == null){
            log.info("Bot start...");
            if (name != null && token != null) {
                botThread = new Thread(() -> {
                    bot = new TelegramBot(token, name);
                    telegramBotsApi = new TelegramBotsApi();
                    try {
                        telegramBotsApi.registerBot(bot);
                        status = BotStatus.worked;
                        telegramNotificator = new TelegramNotificator(bot);
                    } catch (TelegramApiRequestException e) {
                        status = BotStatus.error;
                        log.trace(e);
                    }
                });
                botThread.start();
            } else if (token == null){
                status = BotStatus.no_token;
                log.info("Bot token is null! Please fix it");
            } else {
                status = BotStatus.no_name;
                log.info("Bot name is null! Please fix it");
            }
        }
    }

    public static void shutdown() {

        if (botThread != null) {
            while (botThread.isAlive()) {
                botThread.interrupt();
            }
        }
        if (currentSettings != null) {
            currentSettings.setRun(false);
            dbDAOService.getDAO().save(currentSettings);
        }
    }

    public static BotStatus getStatus() {
        return status;
    }

    public static void setSettings(BotSettings settings) throws IOException {
        currentSettings = settings;
        token = settings.getToken();
        name = settings.getName();
        start();
    }

    public static TelegramNotificator getTelegramNotificator() {
        return telegramNotificator;
    }

    ArrayList<IBot> bots = new ArrayList<>();
    public static void init() {
        readSettings();

    }
    Properties properties;
    InputStream inputStream;
    boolean fileRead = false;

    private static void readSettings() {
    }
}