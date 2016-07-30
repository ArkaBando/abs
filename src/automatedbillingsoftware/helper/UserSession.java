/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware.helper;

import automatedbillingsoftware_modal.Company;

/**
 *
 * @author Arka
 */
public class UserSession {
   private static Company company;

    public static Company getCompany() {
        return company;
    }

    public static void setCompany(Company aCompany) {
        company = aCompany;
    }
   
}
