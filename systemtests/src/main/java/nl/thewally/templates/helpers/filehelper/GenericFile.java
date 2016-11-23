package nl.thewally.templates.helpers.filehelper;

/**
 * Created by arjen on 23-11-16.
 */
public class GenericFile {
    private String filename, path;

    public GenericFile(String path, String filename) {
        this.path = path;
        this.filename = filename;
    }

    public String getFilename() {
        return "";
    }

    public String getPath() {
        return "";
    }

    public void compress(){
    }

    public boolean isAvailable() {
        return true;
    }
}
