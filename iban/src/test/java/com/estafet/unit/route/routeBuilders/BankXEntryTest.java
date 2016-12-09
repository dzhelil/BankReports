package com.estafet.unit.route.routeBuilders;

import com.estafet.controller.BankXEntry;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by DRamadan on 02-Dec-16.
 */
public class BankXEntryTest extends CamelTestSupport {

    private static final Logger LOGGER = Logger.getLogger(BankXEntryTest.class.getName());

    @Test
    public void testSendJson() throws Exception {

        RouteDefinition route = context().getRouteDefinition("entry");

        ModelCamelContext mcc = context.adapt(ModelCamelContext.class);
        route.adviceWith(mcc, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:jetty.mock");
                interceptSendToEndpoint("activemq:*")
                        .skipSendToOriginalEndpoint()
                        .to("mock:activemq.mock.test");
            }
        });

        context().startRoute("entry");

        MockEndpoint endpoint = getMockEndpoint("mock:activemq.mock.test", false);
        endpoint.setExpectedCount(3);

        String request = readPayload("entryRouteTest");

        String challenge = "{\n" +
                "            \"ibans\": [\n" +
                "                \"BG66 ESTF 0616 0000 0000 01\",\n" +
                "                \"BG66 ESTF 0616 0000 0000 02\",\n" +
                "                \"BG66 ESTF 0616 0000 0000 03\"\n" +
                "              ]\n" +
                "        }";

        /*{
            "ibans": [
                "BG66 ESTF 0616 0000 0000 01",
                "BG66 ESTF 0616 0000 0000 02",
                "BG66 ESTF 0616 0000 0000 03"
              ]
        }*/

//        template.sendBody("jetty:{{entrypoint.url}}", challenge);
        template.sendBody("direct:jetty.mock", challenge);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        PropertiesComponent prop = context.getComponent("properties", PropertiesComponent.class);
        prop.setLocation("classpath:properties/test.properties");
        return context;
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new BankXEntry();
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    private String readPayload(String name) {
        String content = null;

        URL url = this.getClass().getResource(
                "/json/" + name + ".json");
        try {
            String path = url.getFile();
            path = path.replaceFirst("^/(.:/)", "$1");
            System.out.println("Resources Path : " + path);

            content = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }

        return content;
    }
}