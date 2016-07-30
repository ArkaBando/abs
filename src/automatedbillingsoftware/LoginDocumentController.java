/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.MachineIdentifierHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class LoginDocumentController implements Initializable {

    @FXML
    private PasswordField passwd;

    @FXML
    private TextField userid;

    @FXML
    private void handleLogin(ActionEvent event) throws ParseException {
        HashMap<String, String> machineIdentifierMap = MachineIdentifierHelper.getMachineIdentifierMap();
        String installedDate = machineIdentifierMap.get("installationDate");
        long currentDate = new Date().getTime();
        long installDate = new SimpleDateFormat("dd-MM-yyyy").parse(installedDate).getTime();

        long noOfDays = (currentDate - installDate) / (1000 * 24 * 60 * 60);
        //System.out.println("noOfDays=>" + noOfDays);
        //condition for trial period noOfDays<7 
//        if (noOfDays < -1) {
            try {
                if (passwd.getText().trim().isEmpty()) {
                    showErrMessage("Please insert password value");
                } else if (userid.getText().trim().isEmpty()) {
                    showErrMessage("Please insert userid value");
                } else {
                    automattedbillingsoftware_BL.AutomatedBillingSoftware_BL automattedBillingSoftware = new automattedbillingsoftware_BL.AutomatedBillingSoftware_BL();
                    boolean validUser = automattedBillingSoftware.isValidUser(userid.getText(), passwd.getText());
                    if (validUser) {
                        Stage stage = (Stage) passwd.getScene().getWindow();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("home.fxml"));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setFullScreen(true);
                        stage.setResizable(false);
                        stage.centerOnScreen();
                        stage.setTitle("Automated Billing Software");
                        stage.show();

                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Login Error");
                        alert.setContentText("Invalid Login Crediantials");

                        alert.showAndWait();
                    }
                }
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Exception");
                alert.setHeaderText("Exception Details");
                alert.setContentText("Login Exception");

// Create expandable Exception.
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("The exception stacktrace was:");

                TextArea textArea = new TextArea(exceptionText);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
                alert.getDialogPane().setExpandableContent(expContent);

                alert.showAndWait();
                ex.printStackTrace();
            }
//        } else {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setHeaderText("Please contact your product Vendor");
//            alert.setTitle(" Software Expired");
//            alert.setContentText("Your software has been been expired");
//            alert.showAndWait();
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void showErrMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Field Value");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
