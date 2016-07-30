/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_DA;

import automatedbillingsoftware.helper.HibernateUtils;
import automatedbillingsoftware_modal.Categories;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Arka
 */
public class Categories_DA {

    public Categories addCategories(Categories cat) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        //session.saveOrUpdate(cat);
        session.persist(cat);

        beginTransaction.commit();
        return cat;
    }

    public List<Categories> searchCategories(String searchKeyWord) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createSQLQuery("Select * from categories_tbl  where catName like  '%" + searchKeyWord + "%' OR catDesc like  '%" + searchKeyWord + "%'AND status=1");
        Categories category = new Categories();
        List list = query.list();
        List<Categories> catList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] cat = (Object[]) list.get(i);
            int id = (Integer) cat[0];
            String name = (String) cat[3];
            String description = (String) cat[1];
            Date dt = (Date) cat[2];
            int status = (Integer) cat[5];
            double discount = (Double) cat[4];
            category = new Categories();
            category.setCatDesc(description);
            category.setCatName(name);
            category.setCatModifiedDate(dt);
            category.setDiscount(discount);
            category.setStatus(status);
            category.setCatid(id);
            catList.add(category);

        }

        beginTransaction.commit();
        return catList;
    }

    public List<Categories> fetchCategorieses() {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Categories c where c.status=:status");
        query.setParameter("status", 1);
        List<Categories> catList = (List<Categories>) query.list();
        beginTransaction.commit();
        return catList;
    }

    public Categories fetchCategoryByName(String name) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Categories c where c.status=:status AND c.catName LIKE :name");
        query.setParameter("status", 1);
        query.setParameter("name", name);
        List<Categories> catList = (List<Categories>) query.list();
        beginTransaction.commit();
        if(catList.size()>0)
        return catList.get(0);
        else
            return null;
    }

    public void updateCategory(Categories cat) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        cat.setCatModifiedDate(new Date());
//        System.out.println("update =>" + cat.getCatName());
//        String name=cat.getCatName();
        // cat = (Categories) session.load(Categories.class, cat.getCatid());
        //cat.setCatName(name);
        session.update(cat);
        // session.flush();
        beginTransaction.commit();
    }

    public void deleteCategory(Categories cat) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        cat.setCatModifiedDate(new Date());
        cat.setStatus(0);
        // cat = (Categories) session.load(Categories.class, cat.getCatid());
        //session.delete(cat);
        //session.flush();
        session.update(cat);
        beginTransaction.commit();
    }

    public Categories fetchCategoryById(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Categories c where c.catid =:catid AND c.status=:status");
        query.setParameter("catid", id);
        query.setParameter("status", 1);
        List<Categories> catList = (List<Categories>) query.list();
        Categories cat = new Categories();
        if (catList.size() > 0) {
            cat = catList.get(0);
        }
        beginTransaction.commit();
        return cat;
    }

}
