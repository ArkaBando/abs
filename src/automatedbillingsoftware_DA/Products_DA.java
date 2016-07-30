/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_DA;

import automatedbillingsoftware.helper.HibernateUtils;
import automatedbillingsoftware_modal.Categories;
import automatedbillingsoftware_modal.Products;
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
public class Products_DA {

    public Products addProducts(Products prod) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        session.saveOrUpdate(prod);
        beginTransaction.commit();
        return prod;
    }

    public void deleteProducts(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Products p where p.status=:status AND p.prodid=:id");
        query.setParameter("status", 1);
        query.setParameter("id", id);
        List<Products> list = (List<Products>) query.list();
        Products get = list.get(0);
        get.setStatus(0);
        session.update(get);
        beginTransaction.commit();

    }

    public void updateProduct(Products prod) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        session.update(prod);

        beginTransaction.commit();

    }

    public Products fetchProductById(int id) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Products p where p.status=:status AND p.prodid=:id");
        query.setParameter("status", 1);
        query.setParameter("id", id);
        List<Products> list = (List<Products>) query.list();
        Products get = list.get(0);

        beginTransaction.commit();
        return get;
    }

    public List<Products> fetchAllProducts() {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Products p where status=:status");
        query.setParameter("status", 1);
        List<Products> list = (List<Products>) query.list();
        beginTransaction.commit();
        return list;
    }

    public List<Products> fetchProductByQRCode(String qrcode) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Products p where status =:status and qrCode=:code");
        query.setParameter("status", 1);
        query.setParameter("code", qrcode);
        List<Products> list = (List<Products>) query.list();
        System.out.println(qrcode + "list.size=>" + list.size());
        beginTransaction.commit();
        return list;
    }

    public Products fetchProductByName(String name) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from Products p where status =:status and prodName=:name");
        query.setParameter("status", 1);
        query.setParameter("name", name);
        List<Products> list = (List<Products>) query.list();
        beginTransaction.commit();
        return (Products) (list.size() > 0 ? list.get(0) : null);
    }

    public List<Products> fetchProductSearchList(String catName, String prodName, double minQty, double maxQty, double minPrice, double maxPrice) {
        System.out.println("prodName 1=>" + prodName);
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Categories_DA categories_DA = new Categories_DA();

        Categories cat = categories_DA.fetchCategoryByName(catName);
        int idcat = 0;
        if (cat != null) {
            idcat = cat.getCatid();
        }
        System.out.println("cat=>" + idcat + "cat=>" + catName);
        if (prodName == null) {
            prodName = "";
        }
        Query query = session.createSQLQuery("Select * from PRODUCT_TBL where PRODNAME LIKE '%"
                + prodName + "%' OR "
                + "CATEGORY_CATID = " + idcat + " AND status = 1 ");
        List list = query.list();
        ArrayList<Products> prodList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Products prod = new Products();
            Object[] obj = (Object[]) list.get(i);
            prod.setProdid((Integer) obj[0]);
            prod.setDateOfAddition((Date) obj[1]);
            prod.setBarCode((String) obj[2]);
            prod.setModTime((Date) obj[3]);
            prod.setModifiedBy((String) obj[4]);
            prod.setProdCost((Double) obj[5]);
            prod.setProdDesc((String) obj[6]);
            prod.setProdName((String) obj[7]);
            prod.setProdQty((Double) obj[8]);
            prod.setStatus((Integer) obj[10]);
            prod.setCategory((Categories) categories_DA.fetchCategoryById((Integer) obj[12]));

            prod.setQrCode((String) obj[9]);
            prod.setUom((String) obj[11]);

            prodList.add(prod);
        }
//         query = session.createQuery("from Products  where status=:status  AND prodName LIKE :name OR category.catName LIKE :catName");
//        query.setParameter("status", 1);
//        query.setParameter("name", prodName);
//        query.setParameter("catName", catName);
//        List<Products> prodList = (List<Products>) query.list();
        System.out.println("prodList size=>" + prodList.size());
        ArrayList<Products> productList = new ArrayList<>();

        List<Products> pList = prodList;

        for (int i = 0; i < pList.size(); i++) {
            Products pr = prodList.get(i);

            System.out.println("prod=>" + pr);
            double qty = pr.getProdQty();
            double cost = pr.getProdCost();
            System.out.println("prodName=>" + prodName);

            System.out.println("prodCost=>" + pr.getProdCost() + "maxPrice=>" + maxPrice + "comp=>" + (((pr.getProdCost() <= maxPrice))));
            if ((idcat != 0 && pr.getCategory().getCatid() == idcat) || !prodName.isEmpty() || ((qty <= maxQty && maxQty != 0) && qty >= minQty) || (cost >= minPrice && (cost <= maxPrice && maxPrice != 0))) {

                productList.add(pr);
            } else {
                // prodList.remove(pr);
            }
        }

//        prodList.stream().forEach((pr) -> {
//
//            System.out.println("prodCost=>" + pr.getProdCost() + "comp=>" + ((pr.getProdCost() >= minPrice && (pr.getProdCost() <= maxPrice && maxPrice != 0))));
//
//            
//            
//            if (((pr.getProdQty() <= maxQty && maxQty != 0) && pr.getProdQty() >= minQty) || (pr.getProdCost() >= minPrice && (pr.getProdCost() <= maxPrice && maxPrice != 0))) {
//                prodList.add(pr);
//            } else {
//                // prodList.remove(pr);
//            }
//        });
        beginTransaction.commit();
        return productList;
    }
}
