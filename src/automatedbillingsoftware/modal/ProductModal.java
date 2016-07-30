/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware.modal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Arka
 */
public class ProductModal {
    private IntegerProperty prodId;
    
    private StringProperty prodName;
    
    private StringProperty prodDesc;
    
    private DoubleProperty prodQty;
    
    private DoubleProperty pricePerUnit;
    
    private StringProperty uom;
    
    private StringProperty qrCode;
    
    private StringProperty barCode;
    
    private StringProperty modifiedDate;

    public IntegerProperty getProdId() {
        return prodId;
    }

    public void setProdId(IntegerProperty prodId) {
        this.prodId = prodId;
    }

    public StringProperty getProdName() {
        return prodName;
    }

    public ProductModal(Integer prodId, String prodName, String prodDesc, Double prodQty, Double pricePerUnit, String uom, String qrCode, String barCode, String modifiedDate) {
        this.prodId =new SimpleIntegerProperty(prodId);
        this.prodName = new SimpleStringProperty(prodName);
        this.prodDesc =  new SimpleStringProperty(prodDesc);
        this.prodQty = new SimpleDoubleProperty(prodQty);
        this.pricePerUnit = new SimpleDoubleProperty(pricePerUnit);
        this.uom = new SimpleStringProperty(uom);
        this.qrCode = new SimpleStringProperty(qrCode);
        this.barCode = new SimpleStringProperty(barCode);
        this.modifiedDate = new SimpleStringProperty(modifiedDate);
    }

    public void setProdName(String prodName) {
        this.prodName = new SimpleStringProperty(prodName);
    }

    public StringProperty getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = new SimpleStringProperty(prodDesc);
    }

    public DoubleProperty getProdQty() {
        return prodQty;
    }

    public void setProdQty(Double prodQty) {
        this.prodQty = new SimpleDoubleProperty(prodQty);
    }

    public DoubleProperty getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = new SimpleDoubleProperty(pricePerUnit);
    }

    public StringProperty getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = new SimpleStringProperty(uom);
    }

    public StringProperty getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = new SimpleStringProperty(qrCode);
    }

    public StringProperty getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = new SimpleStringProperty(barCode);
    }

    public StringProperty getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = new SimpleStringProperty(modifiedDate);
    }
    
}
