package com.estafet.pojo;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Queue;


/**
 * Created by DRamadan on 07-Dec-16.
 */

@Entity
@Table(name = "account")
@NamedQuery(name = "Account.get", query = "select o from AccountDB o where o.iban = :iban")
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

    /**
     * Reads an account based on its IBAN.
     *
     * @param entityManager An EntityManager instance provided.
     * @param iban          The IBAN of interest.
     * @return The corresponding Account object or null if not found.
     */
    public static AccountDB get(EntityManager entityManager, String iban) {
        try {
            Query query = entityManager.createNamedQuery("Account.get");
            query.setParameter("iban", iban);
            return (AccountDB) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

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
