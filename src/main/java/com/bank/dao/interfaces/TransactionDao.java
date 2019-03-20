package com.bank.dao.interfaces;

import com.bank.entities.Transaction;

public interface TransactionDao extends Dao<Transaction, Long> {
    void transfer(Long firstId, Long secondId, Double amount);

    void convert(Long firstId, Long secondId, Double amount);
}
