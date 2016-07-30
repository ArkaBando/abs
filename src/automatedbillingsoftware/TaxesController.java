/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.modal.CategoryModal;
import automatedbillingsoftware.modal.TaxModal;
import automatedbillingsoftware_modal.Categories;
import automatedbillingsoftware_modal.Tax;
import automattedbillingsoftware_BL.CategoriesBL;
import automattedbillingsoftware_BL.TaxBL;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class TaxesController implements Initializable {

    private HomeController hc;

    @FXML
    private TextField taxid;

    @FXML
    private TextField taxperc;

    @FXML
    private TableView taxTbl;

    @FXML
    private TableColumn<TaxModal, Integer> taxId;
    @FXML
    private TableColumn<TaxModal, String> taxName;
    @FXML
    private TableColumn<TaxModal, Double> taxPerc;
    @FXML
    private TableColumn<TaxModal, String> taxModified;
    @FXML
    private TableColumn<TaxModal, TaxModal> action;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taxId.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        taxName.setCellValueFactory(cellData -> cellData.getValue().getTaxName());
        taxPerc.setCellValueFactory(cellData -> cellData.getValue().getTaxPerc().asObject());
        taxModified.setCellValueFactory(cellData -> cellData.getValue().getModificationDate());

        action.setMinWidth(40);
        action.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        action.setCellFactory(param -> new TableCell<TaxModal, TaxModal>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(TaxModal person, boolean empty) {
                super.updateItem(person, empty);

                if (person == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> {
                            getTableView().getItems().remove(person);

                            TaxBL taxbl = new TaxBL();
                            taxbl.deleteTax(person.getId().getValue());

//                            CategoriesBL categoriesBL = new CategoriesBL();
//                            Categories category = categoriesBL.fetchCategoriesbyId(person.getId().getValue());
//                            categoriesBL.deleteCategory(category);
                        }
                );

            }
        });

        taxName.setCellFactory(TextFieldTableCell.forTableColumn());
        taxName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TaxModal, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TaxModal, String> t) {

                ((TaxModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setTaxName(t.getNewValue());
                int id = t.getRowValue().getId().getValue();
                TaxBL taxBL = new TaxBL();
                Tax tax = taxBL.fetchTaxById(id);
                tax.setTaxName(t.getNewValue());
                taxBL.updateTax(tax);
            }
        });

        taxPerc.setCellFactory(TextFieldTableCell.<TaxModal, Double>forTableColumn(new DoubleStringConverter()));
        taxPerc.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TaxModal, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TaxModal, Double> t) {
                ((TaxModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setTaxPerc(t.getNewValue());
                int id = t.getRowValue().getId().getValue();
                TaxBL taxBL = new TaxBL();
                Tax tax = taxBL.fetchTaxById(id);
                tax.setTaxValue(t.getNewValue());
                taxBL.updateTax(tax);
            }
        });
// TODO
    }

    public void showErrMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Field Value");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addTax(ActionEvent event) {
        if (taxid.getText().trim().isEmpty()) {
            showErrMessage("Please Enter Valid taxName Field");
        } else if (taxperc.getText().trim().isEmpty()) {
            showErrMessage("Please Enter Valid taxValue in % Field");
        } else {
            Tax tax = new Tax();
            tax.setIsGroup(false);
            tax.setStatus(1);
            tax.setTaxAdditionDate(new Date());
            tax.setTaxValue(Double.parseDouble(taxperc.getText()));
            tax.setTaxName(taxid.getText());
            tax.setTaxType("permanent");
            TaxBL taxBL = new TaxBL();
            tax = taxBL.addTax(tax);
            hc.getTaxesData().add(new TaxModal(tax.getTaxId(), tax.getTaxName(), tax.getTaxValue(), new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a").format(tax.getTaxAdditionDate())));
            taxTbl.setItems(hc.getTaxesData());
        }
    }

    public void addTableList(HomeController hc) {
        this.hc = hc;
        taxTbl.setItems(hc.getTaxesData());

    }
}
