package com.bank.dao.interfaces;

import com.bank.entities.User;

import java.util.List;

public interface UserDao extends Dao<User, Long> {
    List<User> findByFirstName(String firstName);
}
