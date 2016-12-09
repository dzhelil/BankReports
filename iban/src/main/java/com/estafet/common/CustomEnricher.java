package com.estafet.common;


import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Processor;
import org.apache.camel.processor.Enricher;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

/**
 * Created by DRamadan on 23-Nov-16.
 */
public class CustomEnricher implements AggregationStrategy {

    /**
     * Enriches the message. Will be used in the future
     * @param oldExchange - original message
     * @param newExchange - uodated message
     * @return - enriched message
     */
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        return newExchange;
    }
}
