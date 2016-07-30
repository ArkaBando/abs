/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.UserSession;
import automatedbillingsoftware_modal.Company;
import automattedbillingsoftware_BL.Company_BL;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class CompanyController implements Initializable {

    @FXML
    BorderPane compPane;

    @FXML
    TextField companyName;

    @FXML
    TextField adminName;

    @FXML
    TextField city;

    @FXML
    ComboBox<String> country;

    @FXML
    TextField email;

    @FXML
    TextField password;

    @FXML
    TextField tax;

    @FXML
    TextField pan;

    @FXML
    TextField vat;

    @FXML
    TextField website;

    @FXML
    TextArea address;

    @FXML
    TextField ph;

    private HomeController hc;

    private String logoFilePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Company company = UserSession.getCompany();

        if (company != null) {
            if (company.getCompanyName() != null) {
                companyName.setText(company.getCompanyName());
            }

            if (company.getName() != null) {
                adminName.setText(company.getName());
            }

            if (company.getVatNo() != null) {
                vat.setText(company.getVatNo());
            }

            if (company.getPanNo() != null) {
                pan.setText(company.getPanNo());
            }

            if (company.getPassword() != null) {
                password.setText(company.getPassword());
            }

            if (company.getPhone() != null) {
                ph.setText(company.getPhone());
            }

            if (company.getEmail() != null) {
                email.setText(company.getEmail());
            }

            if (company.getAddress() != null) {
                address.setText(company.getAddress());
            }
            setCountriesComboBox();
        }

    }

    private void setCountriesComboBox() {
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();

        ArrayList<String> countriesList = new ArrayList<String>();
        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);
            countriesList.add(obj.getDisplayCountry());
//		System.out.println("Country Code = " + obj.getCountry() 
//			+ ", Country Name = " + obj.getDisplayCountry());
        }
        observableArrayList.addAll(countriesList);
        country.setItems(observableArrayList);
    }

    @FXML
    public void handleLogo(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an logo file");
        // fileChooser.setInitialDirectory(new File(".//res//"));
        boolean addAll = fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(compPane.getScene().getWindow());
        if (selectedFile != null) {
            String property = System.getProperty("user.dir");
            String pdfLoc = "" + property.substring(0, property.lastIndexOf("\\")) + "\\" + selectedFile.getName();
            try {
                BufferedImage readImg = ImageIO.read(selectedFile);
                boolean write = ImageIO.write(readImg, "png", new File(pdfLoc));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            BufferedImage read = ImageIO.read(selectedFile);
//            logoFilePath = companyName.getText();
//            ImageIO.write(read, "png", new File(logoFilePath));
//            logoFilePath = new File(logoFilePath).getAbsolutePath();

            logoFilePath = pdfLoc;
        } else {
            showErrMessage("Please Select an Valid Logo File");
        }
    }

    @FXML
    private void handleSave(ActionEvent event) throws IOException {

        if (logoFilePath == null || logoFilePath.isEmpty()) {
            showErrMessage("Please Select an logo Image File");
        } else if (companyName.getText().isEmpty()) {
            showErrMessage("Please Enter your Company Name");
        } else if (adminName.getText().isEmpty()) {
            showErrMessage("Please Enter your name");
        } else if (email.getText().isEmpty()) {
            showErrMessage("Please Enter your Email");
        } else if (password.getText().isEmpty()) {
            showErrMessage("Please Enter your Password");
        } else if (pan.getText().isEmpty()) {
            showErrMessage("Please Enter your Password");
        } else if (address.getText().isEmpty()) {
            showErrMessage("Please Enter your Address");
        } else if (ph.getText().isEmpty()) {
            showErrMessage("Please Enter your Ph no");
        } else if (country.getSelectionModel().getSelectedItem().equals(country.getPromptText())) {
            showErrMessage("Please Select an Country");
        } else {
            Company com = new Company();
            com.setName(adminName.getText());
            com.setCompanyName(companyName.getText());
            com.setCountry(country.getSelectionModel().getSelectedItem());
            com.setAddress(address.getText());
            com.setStatus(1);
            com.setPhone(ph.getText());
            com.setPassword(password.getText());
            com.setPanNo(pan.getText());
            com.setLogo(logoFilePath);
            com.setDateOfInsertion(new Date());
            if (!tax.getText().isEmpty()) {
                com.setTax(tax.getText());
            }
            if (!city.getText().isEmpty()) {
                if (com.getAddress().contains("city")) {
                    com.setAddress(com.getAddress());
                } else {
                    com.setAddress(com.getAddress() + "city:" + city.getText());
                }
            }
            if (!vat.getText().isEmpty()) {
                com.setVatNo(vat.getText());
            }
            if (!website.getText().isEmpty()) {
                com.setWebsite(website.getText());
            }

            Company_BL comBL = new Company_BL();
            comBL.updateCompany(com);

            setImageIcon(hc);
            Stage stage = (Stage) compPane.getScene().getWindow();
            stage.close();

//            Stage stage = (Stage) city.getScene().getWindow();
//            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
        }
    }

    public void showErrMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Field Value");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setImageIcon(HomeController hc) {
        if (UserSession.getCompany().getLogo() != null && !UserSession.getCompany().getLogo().isEmpty()) {
            try {
                Image img = new Image(new FileInputStream(new File(UserSession.getCompany().getLogo())));
//                hc.getCompanyIco().setImage(img);
//                hc.getCompanyIco().setFitHeight(37);
//                hc.getCompanyIco().setFitWidth(45);
//                hc.getCompanyIco().setPreserveRatio(true);
//                hc.getCompanyIco().setSmooth(true);
//                hc.getCompanyIco().setCache(true);

                BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                Background background = new Background(backgroundImage);
                File file = new File(UserSession.getCompany().getLogo());
                System.out.println("logo path=>" + UserSession.getCompany().getLogo());
                Image imgVal = new Image(file.toURI().toURL().toString(), 30, 30, true, true);
                hc.imgId.setImage(imgVal);
                //     hc.homebtn.setBackground(background);
                hc.Infomessage.setText("");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @return the hc
     */
    public HomeController getHc() {
        return hc;
    }

    /**
     * @param hc the hc to set
     */
    public void setHc(HomeController hc) {
        this.hc = hc;
    }
}
