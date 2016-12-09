package com.estafet.postgres;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;

import java.util.logging.Logger;

/**
 * Created by DRamadan on 07-Dec-16.
 */
public class DBTest extends CamelTestSupport {
    private static final Logger LOGGER = Logger.getLogger(DBTest.class.getName());

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
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

        return registry;
    }

}
