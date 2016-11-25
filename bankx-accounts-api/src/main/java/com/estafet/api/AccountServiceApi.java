package com.estafet.api;


import com.estafet.dao.Account;

/**
 * Created by DRamadan on 24-Nov-16.
 */
public interface AccountServiceApi {

    public abstract Account getAccountByIban(String iban);

}
