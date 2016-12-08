package nl.thewally.templates.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.thewally.templates.helpers.freemarkerhelper.TemplateHandler;
import nl.thewally.templates.helpers.servicehelper.ServiceClient;
import nl.thewally.templates.helpers.servicehelper.SoapServiceClient;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.SOAPElement;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by arjen on 30-11-16.
 */
public class StepDefinitionsService {
    private static final Logger LOG = LoggerFactory.getLogger(StepDefinitionsService.class);
    private SoapServiceClient client;
    private ServiceClient getRequest;

    @Given("^nothing$")
    public void nothing() throws Throwable {
        LOG.info("Begin test!!!");
    }

    @When("^send request to emailverify service$")
    public void sendRequestToEmailverifyService(Map<String, String> requestItems) throws Throwable {
        TemplateHandler request = new TemplateHandler("requests/request.ftl");
        Iterator iterator = requestItems.keySet().iterator();
        while(iterator.hasNext()) {
            Object key   = iterator.next();
            Object value = requestItems.get(key);
            request.setValue(key.toString(), value.toString());
        }
        client = new SoapServiceClient("http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx");
        client.setRequestHeaders("auth-user", "wala");
        client.sendSoapRequest(request.getOutput());
    }

    @Then("^get request$")
    public void getRequest() throws Throwable {
        LOG.debug("Headers: \n{}", client.getAllRequestHeaders());
        LOG.debug("Request: \n{}", client.getSoapRequest());
    }

    @Then("^get response$")
    public void getResponse() throws Throwable {
        LOG.debug("Headers: \n{}", client.getAllResponseHeaders());
        LOG.debug("Response: \n{}", client.getSoapResponse());
    }

    @Then("^check request headers$")
    public void checkRequestHeaders() throws Throwable {
        if(client.getRequestHeader("auth-user").equals("wala")) {
            Assert.assertTrue(true);
        }
        else {
            Assert.assertTrue(false);
        }
        LOG.debug("Header auth-user from request: {}", client.getRequestHeader("auth-user"));
    }

    @Then("^check response headers$")
    public void checkResponseHeaders() throws Throwable {
        if(client.getResponseHeader("Cache-Control").equals("no-cache")) {
            Assert.assertTrue(true);
        }
        else {
            Assert.assertTrue(false);
        }
        LOG.debug("Header Cache-Control from response: {}", client.getResponseHeader("Cache-Control"));
    }

    @Then("^check response fields$")
    public void checkResponseFields(Map<String, String> responseItems) throws Throwable {
        Iterator iterator = responseItems.keySet().iterator();
        while(iterator.hasNext()){
            Object key   = iterator.next();
            Object value = responseItems.get(key);

            SOAPElement VerifyEmailResponse = client.getChildOfSoapBody("VerifyEmailResponse");
            SOAPElement VerifyEmailResult = client.getChildOfElement(VerifyEmailResponse, "VerifyEmailResult");
            String check = client.getValueOfChildElement(VerifyEmailResult, key.toString());
            if(check.equals(value.toString())){
                LOG.info("Element '{}' with value '{}' is available",key.toString(), value.toString());
                Assert.assertTrue("ResponseText is " + check , true);
            }
            else {
                Assert.assertFalse("ResponseText is " + check , true);
            }
        }
    }


    @When("^send get request$")
    public void sendGetRequest() throws Throwable {
        getRequest = new ServiceClient("http://freegeoip.net/json/github.com");
        getRequest.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7");
        getRequest.sendGetRequest();
    }

    @Then("^get get response$")
    public void getGetResponse() throws Throwable {
//        getRequest.getHttpHeader();
        getRequest.getResponse();
    }

    @When("^send post request$")
    public void sendPostRequest() throws Throwable {
        getRequest = new ServiceClient("http://www.webservicex.net/globalweather.asmx/GetWeather");
        getRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        getRequest.setRequestHeader("Content-Length", "100");
        getRequest.sendPostRequest("CityName=Vlieland&CountryName=Netherlands");
    }

    @Then("^get post response$")
    public void getPostResponse() throws Throwable {
//        getRequest.getHttpHeader();
        getRequest.getRequest();
        getRequest.getResponse();
    }
}
