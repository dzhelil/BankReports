package com.estafet.controller;

import com.estafet.common.CustomAggregateStrategy;
import com.estafet.common.CustomEnricher;
import com.estafet.common.CustomProcessor;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * Created by DRamadan on 21-Nov-16.
 */
public class Processing extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        CustomEnricher enricher = new CustomEnricher();
        CustomProcessor processor = new CustomProcessor();
        CustomAggregateStrategy strategy = new CustomAggregateStrategy();
       // CustomLocalProcessor localProcessor = new CustomLocalProcessor();

        from("activemq:queue:estafet.iban.report.splitted.queue")
                .routeId("processing")
                .log(LoggingLevel.INFO, "Request Body ACTIVEMQ : \n${body}")
                .process(processor)
                .enrich("direct:enricher", enricher)
                .aggregate(header("IbanTimestampOfRequest"), strategy)
                .completionInterval(5000)
                .marshal().json(JsonLibrary.Jackson, true)
                .to(ExchangePattern.InOnly, "file://C:/Users/DRamadan/Documents/data/iban/reports/?fileName=${date:now:yyyy MM dd HH_mm_ss_SSS}.txt");

        from("direct:enricher").process("enrichService");
    }
}