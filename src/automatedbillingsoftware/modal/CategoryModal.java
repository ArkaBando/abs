/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware.modal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Arka
 */
public class CategoryModal {
    
    private IntegerProperty id;
    private StringProperty catName;
    private StringProperty catDesc;
    private DoubleProperty catDisc;
    private StringProperty date;

    public CategoryModal(Integer id, String catName, String catDesc, Double catDisc, String date) {
        this.id = new SimpleIntegerProperty(id);
        this.catName =new SimpleStringProperty(catName);
        this.catDesc = new SimpleStringProperty(catDesc);
        this.catDisc =new SimpleDoubleProperty(catDisc);
        this.date = new SimpleStringProperty(date);
    }

    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getCatName() {
        return catName;
    }

    public StringProperty getCatDesc() {
        return catDesc;
    }

    public DoubleProperty getCatDisc() {
        return catDisc;
    }

    public StringProperty getDate() {
        return date;
    }

    /**
     * @param id the id to set
     */
    public void setId(IntegerProperty id) {
        this.id = id;
    }

    /**
     * @param catName the catName to set
     */
    public void setCatName(String catName) {
        this.catName = new SimpleStringProperty(catName);
    }

    /**
     * @param catDesc the catDesc to set
     */
    public void setCatDesc(String catDesc) {
        this.catDesc = new SimpleStringProperty(catDesc);
    }

    /**
     * @param catDisc the catDisc to set
     */
    public void setCatDisc(Double catDisc) {
        this.catDisc = new SimpleDoubleProperty(catDisc);
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }
    
    
}
