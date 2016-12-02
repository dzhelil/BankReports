package com.estafet.pojo;



import org.apache.camel.dataformat.bindy.annotation.*;

import java.io.Serializable;

/**
 * Created by DRamadan on 21-Nov-16.
 */

@CsvRecord(separator = ",", crlf="WINDOWS")
public class Account implements Serializable{

    @DataField(name = "IBAN", pos = 1)
    private String iban;

    @DataField(name = "Name", pos = 2)
    private String name;

    @DataField(name = "Balance", pos = 3)
    private double balance;

    @DataField(name = "Currency", pos = 4)
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
