package com.estafet.service.old;


import com.estafet.api.old.AccountServiceApi;
import com.estafet.pojo.old.Account;

/**
 * Created by DRamadan on 24-Nov-16.
 */
public class AccountServiceImpl implements AccountServiceApi {

    @Override
    public Account getAccountByIban(String iban) {

        Account account = new Account();

        switch (iban) {
            case "BG66 ESTF 0616 0000 0000 01" :
                account.setName("Ivan Petrov");
                account.setCurrency("USD");
                account.setBalance(234);
                account.setIban(iban);
                break;
            case "BG66 ESTF 0616 0000 0000 02" :
                account.setName("Dimitar Dimov");
                account.setCurrency("EUR");
                account.setBalance(543);
                account.setIban(iban);
                break;
            case "BG66 ESTF 0616 0000 0000 03" :
                account.setName("Yane Yanev");
                account.setCurrency("BGN");
                account.setBalance(987);
                account.setIban(iban);
                break;
            default:
                account.setName("Error Error");
                account.setCurrency("Error");
                account.setBalance(0);
                account.setIban(iban);
        }
        return account;
    }

}
