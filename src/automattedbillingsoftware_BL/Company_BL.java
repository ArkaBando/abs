/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automattedbillingsoftware_BL;

import automatedbillingsoftware.helper.UserSession;
import automatedbillingsoftware_DA.CompanyDetailsDA;
import automatedbillingsoftware_modal.Company;

/**
 *
 * @author Arka
 */
public class Company_BL {

    automatedbillingsoftware_DA.CompanyDetailsDA company;

    public Company_BL() {
        this.company = new CompanyDetailsDA();
    }

    public void addCompany(Company comp) {
        company.addCompany(comp);
    }
    
    public boolean isValidUser(String user,String pass)
    {
       return (this.company.fetchCompanyDetailsByUserIdandPass(user, pass)!=null)?true:false;
    }
    
    
    public boolean isCompanyReg()
    {
      return company.fetchCompanyList().size()>0?true:false;
    }
    
    public void updateCompany(Company comp)
    {
        company.updateCurrentCompany(comp);
       // UserSession.setCompany(comp);
    }
}
