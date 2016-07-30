/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_modal;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Arka
 */
@Entity
@Table(name = "invoice_report_tbl")
public class InvoiceReport {

    private int status;

    @Id
    private int id;

    private double vatNo;

    private String email;

    private double orderNo;

    private String billNo;

    private Date billDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ChallanGenerated challanGen;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVatNo() {
        return vatNo;
    }

    public void setVatNo(double vatNo) {
        this.vatNo = vatNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(double orderNo) {
        this.orderNo = orderNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public ChallanGenerated getChallanGen() {
        return challanGen;
    }

    public void setChallanGen(ChallanGenerated challanGen) {
        this.challanGen = challanGen;
    }

}
