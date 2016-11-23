package nl.thewally.templates.helpers.filehelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

    public void createFile() {
        try {
            if (!fullfilepath.exists()) {
                fullfilepath.createNewFile();
            }
        } catch (IOException ex) {
            LOG.error("Create file failed {}", ex);
        }
    }

    public void setValueOnNewRowInFile(String content) {
        try {
            if (!fullfilepath.exists()) {
                createFile();
            }
            FileWriter fw = new FileWriter(fullfilepath.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException ex) {
            LOG.error("Cannot set value {} for file {}:/n{}", content, fullfilepath.getAbsolutePath(), ex);
        }
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

    public void getAllRowsOfFile() {
    }

    public void removeFile(){
    }

    public File compress(){
        File path = new File(getPath());
        return compress(path);
    }
    public File compress(File path) {
        //TODO compress file
        //TODO return fullfilepath of new compressed file
        return fullfilepath;
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
