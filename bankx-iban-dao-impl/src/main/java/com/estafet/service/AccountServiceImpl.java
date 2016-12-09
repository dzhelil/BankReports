package com.estafet.service;


import com.estafet.api.AccountServiceApi;
import com.estafet.pojo.Account;
import com.estafet.pojo.AccountDB;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class AccountServiceImpl implements AccountServiceApi {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * EntityManager setter for the blueprint mapping with jpa context
     *
     * @param entityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Account getAccountByIban(String iban) {

        Account account = new Account();

        switch (iban) {
            case "BG66 ESTF 0616 0000 0000 01":
                account.setName("Ivan2 Petrov2");
                account.setCurrency("USD");
                account.setBalance(234);
                account.setIban(iban);
                break;
            case "BG66 ESTF 0616 0000 0000 02":
                account.setName("Dimitar2 Dimov2");
                account.setCurrency("EUR");
                account.setBalance(543);
                account.setIban(iban);
                break;
            case "BG66 ESTF 0616 0000 0000 03":
                account.setName("Yane2 Yanev2");
                account.setCurrency("BGN");
                account.setBalance(987);
                account.setIban(iban);
                break;
            default:
                account.setName("Error2 Error2");
                account.setCurrency("Error2");
                account.setBalance(0);
                account.setIban(iban);
        }
        return account;
    }

    /**
     * Persists new account
     *
     * @param account
     */
    @Override
    public void persistAccount(AccountDB account) throws Exception {
        entityManager.persist(account);
    }

    /**
     * Merges account data
     *
     * @param account
     */
    @Override
    public void mergeAccount(AccountDB account) {
        entityManager.merge(account);
    }

    /**
     * @param iban
     * @param ammount
     * @return
     */
    @Override
    public boolean transaction(String iban, double ammount) {
        AccountDB found = AccountDB.get(entityManager, iban);
        if (found != null) {
            Double currentBalance = found.getBalance();
            if (currentBalance >= 0 && (currentBalance + ammount) >= 0) {
                found.setBalance(currentBalance + ammount);
            }
            found.setChanged(true);
            entityManager.merge(found);
            return true;
        }
        return false;
    }

}
