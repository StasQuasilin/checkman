package utils;

import api.sign.SignInAPI;
import constants.Branches;
import constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by quasilin on 14.03.2019.
 */
public class LoginBox {

    static final LoginBox instance = new LoginBox();

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

    static final String FILE_NAME = "D:\\server.txt";
    Properties properties;
    InputStream inputStream;

    public void init() throws IOException {
        properties = new Properties();

        inputStream = new FileInputStream(FILE_NAME);

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getLogin() {
        return properties.getProperty("login");
    }
    public String getPassword() {
        return properties.getProperty("password");
    }
    public boolean trySignIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = getLogin();
        System.out.println("login: " + login);

        String password = getPassword();
        System.out.println("password: " + password);

        return SignInAPI.signIn(req, resp, login, password).status().equals("success");
    }
}