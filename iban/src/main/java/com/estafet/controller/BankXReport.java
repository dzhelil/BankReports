package com.estafet.controller;

import com.estafet.pojo.Account;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.idempotent.MemoryIdempotentRepository;
import com.estafet.pojo.AccountsWrapper;
import org.apache.camel.spi.DataFormat;


/**
 * Created by DRamadan on 29-Nov-16.
 */
public class BankXReport extends RouteBuilder {

    private DataFormat bindy = new BindyCsvDataFormat(AccountsWrapper.class);

    @Override
    public void configure() throws Exception {

        from("ftp://{{endpoint.output.username}}@{{endpoint.output.host}}?password={{endpoint.output.password}}")
                .routeId("csvReport")
                .filter(header(Exchange.FILE_NAME).endsWith(".txt"))
                .idempotentConsumer(header(Exchange.FILE_NAME), MemoryIdempotentRepository.memoryIdempotentRepository(200))
                .unmarshal().json(JsonLibrary.Jackson, AccountsWrapper.class)
                .process("fileRenameProcessor")
                //.split(simple("${body.getAccounts()}"))
                .setHeader("CamelFileName", simple("${header.CamelFileName}.csv"))
                //.setHeader("CamelFileName", simple("${date:now:yyyy MM dd HH_mm_ss_SSS}.csv"))
                .marshal(bindy)

                .log(LoggingLevel.INFO, "Request Body : \n${body}")

                .to("file://{{csv.store.path}}")
                .end();
    }
}
