package com.estafet.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DRamadan on 21-Nov-16.
 */
public class AccountsWrapper implements Serializable{

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    private List<Account> accounts;

}
