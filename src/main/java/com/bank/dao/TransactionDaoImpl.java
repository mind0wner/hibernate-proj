package com.bank.dao;

import com.bank.dao.interfaces.TransactionDao;
import com.bank.entities.Account;
import com.bank.entities.ExchangeRates;
import com.bank.entities.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.InputMismatchException;

public class TransactionDaoImpl extends AbstractDao<Transaction, Long> implements TransactionDao {
    public void transfer(Long firstId, Long secondId, Double amount) {
        Account sender = getEntityManager().getReference(Account.class, firstId);
        Account recipient = getEntityManager().getReference(Account.class, secondId);
        double senderBalance = sender.getCurrentBalance().doubleValue();
        double recipientBalance = recipient.getCurrentBalance().doubleValue();
        if (senderBalance >= amount) {
            sender.setInitialBalance(sender.getCurrentBalance());
            sender.setCurrentBalance(new BigDecimal(senderBalance - amount));

            recipient.setInitialBalance(recipient.getCurrentBalance());
            recipient.setCurrentBalance(new BigDecimal(recipientBalance + amount));
            Transaction transaction = new Transaction();

            transaction.setAmount(new BigDecimal(amount));
            transaction.setTransactionType("Perevod");
            transaction.setCreatedDate(new Date());
            double initBalS = sender.getInitialBalance().doubleValue();
            transaction.setInitialBalance(new BigDecimal(initBalS));
            double clBalS = sender.getCurrentBalance().doubleValue();
            transaction.setClosingBalance(new BigDecimal(clBalS));
            transaction.setAccount(sender);


            double clBalR = recipient.getCurrentBalance().doubleValue();
            transaction.setClosingBalance(new BigDecimal(clBalR));
            double initBalR = recipient.getInitialBalance().doubleValue();
            transaction.setInitialBalance(new BigDecimal(initBalR));
            transaction.setAccount(recipient);
            getEntityManager().persist(transaction);
            System.out.println("First acc balance = " + sender.getCurrentBalance());
            System.out.println("Second acc balance = " + recipient.getCurrentBalance());
        } else {
            throw new InputMismatchException("Not enough money");
        }
    }

    public void convert(Long firstId, Long secondId, Double amount) {
        Account first = getEntityManager().getReference(Account.class, firstId);
        Account second = getEntityManager().getReference(Account.class, secondId);
        String firstCurr = first.getAccountCurrency().toString();
        String secondCurr = second.getAccountCurrency().toString();
        double senderBalance = first.getCurrentBalance().doubleValue();
        double recipientBalance = second.getCurrentBalance().doubleValue();
        ExchangeRates er = getEntityManager().getReference(ExchangeRates.class, 1L);
        double usdUah = er.getUsdToUah();
        double eurUah = er.getEurToUah();
        double usdEur = er.getUsdToEuro();
        if (first.getAccountId() == second.getAccountId()) {
            if (senderBalance >= amount) {
                if (firstCurr.equalsIgnoreCase("uah") && secondCurr.equalsIgnoreCase("usd")) {
                    setBalance(firstId, amount);

                    second.setInitialBalance(second.getCurrentBalance());
                    second.setCurrentBalance(new BigDecimal(recipientBalance + amount * usdUah));
                } else if (firstCurr.equalsIgnoreCase("usd") && secondCurr.equalsIgnoreCase("uah")) {
                    setBalance(firstId, amount);

                    second.setInitialBalance(second.getCurrentBalance());
                    second.setCurrentBalance(new BigDecimal(recipientBalance + amount / usdUah));
                } else if (firstCurr.equalsIgnoreCase("uah") && secondCurr.equalsIgnoreCase("eur")) {
                    setBalance(firstId, amount);

                    second.setInitialBalance(second.getCurrentBalance());
                    second.setCurrentBalance(new BigDecimal(recipientBalance + amount * eurUah));
                } else if (firstCurr.equalsIgnoreCase("eur") && secondCurr.equalsIgnoreCase("uah")) {
                    setBalance(firstId, amount);


                    second.setInitialBalance(second.getCurrentBalance());
                    second.setCurrentBalance(new BigDecimal(recipientBalance + amount / eurUah));
                } else if (firstCurr.equalsIgnoreCase("usd") && secondCurr.equalsIgnoreCase("eur")) {
                    setBalance(firstId, amount);

                    second.setInitialBalance(second.getCurrentBalance());
                    second.setCurrentBalance(new BigDecimal(recipientBalance + amount * usdEur));
                } else if (firstCurr.equalsIgnoreCase("eur") && secondCurr.equalsIgnoreCase("usd")) {
                    setBalance(firstId, amount);


                    second.setInitialBalance(second.getCurrentBalance());
                    second.setCurrentBalance(new BigDecimal(recipientBalance + amount / usdEur));
                } else if (firstCurr.equalsIgnoreCase(secondCurr)) {
                    setBalance(firstId, amount);

                    second.setInitialBalance(second.getCurrentBalance());
                    second.setCurrentBalance(new BigDecimal(recipientBalance + amount));
                }
                System.out.println("First acc balance = " + first.getCurrentBalance());
                System.out.println("Second acc balance = " + second.getCurrentBalance());
            } else {
                throw new InputMismatchException("Not enough money");
            }
        }
    }

    private void setBalance(Long firstId, double amount) {
        Account first = getEntityManager().getReference(Account.class, firstId);
        double senderBalance = first.getCurrentBalance().doubleValue();
        first.setInitialBalance(first.getCurrentBalance());
        first.setCurrentBalance(new BigDecimal(senderBalance - amount));
    }
}
