package com.estafet.common;

import com.estafet.api.AccountServiceApi;
import com.estafet.dao.Account;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by DRamadan on 23-Nov-16.
 */
public class CustomLocalProcessor implements Processor {

    public void setAccountEnricherService(AccountServiceApi accountEnricherService) {
        this.accountEnricherService = accountEnricherService;
    }

    private AccountServiceApi accountEnricherService;

    @Override
    public void process(Exchange exchange) throws Exception {

        Account ibanAccount = exchange.getIn().getBody(Account.class);
        Account account = accountEnricherService.getAccountByIban(ibanAccount.getIban());
        exchange.getIn().setBody(account);
    }
}