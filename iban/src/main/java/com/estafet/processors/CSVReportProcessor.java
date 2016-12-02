package com.estafet.processors;

import com.estafet.common.CustomAggregateStrategy;
import com.estafet.pojo.Account;
import com.estafet.pojo.AccountsWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DRamadan on 30-Nov-16.
 */
public class CSVReportProcessor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(CSVReportProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        AccountsWrapper body = exchange.getIn().getBody(AccountsWrapper.class);

        List<Account> list = body.getAccounts();


        //exchange.getIn().setBody(custom);
    }
}
