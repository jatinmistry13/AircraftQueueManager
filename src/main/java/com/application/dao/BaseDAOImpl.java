package com.application.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class BaseDAOImpl<T> extends HibernateDaoSupport implements IBaseDAO<T> {


    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public BaseDAOImpl() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        this.clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];    
    }

    public BaseDAOImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void delete(T entity) {
        try {
            this.getHibernateTemplate().delete(entity);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteAll() {
        String queryString = "delete from " + getPersistentClass().getSimpleName();
        this.getHibernateTemplate().bulkUpdate(queryString);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        List<T> all = (List<T>)this.getHibernateTemplate().find("from " + getPersistentClass().getName());
        return all;
    }

    @Override
    public List<T> findAllByQuery(String Query) {
        @SuppressWarnings("unchecked")
        List<T> all = (List<T>)this.getHibernateTemplate().find(Query);
        return all;
    }

    @Override
    public List<T> findByExample(T entity) {
        List<T> ex = this.getHibernateTemplate().findByExample(entity);
        return ex;
    }

    @Override
    public T findById(Serializable id) {
        T entity = this.getHibernateTemplate().get(getPersistentClass(), id);
        return entity;
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value) {
        String[] propertyNames = new String[]{propertyName};
        Object[] values = new Object[]{value};
        List<T> list = findByProperties(propertyNames, values);
        return list;
    }

    @Override
    public T findFirstByProperty(String propertyName, Object value) {
        String[] propertyNames = new String[]{propertyName};
        Object[] values = new Object[]{value};
        return findFirstByProperties(propertyNames, values);
    }

    @Override
    public List<T> findByProperties(String[] propertyNames, Object[] values) {
        return findByProperties(propertyNames, values, 0);
    }

    @Override
    public T findFirstByProperties(String[] propertyNames, Object[] values) {
        List<T> list = findByProperties(propertyNames, values, 1);
        return list.isEmpty() ? null : list.get(0);
    }


    private List<T> findByProperties(String[] propertyNames, Object[] values, int maxResults) {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("from " + getPersistentClass().getName());
        strBuffer.append(" as model where ");
        for (int i = 0; i < propertyNames.length; i++) {
            if (i != 0)
                strBuffer.append(" and");
            strBuffer.append(" model.");
            strBuffer.append(propertyNames[i]);
            strBuffer.append("=");
            strBuffer.append("? ");
        }
        String queryString = strBuffer.toString();
        HibernateTemplate template = this.getHibernateTemplate();
        //XXX set the max results value later
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) template.find(queryString, values);
        return list;
    }

    @Override
    public Class<T> getPersistentClass() {
        return this.clazz;
    }

    @Override
    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
        this.getHibernateTemplate().flush();
    }

    @Override
    public void saveOrUpdate(T entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
        this.getHibernateTemplate().flush();
    }

    @Override
    public void merge(T entity) {
        this.getHibernateTemplate().merge(entity);
        this.getHibernateTemplate().flush();
    }

    @Override
    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
        this.getHibernateTemplate().flush();
    }
}

