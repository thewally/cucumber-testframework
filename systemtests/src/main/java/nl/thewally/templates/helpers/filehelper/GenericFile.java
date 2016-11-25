package nl.thewally.templates.helpers.filehelper;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by arjen on 23-11-16.
 */
public class GenericFile {
    private static final Logger LOG = LoggerFactory.getLogger(GenericFile.class);

    private String filename;
    private Path path;
    private File fullFilePath;

    public GenericFile(Path path, String filename) {
        this.path = path;
        this.filename = filename;
        this.fullFilePath = new File(path + File.separator + filename);
    }

    public void createFile() {
        try {
            if (!fullFilePath.exists()) {
                fullFilePath.createNewFile();
            }
        } catch (IOException ex) {
            LOG.error("Create file failed {}", ex);
        }
    }

    public void setValueOnNewRowInFile(String content) {
        try {
            if (!fullFilePath.exists()) {
                createFile();
            }
            FileWriter fw = new FileWriter(fullFilePath.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException ex) {
            LOG.error("Cannot set value {} for file {}:/n{}", content, fullFilePath.getAbsolutePath(), ex);
        }
    }

    public String getFilename() {
        return filename;
    }

    public Path getPath() {
        return path;
    }

    public File getFullFilePath() {
        return fullFilePath;
    }

    public String getAllContent() {
        //TODO: implement method getAllContent
        return "";
    }

    public String getSpecificContentByRow(int row) {
        //TODO: implement method getSpecificContentByRow
        return "";
    }

    public void removeFile(){
        //TODO: implement method removeFile
    }

    public File compress(){
        return compress(path);
    }

    public File compress(Path compressPath) {
        byte[] buffer = new byte[1024];
        File fullPathToZipFile = new File(compressPath.toString()+File.separator+FilenameUtils.removeExtension(filename) + ".zip");
        try{
            FileOutputStream fos = new FileOutputStream(fullPathToZipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(filename);
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(fullFilePath);

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();
            zos.close();

        } catch (IOException e) {
            LOG.error("Cannot compress file: {}\n{}", path+filename, e);
        }

        return fullPathToZipFile;
    }

    public boolean isAvailable() {
        if (fullFilePath.exists()) {
            LOG.debug("File exists : {}", fullFilePath.getAbsolutePath());
            return true;
        } else {
            LOG.debug("File not found! : {}", fullFilePath.getAbsolutePath());
        }
        return false;
    }
}
