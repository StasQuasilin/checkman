package utils;

import api.sign.SignInAPI;
import constants.Branches;
import constants.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by quasilin on 14.03.2019.
 */
public class LoginBox {

    static final LoginBox instance = new LoginBox();
    private Logger log = Logger.getLogger(LoginBox.class);

    public static LoginBox getInstance() {
        return instance;
    }

    public LoginBox() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Properties properties;
    InputStream inputStream;
    boolean fileRead = false;

    public void init() throws IOException {
        properties = new Properties();
        File file = new File(System.getProperty("user.home") + "/server_access.txt");

        if (file.exists()) {
            inputStream = new FileInputStream(file);
            try {
                properties.load(inputStream);
                fileRead = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                inputStream.close();
            }
        } else {
            log.info("File \'" + file.getPath() + " not found");
        }
    }
    public String getLogin() {
        return properties.getProperty("login");
    }
    public String getPassword() {
        return properties.getProperty("password");
    }
    public boolean trySignIn(HttpServletRequest req) throws IOException {
        if (fileRead) {
            String login = getLogin();
            String password = getPassword();

            return SignInBox.signIn(req, login, password).status().equals("success");
        }
        return false;
    }
}
