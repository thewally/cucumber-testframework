package nl.thewally.templates.helpers.freemarkerhelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arjen on 30-11-16.
 */
public class TemplateHandler {
    private static final Logger LOG = LoggerFactory.getLogger(TemplateHandler.class);

    private Configuration cfg = new Configuration();
    private Template template;

    private Map<String, Object> data = new HashMap<String, Object>();

    public TemplateHandler(String template) throws IOException{
        this.template = cfg.getTemplate("/src/test/resources/templates/" + template);
    }

    public void setValue(String variable, String value) {
        data.put(variable, value);
    }

    public String getOutput() throws TemplateException, IOException {
        try {
            Writer result = new StringWriter();
            template.process(data, result);
            return result.toString();
        } catch (IOException e) {
            LOG.error("Cannot generate output: \n{}", e);
        } catch (TemplateException e) {
            LOG.error("Cannot generate template output: \n{}", e);
        }
        return null;
    }

}
