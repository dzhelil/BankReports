package com.estafet.unit.route.routeBuilders;

import com.estafet.common.Utils;
import com.estafet.controller.BankXReport;
import com.estafet.processors.FileRenameProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.apache.camel.builder.Builder.simple;
import static org.mockito.Mockito.mock;

/**
 * Created by DRamadan on 05-Dec-16.
 */
public class BankXReportTest extends CamelTestSupport{

    private static final Logger LOGGER = Logger.getLogger(BankXReportTest.class.getName());

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new BankXReport();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        PropertiesComponent prop = context.getComponent("properties", PropertiesComponent.class);
        prop.setLocation("classpath:properties/test.properties");
        return context;
    }

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry registry = super.createRegistry();

        FileRenameProcessor fileNameProcessor = new FileRenameProcessor();
        registry.bind("fileRenameProcessor", fileNameProcessor);
        return registry;
    }


    @Test
    public void testReports() throws Exception {

        RouteDefinition route = context().getRouteDefinition("csvReport");
        ModelCamelContext mcc = context.adapt(ModelCamelContext.class);

        route.adviceWith(mcc, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:mock.ftp");
                interceptSendToEndpoint("file:*")
                        .skipSendToOriginalEndpoint()
                        .to("mock:file.dummy");
            }
        });

        context().start();
        MockEndpoint endpoint = getMockEndpoint("mock:file.dummy");
        endpoint.setExpectedCount(2);
        Map<String, String> challenges = new LinkedHashMap<>();
        challenges.put("2016 12 05 13_04_54_967.txt", Utils.convertFileToString("C:\\Users\\DRamadan\\Documents\\data\\iban\\reports\\2016 12 05 13_04_54_967.txt"));
        challenges.put("2016 12 05 13_03_51_433.txt", Utils.convertFileToString("C:\\Users\\DRamadan\\Documents\\data\\iban\\reports\\2016 12 05 13_03_51_433.txt"));

        Object result = null;
        for (Map.Entry<String, String> entry : challenges.entrySet()) {

            String value = entry.getValue();
            String key = entry.getKey();

            result = template.sendBodyAndHeader("direct:mock.ftp", ExchangePattern.InOut, value, Exchange.FILE_NAME, key);
        }

        assertMockEndpointsSatisfied();
    }
}