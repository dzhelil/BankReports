package com.estafet.api;


import com.estafet.pojo.Account;
import com.estafet.pojo.AccountDB;

/**
 * Created by DRamadan on 24-Nov-16.
 */
public interface AccountServiceApi {

    public abstract Account getAccountByIban(String iban);

    public abstract void persistAccount(AccountDB account);

    public abstract void mergeAccount(AccountDB account);
}
