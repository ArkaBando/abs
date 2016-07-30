/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware.modal;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Arka
 */
public class RetailInvoiceList {

    private StringProperty documentNo;

    private StringProperty documentName;

    private StringProperty cLientName;

    private StringProperty date;

    private IntegerProperty orderNo;

    private IntegerProperty id;

    private IntegerProperty challanId;

    public RetailInvoiceList(String documentNo, String documentName, String cLientName, String date, Integer orderNo, Integer id, Integer challanId) {
        this.documentNo = new SimpleStringProperty(documentNo);
        this.documentName = new SimpleStringProperty(documentName);
        this.cLientName = new SimpleStringProperty(cLientName);
        this.date = new SimpleStringProperty(date);
        this.orderNo = new SimpleIntegerProperty(orderNo);
        this.id = new SimpleIntegerProperty(id);
        this.challanId = new SimpleIntegerProperty(challanId);
    }

    public StringProperty getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = new SimpleStringProperty(documentNo);
    }

    public StringProperty getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = new SimpleStringProperty(documentName);
    }

    public StringProperty getcLientName() {
        return cLientName;
    }

    public void setcLientName(String cLientName) {
        this.cLientName = new SimpleStringProperty(cLientName);
    }

    public StringProperty getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }

    public IntegerProperty getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = new SimpleIntegerProperty(orderNo);
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public IntegerProperty getChallanId() {
        return challanId;
    }

    public void setChallanId(Integer challanId) {
        this.challanId = new SimpleIntegerProperty(challanId);
    }

}
