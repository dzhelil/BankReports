package com.estafet.api.old;


import com.estafet.pojo.old.Account;

/**
 * Created by DRamadan on 24-Nov-16.
 */
public interface AccountServiceApi {

    public abstract Account getAccountByIban(String iban);

}
