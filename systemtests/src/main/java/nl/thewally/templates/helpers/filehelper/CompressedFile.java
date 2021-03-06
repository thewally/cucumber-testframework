package nl.thewally.templates.helpers.filehelper;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by arjen on 23-11-16.
 */
public class CompressedFile extends GenericFile {
    private static final Logger LOG = LoggerFactory.getLogger(CompressedFile.class);
    private static final int ONE_K = 1024;

    public CompressedFile(Path path, String filename) {
        super(path, filename);
        String extension = FilenameUtils.getExtension(filename);
        if(!extension.equals("zip")){
            LOG.error("This file with extension {} is not a compressed file", extension);
        }
    }

    public void decompress() {
        decompress(getPath());
    }

    public void decompress(Path newPath) {
        File newLocation = new File(newPath.toString());
        LOG.info("Unzip {} to folder {}", getFullFilePath().getAbsolutePath(), newLocation.getAbsolutePath());
        try {
            //create output directory is not exists
            if (!newLocation.exists() && !newLocation.mkdir()) {
                LOG.error("Error making dir {}", newLocation.getAbsolutePath());
            }
            String lastFileName = null;
            //get the zipped file list entry
            //get the zip file content
            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(getFullFilePath()))) {
                //get the zipped file list entry
                ZipEntry ze = zis.getNextEntry();
                lastFileName = null;
                while (ze != null) {
                    String fileName = ze.getName();
                    final File newFile = new File(newLocation, fileName);
                    LOG.info("file unzip : {}", newFile.getAbsoluteFile());
                    unzipSingleEntry(newFile, zis);
                    lastFileName = newFile.getAbsoluteFile().toString();
                    ze = zis.getNextEntry();
                }
                zis.closeEntry();
            }
            if (lastFileName == null) {
                LOG.error("Error decompressing file {} no entries", getFullFilePath().getAbsolutePath());
            }
        } catch (IOException ex) {
            LOG.error("Error decompressing file {}", getFullFilePath().getAbsolutePath(), ex);
        }
    }

    private void unzipSingleEntry(final File newFile, final ZipInputStream zis) throws IOException {
        //create all non exists folders
        //else you will hit FileNotFoundException for compressed folder
        if (!newFile.getParentFile().mkdirs()) {
            LOG.info("mkdirs for {} failed", newFile.getParent());
        }
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            byte[] buffer = new byte[ONE_K];
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }
}
