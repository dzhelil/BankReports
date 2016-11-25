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
        /*IbanSingleReportEntity entity = exchange.getIn().getBody(IbanSingleReportEntity.class);
        entity.setCurrency("BGN");
        entity.setBalance(100);
        entity.setName("Ivan " + Math.random());*/

        String iban = exchange.getIn().getBody(String.class);

        Account account = accountEnricherService.getAccountByIban(iban);

        exchange.getIn().setBody(account);

    }
}