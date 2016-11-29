package com.estafet.controller;

import com.estafet.common.CustomAggregateStrategy;
import com.estafet.common.CustomEnricher;
import com.estafet.common.CustomLocalProcessor;
import com.estafet.common.CustomProcessor;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.Registry;

/**
 * Created by DRamadan on 21-Nov-16.
 */
public class Processing extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        Registry registry = this.getContext().getRegistry();
        CustomEnricher customEnricher = (CustomEnricher) registry.lookupByName("customEnricher");
        CustomAggregateStrategy strategy = (CustomAggregateStrategy) registry.lookupByName("aggregation");

        from("{{activemq.url}}")
                .routeId("{{processing.route.id}}")
                .log(LoggingLevel.INFO, "Request Body ACTIVEMQ : \n${body}")
                .process("customProcessor")
                .enrich("{{enricher.url}}", customEnricher)
                .aggregate(header("IbanTimestampOfRequest"), strategy)
                .completionInterval(5000)
                .marshal().json(JsonLibrary.Jackson, true)
                .setHeader("CamelFileName", simple("${header.IbanTimestampOfRequest}.txt"))
                .to("ftp://{{endpoint.output.username}}@{{endpoint.output.host}}?password={{endpoint.output.password}}");
                //.to(ExchangePattern.InOnly, "{{file.store.path}}?fileName=${header.IbanTimestampOfRequest}.txt");

        from("{{enricher.url}}").process("enrichService");
    }
}