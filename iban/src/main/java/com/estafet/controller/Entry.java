package com.estafet.controller;

import com.estafet.common.CustomException;
import com.estafet.model.IbanWrapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;


/**
 * Created by DRamadan on 21-Nov-16.
 */
public class Entry extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(Entry.class);

    private static final String ROUTE = "jetty:http://";

    @Override
    public void configure() throws Exception {

        onException(CustomException.class)
                .to("activemq:queue:estafet.iban.report.splitted.queue");
        from("jetty:http://localhost:20616/estafet/iban/report")
                .unmarshal().json(JsonLibrary.Jackson, IbanWrapper.class)
                .split(simple("${body.getIbans()}"))
                .setHeader("IbanTimestampOfRequest")
                .method(new Timestamp(System.currentTimeMillis()))
                .to("activemq:queue:estafet.iban.report.splitted.queue");
    }
}
