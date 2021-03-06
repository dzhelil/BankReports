package com.estafet.api;


import com.estafet.pojo.Account;
import com.estafet.pojo.AccountDB;

/**
 * Created by DRamadan on 24-Nov-16.
 */
public interface AccountServiceApi {

    public abstract Account getAccountByIban(String iban);

    public abstract void persistAccount(AccountDB account) throws  Exception;

    public abstract void mergeAccount(AccountDB account);

    public boolean transaction(String iban, double ammount);
}
