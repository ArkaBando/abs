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
public class ChallanModal {

    private IntegerProperty id;

    private StringProperty clientName;

    private StringProperty description;

    private StringProperty documentName;

    private StringProperty uom;

    private StringProperty docNo;

    private StringProperty companyTitle;

    private StringProperty categoryName;

    private StringProperty productName;

    private DoubleProperty price;

    private DoubleProperty discount;

    private DoubleProperty quantity;

    private DoubleProperty tax;

    private StringProperty date;

    private StringProperty modifiedDate;

    public ChallanModal(Integer id, String clientName, String description, String documentName, String uom, String docNo, String companyTitle, String categoryName, String productName, Double price, double discount, double qty, double tax, String date, String modifiedDate) {
        this.id = new SimpleIntegerProperty(id);
        this.clientName = new SimpleStringProperty(clientName);
        this.description = new SimpleStringProperty(description);
        this.documentName = new SimpleStringProperty(documentName);
        this.uom = new SimpleStringProperty(uom);
        this.docNo = new SimpleStringProperty(docNo);
        this.companyTitle = new SimpleStringProperty(companyTitle);
        this.categoryName = new SimpleStringProperty(categoryName);
        this.productName = new SimpleStringProperty(productName);
        this.price = new SimpleDoubleProperty(price);
        this.discount = new SimpleDoubleProperty(discount);
        this.quantity = new SimpleDoubleProperty(qty);
        this.modifiedDate = new SimpleStringProperty(date);
        this.tax = new SimpleDoubleProperty(tax);
        this.date = new SimpleStringProperty(modifiedDate);
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public StringProperty getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = new SimpleStringProperty(clientName);
    }

    public StringProperty getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    public StringProperty getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = new SimpleStringProperty(documentName);
    }

    public StringProperty getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = new SimpleStringProperty(uom);
    }

    public StringProperty getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = new SimpleStringProperty(docNo);
    }

    public StringProperty getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = new SimpleStringProperty(companyTitle);
    }

    public StringProperty getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = new SimpleStringProperty(categoryName);
    }

    public StringProperty getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = new SimpleStringProperty(productName);
    }

    public DoubleProperty getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public DoubleProperty getDiscount() {
        return discount;
    }

    public void setDiscount(DoubleProperty discount) {
        this.discount = discount;
    }

    public DoubleProperty getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = new SimpleDoubleProperty(quantity);
    }

    public DoubleProperty getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = new SimpleDoubleProperty(tax);
    }

    public StringProperty getDate() {
        return date;
    }

    public void setDate(StringProperty date) {
        this.date = date;
    }

    public StringProperty getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(StringProperty modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
