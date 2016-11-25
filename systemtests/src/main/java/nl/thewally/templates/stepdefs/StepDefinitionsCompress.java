package nl.thewally.templates.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.thewally.templates.helpers.filehelper.CompressedFile;
import nl.thewally.templates.helpers.filehelper.GenericFile;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by arjen on 23-11-16.
 */
public class StepDefinitionsCompress {
    private static final Logger LOG = LoggerFactory.getLogger(StepDefinitionsFileCreator.class);
    private Path relativePath;

    GenericFile newFile;
    CompressedFile compressedFile;

    @Given("^create file with directory (.*) and filename (.*)$")
    public void createFileWithDirectoryAndFilename(String directory, String filename) throws Throwable {
        relativePath = Files.createTempDirectory(new File(System.getProperty("user.home")).toPath(), directory);
        newFile = new GenericFile(relativePath.toString(), filename);
        newFile.createFile();

        if(newFile.isAvailable()) {
            LOG.info("File {} is created", newFile.getFullFilePath());
            Assert.assertTrue(true);
        }
        else {
            Assert.fail("File "+newFile.getFullFilePath()+" is not created.");
        }
    }

    @When("^compress file$")
    public void compressFile() throws Throwable {
        File compressed = newFile.compress();
        compressedFile = new CompressedFile(compressed.getPath(), compressed.getName());
    }

    @Then("^compressed file is available$")
    public void compressedFileIsAvailable() throws Throwable {
        if(compressedFile.isAvailable()) {
            LOG.info("Compressed file {} is created", compressedFile.getFullFilePath());
            Assert.assertTrue(true);
        }
        else {
            Assert.fail("Compressed file "+compressedFile.getFullFilePath()+" is not created.");
        }
    }

    @Given("^select created compressed file with path (.*) and filename (.*)$")
    public void selectCreatedCompressedFileWithPathAndFilenameFile(String directory, String filename) throws Throwable {
        relativePath = Files.createTempDirectory(new File(System.getProperty("user.home")).toPath(), directory);
        compressedFile = new CompressedFile(relativePath.toString(), filename);

        if(compressedFile.isAvailable()) {
            LOG.info("File {} is created", compressedFile.getFullFilePath());
            Assert.assertTrue(true);
        }
        else {
            Assert.fail("File "+compressedFile.getFullFilePath()+" is not created.");
        }
    }

    @When("^decompress file$")
    public void decompressFile() throws Throwable {
        compressedFile.decompress();
    }

    @Then("^decompressed file (.*) is available in directory (.*)$")
    public void decompressedFileIsAvailableInDirectory(String filename, String directory) throws Throwable {
        relativePath = Files.createTempDirectory(new File(System.getProperty("user.home")).toPath(), directory);
        newFile = new GenericFile(relativePath.toString(), filename);
        if(newFile.isAvailable()) {
            LOG.info("File {} is created", newFile.getFullFilePath());
            Assert.assertTrue(true);
        }
        else {
            Assert.fail("File "+newFile.getFullFilePath()+" is not created.");
        }
    }

}
