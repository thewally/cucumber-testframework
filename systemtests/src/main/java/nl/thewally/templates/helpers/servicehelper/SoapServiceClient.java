package nl.thewally.templates.helpers.servicehelper;

import nl.thewally.templates.helpers.filehelper.CompressedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.*;
import javax.xml.xpath.XPath;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;


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
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            response = soapConnection.call(request, getEndpoint());
            soapConnection.close();
        } catch (Exception e) {
            LOG.error("Error occurred while sending SOAP Request to Server: \n{}", e);
        }

    }

    public String getSoapRequest() throws Exception{
        ByteArrayOutputStream baos = null;
        String result = "";
        try {
            baos = new ByteArrayOutputStream();
            request.writeTo(baos);
            result = baos.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return prettyPrintXml(result);
    }

    public String getSoapResponse() throws Exception{
        ByteArrayOutputStream baos = null;
        String result = "";
        try {
            baos = new ByteArrayOutputStream();
            response.writeTo(baos);
            result = baos.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return prettyPrintXml(result);
    }

    public String getValueOfResponseItem(XPath xpath) {
        return "";
    }

    private String prettyPrintXml(String xml) {
        try {
            final InputSource src = new InputSource(new StringReader(xml));
            final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            final Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);

            return writer.writeToString(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
