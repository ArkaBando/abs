/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Arka
 */
@Entity
@Table(name = "product_tbl")
public class Products implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int prodid;

    @Column(unique = true)
    private String prodName;

    private double prodCost;

    private String prodDesc;

    @Temporal(TemporalType.TIMESTAMP)
    private Date DateOfAddition;

    private int status;

    private String modifiedBy;

    private String qrCode;

    private String barCode;

    private String uom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modTime;

    private double prodQty;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER,targetEntity = Tax.class)
 //   @Column(unique = false,nullable = true,updatable = true)
    private Tax tax = new Tax();

    @ManyToOne(targetEntity = Categories.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Categories category;

    public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public double getProdCost() {
        return prodCost;
    }

    public void setProdCost(double prodCost) {
        this.prodCost = prodCost;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public Date getDateOfAddition() {
        return DateOfAddition;
    }

    public void setDateOfAddition(Date DateOfAddition) {
        this.DateOfAddition = DateOfAddition;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public double getProdQty() {
        return prodQty;
    }

    public void setProdQty(double prodQty) {
        this.prodQty = prodQty;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
