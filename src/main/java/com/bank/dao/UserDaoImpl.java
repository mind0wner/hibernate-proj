package com.bank.dao;


import com.bank.dao.interfaces.UserDao;
import com.bank.entities.User;

import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

    public List<User> findByFirstName(String firstName) {
        TypedQuery<User> query = getEntityManager().createQuery("from User u where u.firstName=?1" +
                " order by u.firstName", User.class);
        query.setParameter(1, firstName);
        return query.getResultList();
    }
}
