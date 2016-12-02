package com.estafet.common;

import com.estafet.pojo.Account;
import com.estafet.pojo.AccountsWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by DRamadan on 22-Nov-16.
 */
public class CustomAggregateStrategy implements AggregationStrategy {

    private static Logger logger = LoggerFactory.getLogger(CustomAggregateStrategy.class);

    @Override
    public Exchange aggregate(Exchange original, Exchange update) {
        Account newBody = update.getIn().getBody(Account.class);
        AccountsWrapper wrapper = new AccountsWrapper();
        ArrayList<Account> list = null;
        if (original == null) {
            list = new ArrayList<Account>();
            list.add(newBody);
            wrapper.setAccounts(list);
            update.getIn().setBody(wrapper);
            return update;
        }
        wrapper = original.getIn().getBody(AccountsWrapper.class);
        List<Account> newList = wrapper.getAccounts();
        newList.add(newBody);
        wrapper.setAccounts(newList);
        original.getIn().setBody(wrapper);
        return original;
    }
}
