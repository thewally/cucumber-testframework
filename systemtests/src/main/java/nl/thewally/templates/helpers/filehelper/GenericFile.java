package nl.thewally.templates.helpers.filehelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;

/**
 * Created by arjen on 23-11-16.
 */
public class GenericFile {

    private static final Logger LOG = LoggerFactory.getLogger(GenericFile.class);

    private String filename, path;
    private File fullfilepath;

    public GenericFile(String path, String filename) {
        this.path = path;
        this.filename = filename;
        this.fullfilepath = new File(path + filename);
    }

    public String getFilename() {
        return filename;
    }

    public String getPath() {
        return path;
    }

    public File getFullFilePath() {
        return fullfilepath;
    }

    public URI getFileUri() {
        return fullfilepath.toURI();
    }

    public void compress(){
    }

    public boolean isAvailable() {
        if (fullfilepath.exists()) {
            LOG.debug("File exists : {}", fullfilepath.getAbsolutePath());
            return true;
        } else {
            LOG.debug("File not found! : {}", fullfilepath.getAbsolutePath());
        }
        return false;
    }
}
