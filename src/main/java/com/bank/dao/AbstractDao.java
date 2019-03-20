package com.bank.dao;

import com.bank.app.JpaUtil;
import com.bank.dao.interfaces.Dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDao<T, PK extends Serializable> implements Dao<T, PK> {

    private Class<T> persistentClass;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @SuppressWarnings(value = "unchecked")
    public AbstractDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public EntityTransaction getTransaction() {
        if (transaction == null) {
            transaction = getEntityManager().getTransaction();
        }
        return this.transaction;
    }

    protected EntityManager getEntityManager() {
        if (this.entityManager == null) {
            this.entityManager = JpaUtil.createEntityManager();
        }
        return this.entityManager;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public T persist(T entity) {
        this.getEntityManager().merge(entity);
        return entity;
    }

    public void flush(T entity) {
        this.getEntityManager().flush();
    }

    @SuppressWarnings("unchecked")
    public T findById(PK id) {
        return getEntityManager().getReference(this.getPersistentClass(), id);
    }

    public void delete(T entity) {
        this.getEntityManager().remove(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(this.getPersistentClass());
        Root<T> root = criteriaQuery.from(this.getPersistentClass());
        criteriaQuery.select(root);
        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
        return query.getResultList();
    }

    public void deleteAll() {
        this.getEntityManager().clear();
    }
}
