/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_DA;

import automatedbillingsoftware.helper.HibernateUtils;
import automatedbillingsoftware_modal.Challan;
import automatedbillingsoftware_modal.ChallanGenerated;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Arka
 */
public class ChallanDA {

    public Challan addChallan(Challan challan) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        session.persist(challan);
        beginTransaction.commit();

        return challan;
    }

    public ChallanGenerated saveChallanGen(ChallanGenerated challan) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        challan.setStatus(1);
        session.saveOrUpdate(challan);
        beginTransaction.commit();

        return challan;
    }

    public ChallanGenerated fetchChallanGenById(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from ChallanGenerated where status=:status and id=:id");
        query.setParameter("status", 1);
        query.setParameter("id", id);
        List<ChallanGenerated> challanList = (List<ChallanGenerated>) query.list();
        beginTransaction.commit();

        return (challanList == null || challanList.size() == 0) ? null : challanList.get(0);
    }

    public void deleteChallanGen(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        ChallanGenerated cg = fetchChallanGenById(id);
        System.out.println("id=>" + id + "cg=>" + cg);
        if (cg != null) {
            Transaction beginTransaction = session.beginTransaction();
            cg.setStatus(0);
            session.merge(cg);
            session.flush();
            System.out.println("cg=>" + cg.getStatus());
//        challan.setStatus(1);
//        session.persist(challan);
            beginTransaction.commit();
        }

        //     return challan;
    }

    public Challan fetchChallanByDocNo(String docNo) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Challan where docNo =:docNo");
        query.setParameter("docNo", docNo);
        Challan challan = (Challan) query.list().get(0);
        beginTransaction.commit();
        return challan;
    }

    public Challan updateChallan(Challan challan) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        session.update(challan);
        beginTransaction.commit();
        return challan;
    }

    public ChallanGenerated updateChallanGen(ChallanGenerated challan) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        session.update(challan);
        session.flush();
        beginTransaction.commit();

        return challan;
    }

    public List<ChallanGenerated> fetchChallanOrders() {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from ChallanGenerated where status=:status");
        query.setParameter("status", 1);
        List<ChallanGenerated> challanList = (List<ChallanGenerated>) query.list();
        beginTransaction.commit();

        return challanList;
    }

    public Challan fetchChallanById(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Challan where status=:status AND id=:id");
        query.setParameter("status", 1);
        query.setParameter("id", id);
        List<Challan> challanList = (List<Challan>) query.list();
        beginTransaction.commit();

        return challanList == null || challanList.size() <= 0 ? null : challanList.get(0);
    }

    public Challan fetchChallansByDocNameAndDocNo(String docName) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Challan where status=:status AND documentName LIKE :docName");
        query.setParameter("status", 1);
        query.setParameter("docName", docName);

        List<Challan> challanList = (List<Challan>) query.list();
        beginTransaction.commit();

        return challanList == null ? null : challanList.get(0);
    }

    public List<Challan> fetchAllChallanList() {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Challan where status=:status");
        query.setParameter("status", 1);
        List<Challan> challanList = (List<Challan>) query.list();
        beginTransaction.commit();

        return challanList;
    }

    public void deleteChallan(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Challan where status=:status AND id=:id");
        query.setParameter("status", 1);
        query.setParameter("id", id);
        List<Challan> challanList = (List<Challan>) query.list();
        Challan challan = challanList.get(0);
        challan.setStatus(0);
        session.update(challan);
        beginTransaction.commit();
    }
}
