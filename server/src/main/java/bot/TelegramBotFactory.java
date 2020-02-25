package bot;

import entity.bot.*;
import entity.bot.BotSettings;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotSession;
import utils.hibernate.dbDAOService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.*;

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

    static Timer timer;
    public static void start() throws IOException {

        if (name != null && token != null) {
            botThread = new Thread(() -> {
                bot = new TelegramBot(token, name);
                telegramBotsApi = new TelegramBotsApi();
                try {
                    log.info("Try start telegram bot...");
                    botSession = telegramBotsApi.registerBot(bot);
                    status = BotStatus.worked;
                    telegramNotificator = new TelegramNotificator(bot);
                    log.info("Bot \'" + name + "\' started successfully");
                    if (timer != null){
                        timer.stop();
                    }
                } catch (TelegramApiRequestException e) {
                    status = BotStatus.error;
                    log.info("\t..." + e.getMessage());
                    if (timer == null) {
                        log.info("...Start timer");
                        timer = new Timer(60 * 1000, a -> {
                            try {
                                start();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        });
                        timer.start();
                    }
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

    public static void setSettings(BotSettings settings) throws IOException {
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
