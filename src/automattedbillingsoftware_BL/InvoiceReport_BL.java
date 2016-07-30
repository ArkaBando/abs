/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automattedbillingsoftware_BL;

import automatedbillingsoftware_DA.InvoiceReport_DA;
import automatedbillingsoftware_modal.InvoiceReport;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Arka
 */
public class InvoiceReport_BL {

    InvoiceReport_DA invoiceReportDA;

    public InvoiceReport_BL() {
        if (invoiceReportDA == null) {
            invoiceReportDA = new InvoiceReport_DA();
        }
    }

    public InvoiceReport addInvoiceReport(InvoiceReport invReport) {
        invoiceReportDA = new InvoiceReport_DA();
        System.out.println("invrptObj=>" + invReport + "invoiceReportDA=>" + invoiceReportDA);
        //obj created because of error
        return invoiceReportDA.saveInvoiceReport(invReport);
    }

    public List<InvoiceReport> fetchAllInvoiceReport() {
        return invoiceReportDA.fetchAllInvoiceReport();
    }

    public void updateInvoice(InvoiceReport invReport) {
        invoiceReportDA.updateInvoiceReport(invReport);
    }
    
    public List<InvoiceReport> fetchInvoiceReports(Date frmDate,Date toDate,String docName,int orderNo,int docNo)
    {
      return  invoiceReportDA.searchInvoiceReport(frmDate, toDate, orderNo, docName, docNo);
    }
}
