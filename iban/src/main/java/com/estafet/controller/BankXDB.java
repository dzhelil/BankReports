package com.estafet.controller;


import com.estafet.pojo.AccountDB;
import com.estafet.pojo.AccountTransaction;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by DRamadan on 07-Dec-16.
 */
public class BankXDB extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        persistAccount();
        accountTransactions();
    }

    private void persistAccount() {
        from("jetty:{{endpoint.db.baseUrl}}/insertAccounts?httpMethodRestrict=POST&continuationTimeout=5000").routeId("routeJettyInsertAccounts")
                .onException(Exception.class)
                .handled(true)
                .transform(constant("Something went wrong.")).log(LoggingLevel.INFO, "${exception.message}\\n${exception.stacktrace}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpServletResponse.SC_INTERNAL_SERVER_ERROR))
                .end()
                .unmarshal().json(JsonLibrary.Jackson, AccountDB.class)
                .split(simple("${body}"))
                .log(LoggingLevel.INFO, "Request Body : \\n${body}")
                .process("dbPersistProcessor");
    }

    private void accountTransactions() {
        from("jetty:{{endpoint.db.baseUrl}}/updateAccount?httpMethodRestrict=POST&continuationTimeout=5000").routeId("routeJettyUpdateAccounts")
                .onException(Exception.class)
                    .handled(true)
                    .transform(constant("Something went wrong.")).log(LoggingLevel.INFO, "${exception.message}\\n${exception.stacktrace}")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpServletResponse.SC_INTERNAL_SERVER_ERROR))
                .end()
                .unmarshal().json(JsonLibrary.Jackson, AccountTransaction.class)
                    .split(simple("${body}"))
                    .log(LoggingLevel.INFO, "Request Body : \\n${body}")
                    .process("dbTransactionProcessor");
    }
}
