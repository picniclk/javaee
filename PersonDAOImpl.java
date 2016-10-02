/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movie.dao;

import com.movie.model.CastCrew;
import com.movie.model.Person;
import java.util.List;
import java.util.Locale;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gaa
 */
@Repository
public class PersonDAOImpl implements PersonDAO {

    @Autowired
    private SessionFactory factory;
    
    @Override
    public List<Person> getLatestPersons() {
        String strQuery = "from Person order by personId desc";
        Query query = factory.getCurrentSession().createQuery(strQuery);
        query.setMaxResults(50);
        return query.list();
    }

    @Override
    public Person findPersonDetails(String seoName) {
        String strQuery = "from Person where seoName=:pseo";
        Session session = factory.getCurrentSession();
        Query query = session.createQuery(strQuery);
        query.setParameter("pseo", seoName);
        return (Person) query.uniqueResult();
    }

    @Override
    public List<CastCrew> getPersonCastCrewDetails(Person person) {
        String strQuery = "from CastCrew where personId=:pid";
        Session session = factory.getCurrentSession();
        Query query = session.createQuery(strQuery);
        query.setParameter("pid", person);
        return query.list();
    }

    @Override
    public List<Person> searchPerson(String term, int pageSize, int pageNumber) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("from Person where firstName like :fname or lastName like :lname or secondNames like :sname");
        query.setParameter("fname", "%"+term+"%");
        query.setParameter("lname", "%"+term+"%");
        query.setParameter("sname", "%"+term+"%");
        query.setFirstResult(pageSize * (pageNumber - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }
    
}