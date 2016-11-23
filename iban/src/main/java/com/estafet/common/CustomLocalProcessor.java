package com.estafet.common;

import com.estafet.model.IbanSingleReportEntity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.StreamCache;

/**
 * Created by DRamadan on 23-Nov-16.
 */
public class CustomLocalProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        IbanSingleReportEntity entity = exchange.getIn().getBody(IbanSingleReportEntity.class);
        entity.setCurrency("BGN");
        entity.setBalance(100);
        entity.setName("Ivan " + Math.random());
    }
}