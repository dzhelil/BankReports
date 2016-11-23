package com.estafet.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DRamadan on 21-Nov-16.
 */
public class AccountsWrapper implements Serializable{

    public List<IbanSingleReportEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<IbanSingleReportEntity> accounts) {
        this.accounts = accounts;
    }

    private List<IbanSingleReportEntity> accounts;

}
