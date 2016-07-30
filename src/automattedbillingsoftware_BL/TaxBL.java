/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automattedbillingsoftware_BL;

import automatedbillingsoftware_DA.TaxDA;
import automatedbillingsoftware_modal.Tax;
import java.util.List;

/**
 *
 * @author Arka
 */
public class TaxBL {

    private TaxDA taxDa;

    public TaxBL() {
        if (taxDa == null) {
            this.taxDa = new TaxDA();
        }
    }

    public Tax addTax(Tax tax) {
        return taxDa.addTax(tax);
    }

    public List<Tax> fetchAllTaxes() {
        return taxDa.fetchAllTaxList();
    }

    public void deleteTax(int id) {
        taxDa.deleteTax(id);
    }

    public void updateTax(Tax tax) {
        taxDa.updateTax(tax);
    }

    public Tax fetchTaxById(int id) {
        return taxDa.fetchTaxById(id);
    }

    public Tax fetchTaxByName(String name) {
        return taxDa.fetchTaxByName(name);
    }
}
