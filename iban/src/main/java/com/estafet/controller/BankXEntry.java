package com.estafet.controller;

import com.estafet.common.CustomException;
import com.estafet.pojo.IbanWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import javax.servlet.http.HttpServletResponse;


/**
 * Created by DRamadan on 21-Nov-16.
 */
public class BankXEntry extends RouteBuilder {

    /**
     * Entry route which takes json on jetty entrypoint and sends data to active mq
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {

        onException(CustomException.class)
                .log(LoggingLevel.ERROR, "${exception.message} + ' : ' + ${exception.stacktrace}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpServletResponse.SC_INTERNAL_SERVER_ERROR))
                .setBody(simple("Something went wrong."))
                .to("{{activemq.url}}");

        from("jetty:{{entrypoint.url}}")
                .routeId("{{entry.route.id}}")
                .unmarshal().json(JsonLibrary.Jackson, IbanWrapper.class)
                .setHeader("{{header.name}}", simple("${date:now:yyyy MM dd HH_mm_ss_SSS}"))
                .split(simple("${body.getIbans()}"))
                    .to(ExchangePattern.InOnly, "{{activemq.url}}")
                .end();
    }
}
