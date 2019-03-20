package com.bank.dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface Dao<T, PK extends Serializable> {

    /*void*/T persist(T entity);

    void flush(T entity);

    T findById(PK id);

    void delete(T entity);

    List<T> findAll();

    void deleteAll();

}
