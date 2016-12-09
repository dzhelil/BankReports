package com.estafet.controller;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by DRamadan on 01-Dec-16.
 */
public class BankXSchedule extends RouteBuilder {
    /**
     * Schedule route which triggers cron job on every minute. Will be updated
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        from("quartz2://dummy/schedule?cron={{endpoint.dummySchedule.cron}}&fireNow=true").routeId("dummySchedule")
                .log(LoggingLevel.INFO, "Cron route executed.");
    }
}
