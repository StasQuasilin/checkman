package utils;

import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.impl.Log;

import java.io.*;
import java.util.Properties;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class PropertyReader {

    private final Logger log = Logger.getLogger(PropertyReader.class);

    private final String fileName;
    private final Properties properties = new Properties();
    public PropertyReader(String file) throws IOException {
        this.fileName = file;
        read();
    }

    private void read() throws IOException {
        File file = new File(this.fileName);
        if (file.exists()) {
            InputStream stream = new FileInputStream(this.fileName);
            properties.load(stream);
            stream.close();
        } else {
            if (file.createNewFile()) {
                log.info("File " + file.getAbsolutePath() + " was created. Please put token");
            }
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
