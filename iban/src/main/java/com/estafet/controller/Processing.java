package com.estafet.controller;

import com.estafet.common.CustomAggregateStrategy;
import com.estafet.common.CustomEnricher;
import com.estafet.common.CustomLocalProcessor;
import com.estafet.common.CustomProcessor;
import com.estafet.model.AccountsWrapper;
import com.estafet.model.IbanSingleReportEntity;
import com.estafet.model.IbanWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;

/**
 * Created by DRamadan on 21-Nov-16.
 */
public class Processing extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        CustomEnricher enricher = new CustomEnricher();
        CustomProcessor processor = new CustomProcessor();
        CustomAggregateStrategy strategy = new CustomAggregateStrategy();
        CustomLocalProcessor localProcessor = new CustomLocalProcessor();

        from("activemq:queue:estafet.iban.report.splitted.queue")
                .routeId("processing")
                .log(LoggingLevel.INFO, "Request Body ACTIVEMQ : \n${body}")
                .process(processor)
                .enrich("direct:enricher", enricher)
                .aggregate(header("IbanTimestampOfRequest"), strategy)
                .completionInterval(2000)
                .marshal().json(JsonLibrary.Jackson)

                .to(ExchangePattern.InOnly, "file://C:/Users/DRamadan/Documents/data/iban/reports/?fileName=${date:now:yyyy MM dd HH_mm_ss}.txt");

        from("direct:enricher").process(localProcessor);
    }
}