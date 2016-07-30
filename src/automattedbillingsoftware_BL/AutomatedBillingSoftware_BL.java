package automattedbillingsoftware_BL;

import automatedbillingsoftware.helper.UserSession;
import automatedbillingsoftware_DA.LoginValiidate_DA;
import automatedbillingsoftware_modal.Application_Tbl;
import automatedbillingsoftware_modal.Company;
import java.util.List;

/**
 *
 * @author Arka
 */
public class AutomatedBillingSoftware_BL {

    private automatedbillingsoftware_DA.LoginValiidate_DA loginValidated;

    public AutomatedBillingSoftware_BL() {
        loginValidated = new LoginValiidate_DA();
    }

    public boolean isRegistered() {
        boolean isReg = true;

        List<Application_Tbl> fetchallApplication = loginValidated.fetchAllApplication();
        System.out.println("fetchallApplication=>" + fetchallApplication);
        if (fetchallApplication == null || fetchallApplication.size() == 0) {
            isReg = false;
        }
        return isReg;

    }

    public void addApplication(Application_Tbl application_Tbl) {
        loginValidated.addApplication(application_Tbl);
    }

    public boolean isValidUser(String userid, String pass) {
        boolean isValidUser = true;
        Company comp = loginValidated.getCompanyByUseridandPassword(userid, pass);
        if (comp == null) {
            isValidUser = false;
        } else {
            UserSession.setCompany(comp);
        }
        return isValidUser;
    }
}
