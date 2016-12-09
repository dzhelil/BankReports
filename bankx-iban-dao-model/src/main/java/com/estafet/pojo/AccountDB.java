package com.estafet.pojo;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


/**
 * Created by DRamadan on 07-Dec-16.
 */

@Entity
@Table(name = "account")
public class AccountDB {

    @Id
    @Column(name = "iban")
    private String iban;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "changed")
    private Boolean changed;

    public AccountDB() {
        this.changed = false;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getChanged() {
        return changed;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }
}
