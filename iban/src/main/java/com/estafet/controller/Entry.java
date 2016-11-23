package com.estafet.controller;

import com.estafet.common.CustomException;
import com.estafet.model.IbanSingleReportEntity;
import com.estafet.model.IbanWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;


/**
 * Created by DRamadan on 21-Nov-16.
 */
public class Entry extends RouteBuilder {

    //private static Logger logger = LoggerFactory.getLogger(Entry.class);

    @Override
    public void configure() throws Exception {

        onException(CustomException.class)
                .log(LoggingLevel.ERROR, "${exception.message} + ' : ' + ${exception.stacktrace}")
                .to("activemq:queue:estafet.iban.report.splitted.queue");

        from("jetty:http://localhost:20616/estafet/iban/report")
                .routeId("entry")
                .log(LoggingLevel.DEBUG, "Route started : ${routeId}\nRequest Body : \n${body}")
                //.validate()
                .unmarshal().json(JsonLibrary.Jackson, IbanWrapper.class)

                .split(simple("${body.getIbans()}"))
                    .setHeader("IbanTimestampOfRequest", simple("${date:now:yyyy MM dd HH_mm_ss}"))
                    .to(ExchangePattern.InOnly, "activemq:queue:estafet.iban.report.splitted.queue")
                    .log(LoggingLevel.INFO, "Incoming message: ${in.body}")
                .end();
    }
}
