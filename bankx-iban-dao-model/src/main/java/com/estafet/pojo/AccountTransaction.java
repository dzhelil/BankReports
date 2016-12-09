package com.estafet.pojo;

import java.io.Serializable;

/**
 * Created by DRamadan on 09-Dec-16.
 */
public class AccountTransaction implements Serializable {

    private String iban;
    private double ammount;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }
}
