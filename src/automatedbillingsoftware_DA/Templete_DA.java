/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_DA;

import automatedbillingsoftware.helper.HibernateUtils;
import automatedbillingsoftware_modal.Templete;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Arka
 */
public class Templete_DA {

    public Templete addTemplete(Templete templete) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        session.persist(templete);
        beginTransaction.commit();
        session.close();
        return templete;
    }

    public Templete fetchTempleteByName(String tempName) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Templete where status=:status and name LIKE:name");
        query.setParameter("status", 1);
        query.setParameter("name", tempName);
        List<Templete> templeteList = (List<Templete>) query.list();
        beginTransaction.commit();
        session.close();

        return templeteList != null && templeteList.size() > 0 ? templeteList.get(0) : null;
    }
}
