package automatedbillingsoftware_DA;

import automatedbillingsoftware.helper.IpMacManipulator;
import automatedbillingsoftware_modal.Application_Tbl;
import automatedbillingsoftware_modal.Company;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class LoginValiidate_DA {

    public List<automatedbillingsoftware_modal.Application_Tbl> fetchAllApplication() {

        String ipAddressandMacAddress = IpMacManipulator.getIpAddressandMacAddress();
        String ipAddr = ipAddressandMacAddress.split(":")[0];
        String macAddr = ipAddressandMacAddress.split(":")[1];

        List<automatedbillingsoftware_modal.Application_Tbl> applicationList = new ArrayList<>();

        SessionFactory sessionFactory = automatedbillingsoftware.helper.HibernateUtils.getRemoteSessionFactory();

        String hql = "from Application_Tbl as a where a.macAddress=:macAddr";
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setParameter("macAddr", macAddr);
        applicationList = (List<automatedbillingsoftware_modal.Application_Tbl>) query.list();
        session.getTransaction().commit();
        return applicationList;
    }

    public void addApplication(Application_Tbl applicationtbl) {
        SessionFactory sessionFactory = automatedbillingsoftware.helper.HibernateUtils.getRemoteSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(applicationtbl);
        session.getTransaction().commit();
    }

    public Company getCompanyByUseridandPassword(String userid, String pass) {
        SessionFactory sessionFactory = automatedbillingsoftware.helper.HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Company comp where comp.password =:pass and comp.name =:name");
        query.setParameter("pass", pass);
        query.setParameter("name", userid);
        System.out.println("pass=>" + pass + "name=>" + userid);
        List list = query.list();
        System.out.println("list=>"+list.size());
        Company comp = null;
        if (list.size() > 0) {
            comp = (Company) list.get(0);
        }
//            session = sessionFactory.openSession();
//            session.beginTransaction();

        session.getTransaction().commit();

        return comp;
    }
}
