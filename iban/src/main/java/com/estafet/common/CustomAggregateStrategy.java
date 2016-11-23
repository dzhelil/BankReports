package com.estafet.common;

import com.estafet.model.AccountsWrapper;
import com.estafet.model.IbanSingleReportEntity;
import com.estafet.model.IbanWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.accessibility.AccessibleAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DRamadan on 22-Nov-16.
 */
public class CustomAggregateStrategy implements AggregationStrategy {

    private static Logger logger = LoggerFactory.getLogger(CustomAggregateStrategy.class);

    @Override
    public Exchange aggregate(Exchange original, Exchange update) {
        IbanSingleReportEntity newBody = update.getIn().getBody(IbanSingleReportEntity.class);
        AccountsWrapper wrapper = new AccountsWrapper();
        ArrayList<IbanSingleReportEntity> list = null;
        if (original == null) {
            list = new ArrayList<IbanSingleReportEntity>();
            list.add(newBody);
            wrapper.setAccounts(list);
            update.getIn().setBody(wrapper);
            return update;
        }
        wrapper = original.getIn().getBody(AccountsWrapper.class);
        List<IbanSingleReportEntity> newList = wrapper.getAccounts();
        newList.add(newBody);
        wrapper.setAccounts(newList);
        original.getIn().setBody(wrapper);
        return original;
    }
}
