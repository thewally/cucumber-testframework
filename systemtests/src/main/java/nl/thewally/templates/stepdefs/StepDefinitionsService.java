package nl.thewally.templates.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.thewally.templates.helpers.freemarkerhelper.TemplateHandler;
import nl.thewally.templates.helpers.servicehelper.SoapServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arjen on 30-11-16.
 */
public class StepDefinitionsService {
    private static final Logger LOG = LoggerFactory.getLogger(StepDefinitionsService.class);
    private SoapServiceClient client;

    @Given("^nothing$")
    public void nothing() throws Throwable {
        LOG.info("Begin test!!!");
    }

    @When("^send request$")
    public void sendRequest() throws Throwable {
        TemplateHandler request = new TemplateHandler("requests/request.ftl");
        request.setValue("email", "arjen.vanderwal@gmail.com");
//        request.setValue("licensekey", "123"); # Turn on if needed
        client = new SoapServiceClient("http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx");
        client.sendSoapRequest(request.getOutput());
        LOG.debug("Request: \n{}", client.getSoapRequest());
    }

    @Then("^get response$")
    public void getResponse() throws Throwable {
        LOG.debug("Response: \n{}", client.getSoapResponse());
    }
}
