/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_DA;

import automatedbillingsoftware.helper.HibernateUtils;
import automatedbillingsoftware_modal.Tax;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Arka
 */
public class TaxDA {

    public Tax addTax(Tax tax) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        session.persist(tax);
        beginTransaction.commit();
        return tax;
    }

    public List<Tax> fetchAllTaxList() {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Tax t where t.status=:status");
        query.setParameter("status", 1);
        List<Tax> list = (List<Tax>) query.list();
        beginTransaction.commit();
        return list;
    }

    public Tax fetchTaxByName(String name) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Tax t where t.taxName LIKE :taxName AND t.status=:status");
        query.setParameter("status", 1);
        query.setParameter("taxName", name);
        Tax tax = (Tax) query.list().get(0);
        beginTransaction.commit();
        return tax;
    }

    public Tax fetchTaxById(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Tax t where t.taxId=:taxId AND t.status=:status");
        query.setParameter("status", 1);
        query.setParameter("taxId", id);
        Tax tax = (Tax) query.list().get(0);
        beginTransaction.commit();
        return tax;
    }

    public void deleteTax(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Tax tax = (Tax) session.load(Tax.class, id);
        tax.setStatus(0);
        session.update(tax);
        beginTransaction.commit();
    }

    public void updateTax(Tax tax) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        session.update(tax);
        beginTransaction.commit();
    }

}
