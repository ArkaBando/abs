/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_DA;

import automatedbillingsoftware.helper.HibernateUtils;
import automatedbillingsoftware_modal.InvoiceReport;
import automattedbillingsoftware_BL.ChallanBL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Arka
 */
public class InvoiceReport_DA {

    public InvoiceReport saveInvoiceReport(InvoiceReport invReport) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        session.saveOrUpdate(invReport);
        beginTransaction.commit();

        return invReport;
    }

    public List<InvoiceReport> fetchAllInvoiceReport() {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from InvoiceReport where status =:status");
        query.setParameter("status", 1);
        List<InvoiceReport> invoiceReportList = query.list();
        beginTransaction.commit();
        return invoiceReportList;
    }

    public InvoiceReport updateInvoiceReport(InvoiceReport invReport) {
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        session.update(invReport);
        beginTransaction.commit();
        return invReport;
    }

    public List<InvoiceReport> searchInvoiceReport(Date frmDate, Date toDate, int orderNo, String docName, int docNo) {
        List<InvoiceReport> invoiceList = new LinkedList<>();
        SessionFactory sessionFactory = HibernateUtils.getLocSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("from InvoiceReport where status =:status");
        query.setParameter("status", 1);
        List<InvoiceReport> invoiceReportList = query.list();

        List<InvoiceReport> searchList = new LinkedList<>();

        for (int i = 0; i < invoiceReportList.size(); i++) {
            if (invoiceReportList.get(i).getOrderNo() == (double) orderNo
                    || (invoiceReportList.get(i).getBillDate().compareTo(toDate) <= 0 && invoiceReportList.get(i).getBillDate().compareTo(frmDate) >= 0)
                    || new ChallanBL().fetchChallanByDocName(docName) != null) {
                searchList.add(invoiceReportList.get(i));
            }
        }

        beginTransaction.commit();
        System.out.println("searchList=>"+searchList);
        return searchList;

    }
}
