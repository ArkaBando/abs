/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_DA;

import automatedbillingsoftware.helper.HibernateUtils;
import automatedbillingsoftware.helper.UserSession;
import automatedbillingsoftware_modal.Company;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Arka
 */
public class CompanyDetailsDA {

    public void addCompany(Company comp) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(comp);
        session.getTransaction().commit();
    }

    public Company fetchCompanyDetailsByUserIdandPass(String user, String pass) {
        Company comp = null;
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Company comp where comp.password=:pass and comp.name=:name");
        query.setParameter("name", user);
        query.setParameter("pass", pass);
        List list = query.list();
        if (list != null && list.size() > 0) {
            comp = (Company) list.get(0);
        }
        session.getTransaction().commit();
        return comp;
    }

    public List<Company> fetchCompanyList() {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Company comp");
        List<Company> compList = query.list();
        session.getTransaction().commit();

        return compList;
    }

    public void updateCurrentCompany(Company comp) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Company c where c.companyId=:id");
        query.setParameter("id", UserSession.getCompany().getCompanyId());
        Company com = (Company) query.list().get(0);
        com.setAddress(comp.getAddress());
        com.setCompanyName(comp.getCompanyName());
        com.setCountry(comp.getCountry());
        com.setDateOfInsertion(comp.getDateOfInsertion());
        com.setEmail(comp.getEmail());
        com.setLogo(comp.getLogo());
        com.setName(comp.getName());
        com.setPanNo(comp.getPanNo());
        com.setPassword(comp.getPassword());
        com.setPhone(comp.getPhone());
        com.setStatus(1);
        com.setTax(comp.getTax());
        com.setVatNo(comp.getVatNo());
        com.setWebsite(comp.getWebsite());

        session.update(com);
        UserSession.setCompany(com);
        session.getTransaction().commit();

    }
}
