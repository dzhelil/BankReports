package com.estafet.service;


import com.estafet.api.AccountServiceApi;
import com.estafet.dao.Account;


public class AccountServiceImpl implements AccountServiceApi {

    @Override
    public Account getAccountByIban(String iban) {

        Account account = new Account();

        switch (iban) {
            case "BG66 ESTF 0616 0000 0000 01" :
                account.setName("Ivan2 Petrov2");
                account.setCurrency("USD");
                account.setBalance(234);
                account.setIban(iban);
                break;
            case "BG66 ESTF 0616 0000 0000 02" :
                account.setName("Dimitar2 Dimov2");
                account.setCurrency("EUR");
                account.setBalance(543);
                account.setIban(iban);
                break;
            case "BG66 ESTF 0616 0000 0000 03" :
                account.setName("Yane2 Yanev2");
                account.setCurrency("BGN");
                account.setBalance(987);
                account.setIban(iban);
                break;
            default:
                account.setName("Error2 Error2");
                account.setCurrency("Error2");
                account.setBalance(0);
                account.setIban(iban);
        }
        return account;
    }

}
