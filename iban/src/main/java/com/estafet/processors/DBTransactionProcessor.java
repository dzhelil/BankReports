package com.estafet.processors;

import com.estafet.api.AccountServiceApi;
import com.estafet.pojo.AccountTransaction;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by DRamadan on 09-Dec-16.
 */
public class DBTransactionProcessor implements Processor {

    private AccountServiceApi accountService;

    /**
     * @param exchange
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        AccountTransaction transaction = exchange.getIn().getBody(AccountTransaction.class);

        boolean success = false;
        if (transaction != null) {
            success = accountService.transaction(transaction.getIban(), transaction.getAmmount());
        }
    }

    public void setAccountService(AccountServiceApi accountService) {
        this.accountService = accountService;
    }
}
