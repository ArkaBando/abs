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
public class TaxModal {

    public TaxModal(Integer id, String taxName, Double taxPerc, String modificationDate) {
        this.id = new SimpleIntegerProperty(id);
        this.taxName = new SimpleStringProperty(taxName);
        this.taxPerc = new SimpleDoubleProperty(taxPerc);
        this.modificationDate = new SimpleStringProperty(modificationDate);
    }
    private IntegerProperty id;
    private StringProperty taxName;
    private DoubleProperty taxPerc;
    private StringProperty modificationDate;

    public IntegerProperty getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public StringProperty getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = new SimpleStringProperty(taxName);
    }

    public DoubleProperty getTaxPerc() {
        return taxPerc;
    }

    public void setTaxPerc(Double taxPerc) {
        this.taxPerc = new SimpleDoubleProperty(taxPerc);
    }

    public StringProperty getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = new SimpleStringProperty(modificationDate);
    }

}
