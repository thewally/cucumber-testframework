package nl.thewally.templates.helpers.servicehelper;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.soap.*;
import java.io.*;
import java.nio.charset.Charset;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.xpath.XPathNamespace;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arjen on 29-11-16.
 */
public class SoapServiceClient extends ServiceClient {
    private static final Logger LOG = LoggerFactory.getLogger(SoapServiceClient.class);

    private MimeHeaders requestHeaders = new MimeHeaders();
    private SOAPMessage request, response;

    private ByteArrayOutputStream baosRequest = new ByteArrayOutputStream();
    private ByteArrayOutputStream baosResponse = new ByteArrayOutputStream();

    private Document doc = null;
    private XPathExpression expr = null;

    public SoapServiceClient(String endpoint) throws Exception {
        super(endpoint);
    }

    public void setRequestHeaders(String name, String value) {
        requestHeaders.addHeader(name, value);
    }

    public void sendSoapRequest(String requestMessage) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        request = factory.createMessage(requestHeaders, new ByteArrayInputStream(requestMessage.getBytes(Charset.forName("UTF-8"))));
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            response = soapConnection.call(request, getEndpoint());
            request.writeTo(baosRequest);
            response.writeTo(baosResponse);
            soapConnection.close();
        } catch (Exception e) {
            LOG.error("Error occurred while sending SOAP Request to Server: \n{}", e);
        }

    }

    public String getAllRequestHeaders() {
        String result = "";
        try {
            Iterator iterator = request.getMimeHeaders().getAllHeaders();
            while (iterator.hasNext()) {
                MimeHeader mimeHeader = (MimeHeader) iterator.next();
                result += mimeHeader.getName() + " = " + mimeHeader.getValue() + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getRequestHeader(String name) {
        String result = "";
        try {
            String[] results = request.getMimeHeaders().getHeader(name);
            result = results[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getSoapRequest() throws Exception {
        String result = "";
        try {
            result = baosRequest.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prettyPrintXml(result);
    }

    public String getAllResponseHeaders() {
        String result = "";
        try {
            Iterator iterator = response.getMimeHeaders().getAllHeaders();
            while (iterator.hasNext()) {
                MimeHeader mimeHeader = (MimeHeader) iterator.next();
                result += mimeHeader.getName() + " = " + mimeHeader.getValue() + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getResponseHeader(String name) {
        String result = "";
        try {
            String[] results = response.getMimeHeaders().getHeader(name);
            result = results[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getSoapResponse() throws Exception {
        String result = "";
        try {
            result = baosResponse.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prettyPrintXml(result);
    }

    public SOAPElement getChildOfSoapBody(String element) throws SOAPException {
        SOAPBody soapBody = response.getSOAPBody();
        Iterator list = soapBody.getChildElements();
        return iterate(list, element);
    }

    public SOAPElement getChildOfElement(SOAPElement element, String childElement) {
        Iterator list = element.getChildElements();
        return iterate(list, childElement);
    }

    public String getValueOfChildElement(SOAPElement element, String childElement) {
        Iterator list = element.getChildElements();
        SOAPElement se = iterate(list, childElement);
        return se.getValue();
    }

    private SOAPElement iterate(Iterator iterator, String element) {
        SOAPElement result = null;
        while (iterator.hasNext()) {
            SOAPElement se = (SOAPElement) iterator.next();
            if (se.getElementName().getLocalName().equals(element)) {
                result = se;
            }
        }
        if (result == null) {
            LOG.error("Element {} is not available.", element);
            Assert.fail();
        }
        return result;
    }

    public String getValueByXpath(String expression) throws Exception {
        String returnVal = null;

        if(doc==null) {
            doc = prepareDocumentForResponse();
        }
        expr = prepareXpathExpression(expression);

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            returnVal = nodes.item(i).getNodeValue();
            LOG.info("Return value: {}", returnVal);
        }
        return returnVal;
    }

    public List<String> getValueArrayByXpath(String expression) throws Exception {
        List<String> returnList = new ArrayList<>();

        if(doc==null) {
            doc = prepareDocumentForResponse();
        }
        expr = prepareXpathExpression(expression);

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            returnList.add(nodes.item(i).getNodeValue());
            LOG.info("Added value '{}' to list", nodes.item(i).getNodeValue());
        }
        return returnList;
    }

    public int getCountOfNodesByXpath(String expression) throws Exception {
        if(doc==null) {
            doc = prepareDocumentForResponse();
        }
        expr = prepareXpathExpression(expression);
        Object result = expr.evaluate(doc, XPathConstants.NUMBER);
        Double count = (Double) result;
        LOG.info("Count of nodes: {}", count.intValue());
        return count.intValue();
    }

    private Document prepareDocumentForResponse() throws Exception {
        InputSource is = new InputSource(new StringReader(new String(baosResponse.toByteArray())));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        doc = db.parse(is);
        return doc;
    }

    private XPathExpression prepareXpathExpression(String expression) throws Exception {
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        expr = xpath.compile(expression);
        return expr;
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
