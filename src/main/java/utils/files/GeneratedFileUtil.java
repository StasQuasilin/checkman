package utils.files;

import entity.GeneratedFile;
import utils.hibernate.Hibernator;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class GeneratedFileUtil {

    private final Hibernator hibernator = Hibernator.getInstance();

    public void fixFile(String fileName) {
        GeneratedFile generatedFile = new GeneratedFile();
        generatedFile.setFile(fileName);
        generatedFile.setTime(Timestamp.valueOf(LocalDateTime.now()));
        hibernator.save(generatedFile);
    }

    public void removeFile(String fileName) {
        final GeneratedFile generatedFile = hibernator.get(GeneratedFile.class, "file", fileName);
        if(generatedFile != null){
            hibernator.remove(generatedFile);
        }
    }
}
