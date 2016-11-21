package com.estafet.controller;

import com.estafet.model.IbanSingleReportEntity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by DRamadan on 21-Nov-16.
 */
public class Processing extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("").process(new CustomProcessor());
    }
}

class CustomProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String payload = exchange.getIn().getBody(String.class);

        IbanSingleReportEntity currentIban = new IbanSingleReportEntity();
        currentIban.setIban(payload);

        exchange.getIn().setBody(currentIban);
    }
}