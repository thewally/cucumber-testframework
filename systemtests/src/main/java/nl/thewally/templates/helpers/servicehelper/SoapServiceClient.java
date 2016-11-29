package nl.thewally.templates.helpers.servicehelper;

import nl.thewally.templates.helpers.filehelper.CompressedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by arjen on 29-11-16.
 */
public class SoapServiceClient extends ServiceClient {
    private static final Logger LOG = LoggerFactory.getLogger(SoapServiceClient.class);

    private SOAPMessage request, response;

    public SoapServiceClient(String endpoint) {
        super(endpoint);
    }

    public void sendSoapRequest(String requestMessage) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        request = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(requestMessage.getBytes(Charset.forName("UTF-8"))));

        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            response = soapConnection.call(request, getEndpoint());

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }

    }

    public SOAPMessage getSoapRequest() throws Exception{
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = request.getSOAPPart().getContent();
        System.out.print("\nRequest SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
        return request;
    }

    public SOAPMessage getSoapResponse() throws Exception{
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = response.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
        return response;
    }

    public String getValueOfResponseItem(XPath xpath) {
        return "";
    }
}
