package dbmodal;

import automatedbillingsoftware.helper.IpMacManipulator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBAcessLayer {

    Connection con = null;

    public Connection getCon() {
        return con;
    }

    public void setCon() {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/abs_db [odexabs on ODEXABS]", "odexabs", "odexabs123");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DBAcessLayer() {

        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/abs_db [odexabs on ODEXABS]", "odexabs", "odexabs123");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean addUser(String name, String password, String designation, String email, String companyName, String uid) {
        boolean IsAddedUser = false;
        String ipAddressandMacAddress = IpMacManipulator.getIpAddressandMacAddress();
        String insertQuery_userdtls = "INSERT into USER_DETAILS_TBL (UID,MACADDRESS,IPADDRESS,EMAL,COMPANYNAME)  values(" + uid + ",'" + ipAddressandMacAddress.split(":")[0] + "'"
                + ",'" + ipAddressandMacAddress.split(":")[1] + "','" + email + "','" + companyName + "')";
        String insertQuery_user = "INSERT into USER_TBL(NAME,PASSWORD,DESIGNATION) values('" + name + "','" + password + "','" + designation + "')";
        try {
            Statement stmt = con.createStatement();
            int executeUpdate = stmt.executeUpdate(insertQuery_user);

            stmt = con.createStatement();
           
            IsAddedUser = true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return IsAddedUser;
    }
}
