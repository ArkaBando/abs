/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automattedbillingsoftware_BL;

import automatedbillingsoftware_DA.ChallanDA;
import automatedbillingsoftware_modal.Challan;
import automatedbillingsoftware_modal.ChallanGenerated;
import java.util.List;

/**
 *
 * @author Arka
 */
public class ChallanBL {

    private ChallanDA challanda;

    public ChallanBL() {
        if (challanda == null) {
            challanda = new ChallanDA();
        }
    }

    public Challan addChallan(Challan challan) {
        return challanda.addChallan(challan);
    }

    public List<Challan> fetchChallanList() {
        return challanda.fetchAllChallanList();
    }

    public void deleteChallan(int id) {
        challanda.deleteChallan(id);
    }

    public Challan fetchChallanById(int id) {
        return challanda.fetchChallanById(id);
    }

    public ChallanGenerated saveChallan(ChallanGenerated challan) {
        System.out.println("challanid=>" + challan.getId());
        if (challan.getId() == 0) {
            return challanda.saveChallanGen(challan);
        } else {
            return challanda.updateChallanGen(challan);
        }
    }

    public List<ChallanGenerated> fetchAllOrders() {
        return challanda.fetchChallanOrders();
    }

    public void deleteChallanGen(int id) {
        challanda.deleteChallanGen(id);
    }

    public ChallanGenerated fetchChallanGenerated(int id) {
        return challanda.fetchChallanGenById(id);
    }

    public Challan fetchChallanByDocName(String name) {
        return challanda.fetchChallansByDocNameAndDocNo(name);
    }

    public Challan fetchChallanByDocNo(String docNo) {
        return challanda.fetchChallanByDocNo(docNo);
    }

    public void updateChallan(Challan challan) {
        challanda.updateChallan(challan);
    }
}
