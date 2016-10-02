/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misyng.thogakade.dao.impl;

import com.misyng.thogakade.bo.Customer;
import com.misyng.thogakade.dao.CustomerDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yoshan Amarathunga
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private SessionFactory factory;

    @Override
    public boolean addCustomer(Customer customer) {

        Session session = null;
        Transaction tx = null;
        boolean isAdded = false;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(customer);
            tx.commit();
            isAdded = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            isAdded = false;
        } finally {
            session.close();
            return isAdded;
        }
    }

    @Override
    public Customer searchCustomer(int custId) {
        String strQuery = "from Customer where custId=:cid";
        Session session = factory.openSession();
        Query query = session.createQuery(strQuery);
        query.setParameter("cid", custId);
        return (Customer) query.uniqueResult();

    }

    @Override
    public boolean updateCustomer(Customer customer) {

        Session session = null;
        Transaction tx = null;
        boolean isAdded = false;
        String hqlQuery = "update Customer set name = :nm, address = :ad where custId = :id";

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hqlQuery);
            query.setParameter("nm", customer.getName());
            query.setParameter("ad", customer.getAddress());
            query.setParameter("id", customer.getCustId());
            query.executeUpdate();
            tx.commit();
            isAdded = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
            return isAdded;
        }

    }

    @Override
    public boolean deleteCustomer(int custId) {
        
        Session session = null;
        Transaction tx = null;
        
        String hqlQuery = "delete Customer where custId = :id";
        
        try{
           session = factory.openSession();
           tx = session.beginTransaction();
           Query query = session.createQuery(hqlQuery);
           query.setParameter("id", custId);
           query.executeUpdate();
           tx.commit();
           return true;
        }catch(Exception ex){
            tx.rollback();
            return false;
        }finally{
            session.close();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {

        Session session = null;
        String hql = "from Customer p";
        try {            
            session = factory.openSession();
            Query q = session.createQuery(hql);
            return q.list();
        } finally {
            session.close();
        }

    }
}
