/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.QRCodeHelper;
import automatedbillingsoftware.helper.UserSession;
import automatedbillingsoftware.modal.ProductModal;
import automatedbillingsoftware_modal.Categories;
import automatedbillingsoftware_modal.Products;
import automatedbillingsoftware_modal.Tax;
import automattedbillingsoftware_BL.CategoriesBL;
import automattedbillingsoftware_BL.ProductsBL;
import automattedbillingsoftware_BL.TaxBL;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class AddProductController implements Initializable {

    ProductController hc;

    ObservableList<String> mu_options
            = FXCollections.observableArrayList(
                    "Boxes",
                    "Centimeters",
                    "CubicMeters",
                    "Gram",
                    "Kilogram",
                    "Pound",
                    "Litre",
                    "Meter"
            );

    ObservableList<String> cat_options
            = FXCollections.observableArrayList();

    ObservableList<String> tax_options
            = FXCollections.observableArrayList();

    @FXML
    BorderPane prodAnchorPane;

    @FXML
    TextField prodName;

    @FXML
    TextArea prodDesc;

    @FXML
    TextField prodQty;

    @FXML
    TextField price;

    @FXML
    ComboBox<String> mu;

    @FXML
    ComboBox<String> cat;

    @FXML
    ComboBox<String> tax;

    @FXML
    Label message;

    private String qrFilePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mu.setItems(mu_options);
        ArrayList<String> al = new ArrayList<String>();
        CategoriesBL categoriesBL = new CategoriesBL();
        for (Categories cat : categoriesBL.fetchCategoriesesList()) {
            al.add(cat.getCatName());
        }
        cat_options.setAll(al);
        cat.setItems(cat_options);

        al = new ArrayList<>();
        TaxBL tbl = new TaxBL();
        for (Tax tax : tbl.fetchAllTaxes()) {
            al.add(tax.getTaxName());
        }

        tax_options.setAll(al);
        tax.setItems(tax_options);
        // TODO
    }

    @FXML
    public void handleQRCode(ActionEvent event) throws WriterException, IOException {
        FileChooser fileChooser = new FileChooser();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        //   directoryChooser.setInitialDirectory(new File(".\\res\\"));
        directoryChooser.setTitle("Select QRcode file save location");
        File selectedDirectory
                = directoryChooser.showDialog(prodAnchorPane.getScene().getWindow());

        if (selectedDirectory == null) {
            showErrMessage("Please select an directory for storing QRCode File");
        } else if (!prodName.getText().isEmpty() && !prodDesc.getText().isEmpty()) {
            qrFilePath = selectedDirectory.getAbsolutePath() + "\\" + mu.getSelectionModel().getSelectedItem() + prodQty.getText() + prodName.getText() + prodDesc.getText() + ".png";

        } else {
            showErrMessage("Please fill up ProductDescription And Its Name");
        }//  labelSelectedDirectory.setText(selectedDirectory.getAbsolutePath());
    }

    public void showErrMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Field Value");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleAddProduct(ActionEvent event) throws WriterException, IOException {
        if (prodName.getText().isEmpty()) {

        } else if (prodDesc.getText().isEmpty()) {

        } else if (prodQty.getText().isEmpty()) {

        } else if (cat.getSelectionModel().getSelectedItem().equals(cat.promptTextProperty().getValue())) {

        } else if (tax.getSelectionModel().getSelectedItem().equals(tax.promptTextProperty().getValue())) {

        } else if (mu.getSelectionModel().getSelectedItem().equals(mu.promptTextProperty().getValue())) {

        } else if (price.getText().isEmpty()) {

        } else {
            Products products = new Products();
            CategoriesBL catBL = new CategoriesBL();
            products.setCategory(catBL.fetchCategoriesByName(cat.getSelectionModel().getSelectedItem()));
            String taxItem = tax.getSelectionModel().getSelectedItem();
            //      List<Tax> taxList = new ArrayList<Tax>();
            TaxBL taxbl = new TaxBL();
            System.out.println("tax=>" + tax.getValue());
            Tax taxObj = taxbl.fetchTaxByName(tax.getValue());
//            taxList.add(taxObj);
            products.setTax(taxObj);
            products.setDateOfAddition(new Date());
            products.setUom(mu.getSelectionModel().getSelectedItem());
            products.setModTime(new Date());
            products.setProdCost(Double.parseDouble(price.getText()));
            products.setModifiedBy(UserSession.getCompany().getName());
            System.out.println("qrFilePath=>" + qrFilePath);
            
            if (qrFilePath != null && !qrFilePath.isEmpty()) {
                products.setQrCode(mu.getSelectionModel().getSelectedItem() + prodQty.getText()
                        + prodName.getText() + prodDesc.getText());
            }
            products.setProdQty(Double.parseDouble(prodQty.getText()));
            products.setProdDesc(prodDesc.getText());
            products.setProdName(prodName.getText());

            products.setStatus(1);
            ProductsBL prodBL = new ProductsBL();
            prodBL.addProducts(products);

            if (qrFilePath != null) {
                boolean createQRCode = QRCodeHelper.createQRCode(qrFilePath, mu.getSelectionModel().getSelectedItem() + prodQty.getText() + prodName.getText() + prodDesc.getText());
                if (createQRCode) {

                    message.setText("QRCode File has been Generated: " + qrFilePath);
//            Alert alert = new Alert(AlertType.INFORMATION);
//            
//            alert.setTitle("QRFile Information");
//            alert.setContentText("Your QRCode File has been Generated At" + qrFilePath);
//            alert.showAndWait();

                }
            }
            hc.initProdTable();
            ((Stage) cat.getScene().getWindow()).close();
        }
    }

    @FXML
    public void handleReset(ActionEvent event) {
        prodName.clear();
        prodQty.clear();
        prodDesc.clear();
        price.clear();
        mu.getSelectionModel().clearSelection();
        cat.getSelectionModel().clearSelection();
        tax.getSelectionModel().clearSelection();
    }

    public void setHc(ProductController hc) {
        this.hc = hc;
    }
}
