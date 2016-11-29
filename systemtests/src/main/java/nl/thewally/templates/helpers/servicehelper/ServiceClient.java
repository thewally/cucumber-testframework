package nl.thewally.templates.helpers.servicehelper;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by arjen on 29-11-16.
 */
public class ServiceClient {

    private String endpoint;
    private String request, response;


    public ServiceClient(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setHttpHeader(String name, String value) {

    }

    public void sendPostRequest(String requestMessage) {

    }

    public void sendGetRequest() {

    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }
}
