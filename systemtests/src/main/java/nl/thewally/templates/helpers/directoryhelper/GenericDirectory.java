package nl.thewally.templates.helpers.directoryhelper;

import nl.thewally.templates.helpers.filehelper.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

/**
 * Created by arjen on 25-11-16.
 */
public class GenericDirectory {
    private static final Logger LOG = LoggerFactory.getLogger(GenericDirectory.class);
    private Path path;

    public GenericDirectory(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public Path getSubDirectory(String directoryName) {
        Path pathDir = null;
        try{
            pathDir = new File(path.toString() + File.separator + directoryName).toPath();
            LOG.info("Directory exists : {}", pathDir);
        } catch (InvalidPathException e) {
            LOG.error("Directory not found:\n{}", e);
        }
        return pathDir;
    }

    public Path createSubDirectory(String directoryName) throws IOException {
        return Files.createDirectory(new File(path.toString() + File.separator + directoryName).toPath());
    }

    public void removeDirectory() {
        File folder = path.toFile();
        removeDirectory(folder);
    }

    public void removeDirectory(File folder) {
        try{

            File[] files = folder.listFiles();
            if(files!=null) { //some JVMs return null for empty dirs
                for(File f: files) {
                    if(f.isDirectory()) {
                        removeDirectory(f);
                    } else {
                        f.delete();
                    }
                }
            }
            folder.delete();
            LOG.info("Directory exists and deleted : {}", folder);
        } catch (InvalidPathException e) {
            LOG.error("Directory not found:\n{}", e);
        }
    }

    public boolean isAvailable() {
        if (path.toFile().exists()) {
            LOG.debug("File exists : {}", path.toFile().getAbsolutePath());
            return true;
        } else {
            LOG.debug("File not found! : {}", path.toFile().getAbsolutePath());
        }
        return false;
    }
}
