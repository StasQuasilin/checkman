package ua.svasilina.spedition.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

public class StorageUtil {


    private final Context context;

    public StorageUtil(Context context) {
        this.context = context;
    }

    public void saveData(String fileName, String data){
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    File[] getFiles(FileFilter fileFilter){
        final File[] files = context.getFilesDir().listFiles(fileFilter);
        if (files != null){
            for (File file : files){
                System.out.println(file.getName());
            }
        }
        return files;
    }

    String readFile(String name) {
        try {
            final FileInputStream fileInputStream = context.openFileInput(name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder builder = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null){
                builder.append(s);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearStorage(FileFilter filter){
        final File[] files = getFiles(filter);
        if (files != null){
            for (File file : files){
                file.delete();
            }
        }
    }

    public void remove(String name) {

    }
}
