package nl.thewally.templates.helpers.servicehelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arjen on 29-11-16.
 */
public class ServiceClient {

    private String endpoint;
    private String request, response;
    private Map<String, List<String>> requestHeaders = new HashMap<>();


    public ServiceClient(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setHttpHeader(String name, String values) {
        requestHeaders.put(name, Arrays.asList(values));
    }

    public void sendPostRequest(String requestMessage) {

    }

    public void sendGetRequest() {

    }

    public String getEndpoint() {
        return endpoint;
    }

    public void getHttpHeader() {

    }

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }
}
