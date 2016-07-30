/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_DA;

import automatedbillingsoftware.helper.HibernateUtils;
import automatedbillingsoftware_modal.Users;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Arka
 */
public class User_DA {

    public Users addUser(Users users) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        session.saveOrUpdate(users);
        beginTransaction.commit();
        return users;
    }

    public List<Users> fetchAllUser(Users users) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Users where status=:status");
        query.setParameter("status", 1);

        List<Users> list = (List<Users>) query.list();
        //   session.saveOrUpdate(users);
        beginTransaction.commit();
        //  return users;
        return list;
    }

    public Users fetchUserById(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Users where status=:status and userid=:id");
        query.setParameter("status", 1);
        query.setParameter("userid", id);
        List<Users> list = (List<Users>) query.list();
        //   session.saveOrUpdate(users);
        beginTransaction.commit();
        //  return users;
        return (Users) list.get(0);
    }

    public void deleteUser(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Users userId = fetchUserById(id);
        userId.setStatus(0);
        session.update(userId);
        session.flush();
        //   session.saveOrUpdate(users);
        beginTransaction.commit();
        //  return users;
    }

    public void updateUser(Users user) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();

        session.update(user);
        session.flush();
        //   session.saveOrUpdate(users);
        beginTransaction.commit();
        //  return users;
    }
}
