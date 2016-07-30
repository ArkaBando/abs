/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.HtmlToPdf;
import automatedbillingsoftware.helper.IpMacManipulator;
import automatedbillingsoftware.helper.MachineIdentifierHelper;
import automatedbillingsoftware_modal.Application_Tbl;
import automatedbillingsoftware_modal.Company;
import automattedbillingsoftware_BL.AutomatedBillingSoftware_BL;
import automattedbillingsoftware_BL.Company_BL;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AdminInformationEntryController implements Initializable {
    
    @FXML
    private PasswordField passwordid;
    
    @FXML
    private PasswordField repasswordid;
    
    @FXML
    private TextField nameid;
    
    @FXML
    private TextField companyid;
    
    @FXML
    private TextField vatid;
    
    @FXML
    private TextField panid;
    
    @FXML
    private TextField ph;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField ip;
    
    @FXML
    private void handlesave(ActionEvent event) throws IOException {
        
        String ipAddressandMacAddress = IpMacManipulator.getIpAddressandMacAddress();
        String ipAddr = ipAddressandMacAddress.split(":")[0];
        String macAddr = ipAddressandMacAddress.split(":")[1];
        
        if (nameid.getText().trim().isEmpty()) {
            showErrMessage("Please enter your name");
        } else if (companyid.getText().trim().isEmpty()) {
            showErrMessage("Please enter Company's name");
        } //        else if (vatid.getText().trim().isEmpty()) {
        //            showErrMessage("Please enter vatId");
        //        } //        } else if (panid.getText().trim().isEmpty()) {
        //            showErrMessage("Please enter your Pan id");
        //        } else if (ph.getText().trim().isEmpty()) {
        //            showErrMessage("Please Enter your phone");
        //        }
        else if (passwordid.getText().trim().isEmpty()) {
            showErrMessage("Please enter your password");
        } else if (repasswordid.getText().trim().isEmpty()) {
            showErrMessage("Please enter your confirmation password");
        } else if (!passwordid.getText().equals(repasswordid.getText())) {
            showErrMessage("Password and Confirmation Password does not matched");
        } else {
            Application_Tbl application_Tbl = new Application_Tbl();
            application_Tbl.setDateOfInstallation(new Date());
            application_Tbl.setIpAddress(ipAddr);
            application_Tbl.setMacAddress(macAddr);
            application_Tbl.setName(nameid.getText());
            application_Tbl.setStatus(1);
            application_Tbl.setText("Testing");
            application_Tbl.setType("admin");
            
            Company comp = new Company();
            comp.setAddress("");
            comp.setCompanyName(companyid.getText());
            comp.setVatNo(vatid.getText());
            comp.setCountry("India");
            comp.setStatus(1);
            comp.setWebsite("");
            comp.setCountry("India");
            comp.setTax("");
            comp.setLogo("");
            comp.setDateOfInsertion(new Date());
            comp.setVatNo("");
            comp.setName(nameid.getText());
            comp.setPhone(ph.getText());
            comp.setPassword(passwordid.getText());
            comp.setPanNo(panid.getText());
            comp.setPhone(ph.getText());
            comp.setEmail(email.getText());
            Company_BL companyBL = new Company_BL();
            companyBL.addCompany(comp);
            
            automattedbillingsoftware_BL.AutomatedBillingSoftware_BL abl = new AutomatedBillingSoftware_BL();
            abl.addApplication(application_Tbl);
            
            HashMap<String, String> hm = new HashMap<>();
            hm.put("macId", macAddr);
            hm.put("ipId", ipAddr);
            hm.put("installationDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            HtmlToPdf.setDefaultTemplete();
            
            boolean isSetMachineIdentifier = MachineIdentifierHelper.setMachineIdentifier(hm);
            
            if (isSetMachineIdentifier) {
                try {
                    Stage stage = (Stage) companyid.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("LoginDocument.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    stage.setOnCloseRequest(e -> handle(e));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("ipAddress=>" + ipAddr);
            System.out.println("macAddr=>" + macAddr);
            //ip.setText(ipAddr + "/" + macAddr);
        }
        
    }
    
    public void handle(WindowEvent event) {
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    private void handlereset(ActionEvent event) {
        passwordid.setText("");
        repasswordid.setText("");
        nameid.setText("");
        companyid.setText("");
        vatid.setText("");
        panid.setText("");
        ph.setText("");
        email.setText("");
        String ipAddressandMacAddress = IpMacManipulator.getIpAddressandMacAddress();
        String ipAddr = ipAddressandMacAddress.split(":")[0];
        String macAddr = ipAddressandMacAddress.split(":")[1];
        ip.setText(macAddr + "/" + ipAddr);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String ipAddressandMacAddress = IpMacManipulator.getIpAddressandMacAddress();
        String ipAddr = ipAddressandMacAddress.split(":")[0];
        String macAddr = ipAddressandMacAddress.split(":")[1];
        ip.setText(macAddr + "/" + ipAddr);
    }
    
    public void showErrMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Field Value");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
