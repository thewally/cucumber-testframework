package nl.thewally.templates.helpers.servicehelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arjen on 29-11-16.
 */
public class ServiceClient {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceClient.class);
    private String endpoint;
    private String request, response;
    private Map<String, List<String>> requestHeaders = new HashMap<>();
    private URL obj;
    private HttpURLConnection con;


    public ServiceClient(String endpoint) throws Exception {
        this.endpoint = endpoint;
        obj = new URL(endpoint);
        con = (HttpURLConnection) obj.openConnection();
    }

    public void setHttpHeader(String name, String values) {
        requestHeaders.put(name, Arrays.asList(values));
    }

    public void sendPostRequest(String requestMessage) {

    }

    public void setRequestHeader(String name, String value) {
        con.setRequestProperty(name, value);
    }

    public void sendGetRequest() throws Exception {
        con.setRequestMethod("GET");
        System.out.println("\nSending 'GET' request to URL : " + endpoint);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void getHttpHeader() {
        LOG.info(con.getHeaderField("User-Agent"));

    }

    public String getRequest() {
        return request;
    }

    public void getResponse() throws Exception {
        // Get response code and print it
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        // Read response message
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        LOG.info(response.toString());
    }
}
