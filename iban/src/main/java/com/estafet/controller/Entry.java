package com.estafet.controller;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by DRamadan on 21-Nov-16.
 */
public class Entry extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(Entry.class);

    private static final String ROUTE = "jetty:http://{{asd}}";

    @Override
    public void configure() throws Exception {
        from(ROUTE + "").to("");
    }
}
