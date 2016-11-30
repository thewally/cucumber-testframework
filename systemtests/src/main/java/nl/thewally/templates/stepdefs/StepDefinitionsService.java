package nl.thewally.templates.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
        client = new SoapServiceClient("http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx");
        client.sendSoapRequest("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:example=\"http://ws.cdyne.com/\"><SOAP-ENV:Header/><SOAP-ENV:Body><example:VerifyEmail><example:email>mutantninja@gmail.com</example:email><example:LicenseKey>123</example:LicenseKey></example:VerifyEmail></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        LOG.info("Request: \n{}", client.getSoapRequest());
    }

    @Then("^get response$")
    public void getResponse() throws Throwable {
        LOG.info("Response: \n{}", client.getSoapResponse());
    }
}
