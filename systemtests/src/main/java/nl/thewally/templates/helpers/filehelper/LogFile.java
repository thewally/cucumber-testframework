package nl.thewally.templates.helpers.filehelper;

import java.nio.file.Path;
import java.util.Date;

/**
 * Created by arjen on 24-11-16.
 */
public class LogFile extends GenericFile {
    public LogFile(Path path, String filename) {
        super(path, filename);
    }

    public void clearLogFile() {
        //TODO: implement method clearLogFIle
    }

    public String getLastRow() {
        //TODO: implement method getLastRow
        return "";
    }

    public void setLogBeginRecordPoint() {
        //TODO: implement method setLogBeginRecordPoint
    }

    public String getRecordedLog() {
        //TODO: implement method getRecordedLog
        return "";
    }
}
