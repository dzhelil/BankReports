package com.estafet.common;

import com.estafet.model.IbanSingleReportEntity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DRamadan on 23-Nov-16.
 */
public class CustomProcessor implements Processor{
    private static Logger logger = LoggerFactory.getLogger(CustomProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        String payload = exchange.getIn().getBody(String.class);
        IbanSingleReportEntity currentIban = new IbanSingleReportEntity();
        currentIban.setIban(payload);
        exchange.getIn().setBody(currentIban);
    }
}
