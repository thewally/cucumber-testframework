package nl.thewally.templates.helpers.filehelper;

import java.nio.file.Path;

/**
 * Created by arjen on 23-11-16.
 */
public class EncryptedFile extends GenericFile {
    public EncryptedFile(Path path, String filename) {
        super(path, filename);
    }

    public void decrypt() {

    }
}
