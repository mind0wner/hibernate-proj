package com.bank.dao;

import com.bank.dao.interfaces.AccountDao;
import com.bank.entities.Account;
import com.bank.entities.ExchangeRates;
import com.bank.entities.User;

import java.math.BigDecimal;
import java.util.InputMismatchException;

public class AccountDaoImpl extends AbstractDao<Account, Long> implements AccountDao {
    public void topUp(Long id, Double amount, String accountCurrency) {
        Account acc = getEntityManager().getReference(getPersistentClass(), id);
        if (acc.getAccountCurrency().toString().equalsIgnoreCase((accountCurrency))) {
            double current = acc.getCurrentBalance().doubleValue();
            acc.setInitialBalance(new BigDecimal(current));
            double newBalance = acc.getInitialBalance().doubleValue() + amount;
            acc.setCurrentBalance(new BigDecimal(newBalance));
            System.out.println("OK!");
            System.out.println("Current balance = " + acc.getCurrentBalance());
        } else {
            throw new InputMismatchException("Wrong account info");
        }
    }

    public void sumInUah(Long usrId, Long id) {
        Account acc = getEntityManager().getReference(Account.class, id);
        User usr = getEntityManager().getReference(User.class, usrId);

        ExchangeRates er = getEntityManager().getReference(ExchangeRates.class, 1L);
        double usdUah = er.getUsdToUah();
        double eurUah = er.getEurToUah();
        double val;

        if (usr.getAccounts().contains(acc.getAccountId())) {
            if (acc.getAccountCurrency().toString().equalsIgnoreCase("uah")) {
                val = acc.getCurrentBalance().doubleValue();
                System.out.println(val);
            } else if (acc.getAccountCurrency().toString().equalsIgnoreCase("usd")) {
                val = acc.getCurrentBalance().doubleValue() * usdUah;
                System.out.println(val);
            } else if (acc.getAccountCurrency().toString().equalsIgnoreCase("eur")) {
                val = acc.getCurrentBalance().doubleValue() * eurUah;
                System.out.println(val);
            }
        }
    }
}
