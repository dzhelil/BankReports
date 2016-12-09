package com.estafet.processors;

import com.estafet.api.AccountServiceApi;
import com.estafet.pojo.Account;
import com.estafet.pojo.AccountDB;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DRamadan on 07-Dec-16.
 */
public class DBPersistProcessor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(DBPersistProcessor.class);

    private AccountServiceApi accountService;

    /**
     * Processor that persists the passed account object
     * @param exchange
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        AccountDB account = exchange.getIn().getBody(AccountDB.class);

        if (account != null) {
            account.setChanged(false);
            accountService.persistAccount(account);
            logger.info("Done");
        }
    }

    public void setAccountService(AccountServiceApi accountService) {
        this.accountService = accountService;
    }
}
