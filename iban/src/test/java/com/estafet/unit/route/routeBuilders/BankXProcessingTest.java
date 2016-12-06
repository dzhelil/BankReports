package com.estafet.unit.route.routeBuilders;

import com.estafet.api.AccountServiceApi;
import com.estafet.common.CustomAggregateStrategy;
import com.estafet.common.CustomEnricher;
import com.estafet.common.Utils;
import com.estafet.controller.BankXProcessing;
import com.estafet.pojo.Account;
import com.estafet.processors.CustomLocalProcessor;
import com.estafet.processors.CustomProcessor;
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

import static org.apache.camel.builder.AdviceWithTasks.replaceFromWith;
import static org.apache.camel.builder.Builder.simple;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by DRamadan on 05-Dec-16.
 */
public class BankXProcessingTest extends CamelTestSupport {

    private static final Logger LOGGER = Logger.getLogger(BankXProcessingTest.class.getName());

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new BankXProcessing();
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

        // Registering beans

        CustomProcessor customProcessor = new CustomProcessor();

        CustomEnricher enricher = new CustomEnricher();
        CustomAggregateStrategy aggregateStrategy = new CustomAggregateStrategy();
        CustomLocalProcessor customLocalProcessor = new CustomLocalProcessor();

        AccountServiceApi accountService = mock(AccountServiceApi.class);

        registry.bind("accoutService", accountService);
        registry.bind("customProcessor", customProcessor);
        registry.bind("customEnricher", enricher);
        registry.bind("aggregation", aggregateStrategy);
        registry.bind("enrichService", customLocalProcessor);

        ((CustomLocalProcessor)registry.lookupByName("enrichService")).setAccountEnricherService((AccountServiceApi) registry.lookup("accoutService"));

        return registry;
    }

    @Test
    public void testProcessing() throws Exception {

        RouteDefinition route = context().getRouteDefinition("processing");
        ModelCamelContext mcc = context.adapt(ModelCamelContext.class);

        route.adviceWith(mcc, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:mock.activemq");
                interceptSendToEndpoint("ftp:*")
                        .skipSendToOriginalEndpoint()
                        .to("mock:ftp.dummy");
            }
        });

        context().start();
        MockEndpoint endpoint = getMockEndpoint("mock:ftp.dummy");
        endpoint.setExpectedCount(1);

        String iban1 = "BG66 ESTF 0616 0000 0000 01";

        Object result = template.sendBodyAndHeader("direct:mock.activemq", ExchangePattern.InOut, iban1,
                "IbanTimestampOfRequest", simple("${date:now:yyyy MM dd HH_mm_ss_SSS}"));

        assertMockEndpointsSatisfied();
    }
}