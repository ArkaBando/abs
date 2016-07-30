/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class UserController implements Initializable {

    @FXML
    TextField usersearchid;

    @FXML
    TextField userid;

    @FXML
    PasswordField passwid;

    @FXML
    CheckBox productid;

    @FXML
    CheckBox category;

    @FXML
    CheckBox taxes;

    @FXML
    CheckBox billingandchallan;

    @FXML
    Label errMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productid.setAllowIndeterminate(false);
        productid.setSelected(true);

        category.setAllowIndeterminate(false);
        category.setSelected(true);

        taxes.setAllowIndeterminate(false);
        taxes.setSelected(true);

        billingandchallan.setAllowIndeterminate(false);
        billingandchallan.setSelected(true);
    }

}
