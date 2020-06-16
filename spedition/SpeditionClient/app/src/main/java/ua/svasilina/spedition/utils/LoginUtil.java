package ua.svasilina.spedition.utils;

import android.content.Context;

import java.io.File;

public class LoginUtil {
    private final StorageUtil storageUtil;
    private static final String FILE_NAME = "user_access";
    private final NetworkUtil net;

    public LoginUtil(Context context) {
        storageUtil = new StorageUtil(context);
        net = new NetworkUtil();
    }

    public void saveToken(String token) {
        storageUtil.saveData(FILE_NAME, token);
    }

    public boolean checkLogin() {

        final String userData = storageUtil.readFile(FILE_NAME);
        return userData != null;
    }

    public String getToken() {
        return storageUtil.readFile(FILE_NAME);
    }

    public void removeToken(){
        final FileFilter fileFilter = new FileFilter(FILE_NAME);
        final File[] files = storageUtil.getFiles(fileFilter);
        if (files != null){
            for(File file : files){
                file.delete();
            }
        }
    }
}
