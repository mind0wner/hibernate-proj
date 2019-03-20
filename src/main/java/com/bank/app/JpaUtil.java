package com.bank.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUtil {

    public static EntityManager createEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("finances");
        return factory.createEntityManager();
    }

    public static EntityTransaction createTransaction() {
        return createEntityManager().getTransaction();
    }

    public static void close() {
        createEntityManager().close();
    }

}
