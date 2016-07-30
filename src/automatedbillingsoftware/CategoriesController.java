/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.modal.CategoryModal;
import automatedbillingsoftware_modal.Categories;
import automattedbillingsoftware_BL.CategoriesBL;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class CategoriesController implements Initializable {

    private HomeController hc;

    @FXML
    TextField catName;

    @FXML
    TextArea catDesc;

    @FXML
    TextField catDisc;

    public CategoriesController() {

    }

    @FXML
    private TableColumn<CategoryModal, Integer> slNo;

    @FXML
    private TableColumn<CategoryModal, String> cat_Name;

    @FXML
    private TableColumn<CategoryModal, String> cat_Desc;

    @FXML
    private TableColumn<CategoryModal, Double> cat_Disc;

    @FXML
    private TableColumn<CategoryModal, String> catMod;

    @FXML
    private TableColumn<CategoryModal, CategoryModal> catChange;

    @FXML
    private TableView<CategoryModal> catTbl;

    @FXML
    private TextField searchCat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slNo.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        cat_Name.setCellValueFactory(cellData -> cellData.getValue().getCatName());
        cat_Desc.setCellValueFactory(cellData -> cellData.getValue().getCatDesc());
        cat_Disc.setCellValueFactory(cellData -> cellData.getValue().getCatDisc().asObject());
        catMod.setCellValueFactory(cellData -> cellData.getValue().getDate());

        catChange.setMinWidth(40);
        catChange.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        catChange.setCellFactory(param -> new TableCell<CategoryModal, CategoryModal>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(CategoryModal person, boolean empty) {
                super.updateItem(person, empty);

                if (person == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> {
                            getTableView().getItems().remove(person);
                            CategoriesBL categoriesBL = new CategoriesBL();
                            Categories category = categoriesBL.fetchCategoriesbyId(person.getId().getValue());
                            categoriesBL.deleteCategory(category);
                        }
                );

            }
        });

//        cat_Name.setCellValueFactory(
//            new PropertyValueFactory<CategoryModal, StringProperty>("catName"));
        cat_Name.setCellFactory(TextFieldTableCell.forTableColumn());
        cat_Name.setOnEditCommit(new EventHandler<CellEditEvent<CategoryModal, String>>() {
            @Override
            public void handle(CellEditEvent<CategoryModal, String> t) {
                System.out.println("cat name");
                ((CategoryModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setCatName(t.getNewValue());
                int id = t.getRowValue().getId().getValue();
                CategoriesBL categoriesBL = new CategoriesBL();
                Categories category = categoriesBL.fetchCategoriesbyId(id);
                category.setCatName(t.getNewValue());
                categoriesBL.updateCategories(category);
            }
        });

        cat_Desc.setCellFactory(TextFieldTableCell.forTableColumn());
        cat_Desc.setOnEditCommit(new EventHandler<CellEditEvent<CategoryModal, String>>() {
            @Override
            public void handle(CellEditEvent<CategoryModal, String> t) {
                ((CategoryModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setCatDesc(t.getNewValue());
                int id = t.getRowValue().getId().getValue();
                CategoriesBL categoriesBL = new CategoriesBL();
                Categories category = categoriesBL.fetchCategoriesbyId(id);
                category.setCatDesc(t.getNewValue());
                categoriesBL.updateCategories(category);
            }
        });

        cat_Disc.setCellFactory(TextFieldTableCell.<CategoryModal, Double>forTableColumn(new DoubleStringConverter()));
        cat_Disc.setOnEditCommit(new EventHandler<CellEditEvent<CategoryModal, Double>>() {
            @Override
            public void handle(CellEditEvent<CategoryModal, Double> t) {
                ((CategoryModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setCatDisc(t.getNewValue());
                int id = t.getRowValue().getId().getValue();
                CategoriesBL categoriesBL = new CategoriesBL();
                Categories category = categoriesBL.fetchCategoriesbyId(id);
                category.setDiscount(t.getNewValue());
                categoriesBL.updateCategories(category);
            }
        });

// TODO
    }

    @FXML
    public void handleAddCategories(ActionEvent event) {
        try {
            if (catDesc.getText().trim().isEmpty()) {
                showErrMessage("Please enter valid Category Description");
            } else if (catName.getText().trim().isEmpty()) {
                showErrMessage("Please enter valid Category Name");
            } else {
                CategoriesBL categoriesBL = new CategoriesBL();
                Categories cat = new Categories();
                cat.setCatDesc(catDesc.getText());
                cat.setCatName(catName.getText());
                cat.setStatus(1);
                if (catDisc.getText().trim().isEmpty()) {
                    catDisc.setText("0");
                }
                cat.setDiscount(Double.parseDouble(catDisc.getText().trim()));
                cat.setCatModifiedDate(new Date());
                cat = categoriesBL.addCategories(cat);

                SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                hc.getCatData().add(new CategoryModal(cat.getCatid(), catName.getText(), catDesc.getText(), Double.parseDouble(catDisc.getText()), sf.format(new Date())));

            }
        } catch (NumberFormatException nex) {
            showErrMessage(nex.getMessage());
            nex.printStackTrace();
        } finally {
            catDisc.clear();
            catDesc.clear();
            catName.clear();
        }

    }

    public void showErrMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Field Value");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addTableList(HomeController hc) {
        this.hc = hc;
        catTbl.setItems(hc.getCatData());
        System.out.println("45");
    }

    public void handleSearchCategories(ActionEvent event) {
        if (!searchCat.getText().trim().isEmpty()) {
            CategoriesBL cat = new CategoriesBL();
            List<Categories> fetchCategoriesesList = cat.fetchCategoriesList(searchCat.getText());
            System.out.println("list size=>" + fetchCategoriesesList.size() + "list=>" + fetchCategoriesesList);
            SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
            hc.setCatData(FXCollections.observableArrayList());
            for (Iterator<Categories> it = fetchCategoriesesList.iterator(); it.hasNext();) {

                Categories cata = it.next();
                hc.getCatData().add(new CategoryModal(cata.getCatid(), cata.getCatName(), cata.getCatDesc(), cata.getDiscount(), sf.format(cata.getCatModifiedDate())));

            }
            catTbl.setItems(hc.getCatData());

        } else {
            CategoriesBL cat = new CategoriesBL();
            List<Categories> fetchCategoriesesList = cat.fetchCategoriesesList();
            System.out.println("list size=>" + fetchCategoriesesList.size() + "list=>" + fetchCategoriesesList);
            SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
            hc.setCatData(FXCollections.observableArrayList());
            for (Iterator<Categories> it = fetchCategoriesesList.iterator(); it.hasNext();) {

                Categories cata = it.next();
                hc.getCatData().add(new CategoryModal(cata.getCatid(), cata.getCatName(), cata.getCatDesc(), cata.getDiscount(), sf.format(cata.getCatModifiedDate())));

            }
            catTbl.setItems(hc.getCatData());

        }
    }

}
