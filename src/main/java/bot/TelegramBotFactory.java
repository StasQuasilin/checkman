package bot;

import entity.bot.*;
import entity.bot.BotSettings;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotSession;
import utils.PropertyReader;
import utils.notifications.Notificator;

import java.io.IOException;
import java.util.Properties;
import javax.swing.*;

import static constants.Constants.NAME;
import static constants.Constants.TOKEN;

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
    private static BotSession botSession;
    private static String context;
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

    static Timer timer;
    public static void start(String token, String name) {
        if (token != null && name != null) {
            bot = new TelegramBot(token, name);
            telegramBotsApi = new TelegramBotsApi();
            log.info("Try run bot " + name + "...");
            try {
                botSession = telegramBotsApi.registerBot(bot);
                botThread = new Thread(() -> {
                    status = BotStatus.worked;
                    telegramNotificator = new TelegramNotificator(bot);
                    Notificator.addNotificator(bot);
                    log.info("Bot " + name + " run successfully");
                    if (timer != null) {
                        timer.stop();
                    }
                });
                botThread.start();
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
                status = BotStatus.error;
                log.error("\t..." + e.getMessage());
                startTimer();
            }
        } else {
            startTimer();
        }

    }

    private static void startTimer() {
        if (timer == null) {
            timer = new Timer(60 * 1000, a -> init(context));
            timer.start();
        }
    }

    public static void shutdown() {
        if (botSession != null){
            botSession.stop();
        }
        if (botThread != null) {
            while (botThread.isAlive()) {
                botThread.interrupt();
            }
        }
        if (timer != null){
            timer.stop();
        }
    }

    public static BotStatus getStatus() {
        return status;
    }

    public static void setSettings(BotSettings settings) {
        token = settings.getToken();
        name = settings.getName();
        start(token, name);
    }

    public static TelegramNotificator getTelegramNotificator() {
        return telegramNotificator;
    }

    private static final String SETTINGS_BASE = System.getProperty("user.dir") + "/../bot_settings";
    private static final PropertyReader reader = new PropertyReader();
    public static void init(String contextPath) {
        context = contextPath;
        try {
            final Properties properties = reader.read(SETTINGS_BASE + contextPath, true);
            final String token = properties.getProperty(TOKEN);
            final String name = properties.getProperty(NAME);
            start(token, name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
