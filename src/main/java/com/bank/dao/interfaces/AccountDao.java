package com.bank.dao.interfaces;

import com.bank.entities.Account;

public interface AccountDao extends Dao<Account, Long> {
    void topUp(Long id, Double amount, String accountCurrency);

    void sumInUah(Long usrId, Long id);
}
