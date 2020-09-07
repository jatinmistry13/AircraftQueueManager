package com.application.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

public interface IBaseDAO<T> {
    public abstract void delete(T entity); 
    public abstract void deleteAll();
    
    public abstract List<T> findAll();  
    public abstract List<T> findAllByQuery(String Query);
    public abstract List<T> findByExample(T example);  
    public abstract T findById(Serializable id);  
    
    public abstract List<T> findByProperty(String propertyName, Object value);  
    public abstract T findFirstByProperty(String propertyName, Object value);  
    public abstract List<T> findByProperties(String[] propertyNames,Object[] values);
    public abstract T findFirstByProperties(String[] propertyNames,Object[] values); 
    
    public abstract Class<T> getPersistentClass();
    
    public abstract void save(T entity);  
    public abstract void saveOrUpdate(T entity); 
    public abstract void merge(T entity); 
    
    public abstract void update(T entity);
    public void setSessionFactory(SessionFactory sessionFactory);
}