package stanislav.vasilina.speditionclient.utils;

import java.io.File;
import java.io.FilenameFilter;

public class ReportFilter implements FilenameFilter {

    private final String mask;

    ReportFilter(String mask) {
        this.mask = mask;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.contains(mask);
    }
}