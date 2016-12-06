package com.estafet.pojo;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.OneToMany;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DRamadan on 21-Nov-16.
 */

@CsvRecord(separator = ",", crlf="WINDOWS", generateHeaderColumns = true)
public class AccountsWrapper implements Serializable{

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    @OneToMany
    private List<Account> accounts;

}
