package utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class PropertyReader {

    private final Logger log = Logger.getLogger(PropertyReader.class);

    public Properties read(String fileName, boolean create) throws IOException {
        final Properties properties = new Properties();
        File file = new File(fileName);
        if (file.exists()) {
            InputStream stream = new FileInputStream(fileName);
            properties.load(stream);
            stream.close();
            log.info("File '" + file.getPath() + "' read successfully!");
        } else {
            log.info("File '" + file.getAbsolutePath() + "' not found");
            if (create){
                final File parentFile = file.getParentFile();
                if (parentFile.mkdirs()){
                    log.info("Path '"+ parentFile.getPath() + "'created");
                }
                if (file.createNewFile()) {
                    log.info("File '" + file.getAbsolutePath() + "' created. You can fill this");
                }
            }
        }
        return properties;
    }


}
