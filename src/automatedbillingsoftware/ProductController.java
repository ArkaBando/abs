/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.QRCodeHelper;
import automatedbillingsoftware.modal.ProductModal;
import automatedbillingsoftware_modal.Categories;
import automatedbillingsoftware_modal.Products;
import automattedbillingsoftware_BL.CategoriesBL;
import automattedbillingsoftware_BL.ProductsBL;
import com.google.zxing.NotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class ProductController implements Initializable {

    ObservableList<String> categoryObservableArrayList = FXCollections.observableArrayList();

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

    private HomeController hc;
    @FXML
    private ComboBox<String> catList;
    @FXML
    private BorderPane productBorderPane;

    @FXML
    private TableView<ProductModal> prodTable;

    @FXML
    private TableColumn<ProductModal, Integer> slNo;

    @FXML
    private TableColumn<ProductModal, String> productName;

    @FXML
    private TableColumn<ProductModal, String> productDesc;

    @FXML
    private TableColumn<ProductModal, Double> qty;

    @FXML
    private TableColumn<ProductModal, Double> ppu;

    @FXML
    private TableColumn<ProductModal, String> produom;

    @FXML
    private TableColumn<ProductModal, String> qrCode;

    @FXML
    private TableColumn<ProductModal, String> barCode;

    @FXML
    private TableColumn<ProductModal, String> modifiedDate;

    @FXML
    private TableColumn<ProductModal, ProductModal> action;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //catList.
        slNo.setCellValueFactory(cellData -> cellData.getValue().getProdId().asObject());
        productName.setCellValueFactory(cellData -> cellData.getValue().getProdName());
        productDesc.setCellValueFactory(cellData -> cellData.getValue().getProdDesc());
        produom.setCellValueFactory(cellData -> cellData.getValue().getUom());
        qty.setCellValueFactory(cellData -> cellData.getValue().getProdQty().asObject());
        ppu.setCellValueFactory(cellData -> cellData.getValue().getPricePerUnit().asObject());
        qrCode.setCellValueFactory(cellData -> cellData.getValue().getQrCode());
        barCode.setCellValueFactory(cellData -> cellData.getValue().getBarCode());
        modifiedDate.setCellValueFactory(cellData -> cellData.getValue().getModifiedDate());
        //    cat_Name.setCellValueFactory(cellData -> cellData.getValue().getCatName());
        // ObservableList<String> levelChoice = FXCollections.observableArrayList("Bla", "Blo");

        productName.setCellFactory(TextFieldTableCell.forTableColumn());
        productName.setOnEditCommit(new EventHandler<CellEditEvent<ProductModal, String>>() {
            @Override
            public void handle(CellEditEvent<ProductModal, String> t) {
                //   System.out.println("cat name");
                ((ProductModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setProdName(t.getNewValue());
                int id = t.getRowValue().getProdId().getValue();
                ProductsBL categoriesBL = new ProductsBL();
                Products prod = categoriesBL.fetchProdById(id);
                prod.setProdName(t.getNewValue());
                categoriesBL.updateProduct(prod);
            }
        });

        productDesc.setCellFactory(TextFieldTableCell.forTableColumn());
        productDesc.setOnEditCommit(new EventHandler<CellEditEvent<ProductModal, String>>() {
            @Override
            public void handle(CellEditEvent<ProductModal, String> t) {
                //   System.out.println("cat name");
                ((ProductModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setProdDesc(t.getNewValue());
                int id = t.getRowValue().getProdId().getValue();
                ProductsBL categoriesBL = new ProductsBL();
                Products prod = categoriesBL.fetchProdById(id);
                prod.setProdDesc(t.getNewValue());
                categoriesBL.updateProduct(prod);
            }
        });

//        qrCode.setCellFactory(TextFieldTableCell.forTableColumn());
//        qrCode.setOnEditCommit(new EventHandler<CellEditEvent<ProductModal, String>>() {
//            private final Button addBarCode = new Button("Add BarCode");
//
//            @Override
//            public void handle(CellEditEvent<ProductModal, String> t) {
//                //   System.out.println("cat name");
//
//                FileChooser fileChooser = new FileChooser();
//
//                DirectoryChooser directoryChooser = new DirectoryChooser();
//                directoryChooser.setInitialDirectory(new File(".\\res\\"));
//                directoryChooser.setTitle("Select QRcode file save location");
//                File selectedDirectory
//                        = directoryChooser.showDialog(productBorderPane.getScene().getWindow());
//
//            }
//        });
        ppu.setCellFactory(TextFieldTableCell.<ProductModal, Double>forTableColumn(new DoubleStringConverter()));
        ppu.setOnEditCommit(new EventHandler<CellEditEvent<ProductModal, Double>>() {
            @Override
            public void handle(CellEditEvent<ProductModal, Double> t) {
                ((ProductModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setPricePerUnit(t.getNewValue());
                int id = t.getRowValue().getProdId().getValue();

                ProductsBL categoriesBL = new ProductsBL();
                Products prod = categoriesBL.fetchProdById(id);
                prod.setProdCost(t.getNewValue());
                categoriesBL.updateProduct(prod);
            }
        });

        qty.setCellFactory(TextFieldTableCell.<ProductModal, Double>forTableColumn(new DoubleStringConverter()));
        qty.setOnEditCommit(new EventHandler<CellEditEvent<ProductModal, Double>>() {
            @Override
            public void handle(CellEditEvent<ProductModal, Double> t) {
                ((ProductModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setProdQty(t.getNewValue());
                int id = t.getRowValue().getProdId().getValue();

                ProductsBL categoriesBL = new ProductsBL();
                Products prod = categoriesBL.fetchProdById(id);
                prod.setProdQty(t.getNewValue());
                categoriesBL.updateProduct(prod);
            }
        });

        produom.setCellFactory(ComboBoxTableCell.forTableColumn(mu_options));
//        produom.setCellValueFactory(
//                new PropertyValueFactory<>("level")
//        );
//        produom.setCellFactory(ComboBoxTableCell.forTableColumn(levelChoice));
        produom.setOnEditCommit(
                new EventHandler<CellEditEvent<ProductModal, String>>() {
            @Override
            public void handle(CellEditEvent<ProductModal, String> t) {
                System.out.println("edit");
                ((ProductModal) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUom(t.getNewValue());
                String newValue = t.getNewValue();
                int id = t.getRowValue().getProdId().getValue();
                ProductsBL categoriesBL = new ProductsBL();
                Products prod = categoriesBL.fetchProdById(id);
                prod.setUom(newValue);
                categoriesBL.updateProduct(prod);
            }
        ;
        }
);
        
        action.setMinWidth(40);
        action.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        action.setCellFactory(param -> new TableCell<ProductModal, ProductModal>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(ProductModal person, boolean empty) {
                super.updateItem(person, empty);

                if (person == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> {
                            getTableView().getItems().remove(person);
                            new ProductsBL().deleteProduct(person.getProdId().getValue());
//                            CategoriesBL categoriesBL = new CategoriesBL();
//                            Categories category = categoriesBL.fetchCategoriesbyId(person.getId().getValue());
//                            categoriesBL.deleteCategory(category);
                        }
                );

            }
        });

        setCategoryList();
    }
    @FXML
    TextField prodName;

//    @FXML
//    ComboBox<String> catList;
    @FXML
    TextField minUnit;

    @FXML
    TextField maxUnit;

    @FXML
    TextField minQty;

    @FXML
    TextField maxQty;

    @FXML
    private void searchCategory(ActionEvent event) {

        System.out.println("searching");

        String catName = catList.getSelectionModel().getSelectedItem();
        double minimumUnit = Double.parseDouble((minUnit.getText().isEmpty()) ? "0" : minUnit.getText());
        double maximumUnit = Double.parseDouble((maxUnit.getText().isEmpty()) ? "0" : maxUnit.getText());
        double minumQty = Double.parseDouble((minQty.getText().isEmpty()) ? "0" : minQty.getText());
        double maxumQty = Double.parseDouble((maxQty.getText().isEmpty()) ? "0" : maxQty.getText());
        String productName = prodName.getText();
        System.out.println("catName=>" + catName + "prodName=>" + productName);
        List<Products> fetchProductsforSearch = new ProductsBL().fetchProductsforSearch(productName, catName, minumQty, maxumQty, minimumUnit, maximumUnit);
        System.out.println("list size=>" + fetchProductsforSearch.size());
        hc.getProdData().setAll(FXCollections.observableArrayList());
        for (Products prod : fetchProductsforSearch) {
            hc.getProdData().add(new ProductModal(prod.getProdid(), prod.getProdName(), prod.getProdDesc(), prod.getProdQty(), prod.getProdCost(), prod.getUom(), prod.getQrCode(), ((prod.getBarCode() == null) ? "" : prod.getBarCode()), new SimpleDateFormat("dd-MM-yyyy").format(prod.getDateOfAddition())));
        }
        prodTable.setItems(hc.getProdData());
    }

    @FXML
    private void handleReset(ActionEvent event) {
        catList.getSelectionModel().clearSelection();
        prodName.clear();
        minUnit.clear();
        maxUnit.clear();
        minQty.clear();
        maxQty.clear();
    }

    private void setCategoryList() {
        CategoriesBL catbl = new CategoriesBL();
        List<Categories> fetchCategoriesesList = catbl.fetchCategoriesesList();
        ArrayList<String> al = new ArrayList<>();
        for (Categories cat : fetchCategoriesesList) {
            al.add(cat.getCatName());
        }

        categoryObservableArrayList.addAll(al);
        catList.setItems(categoryObservableArrayList);
    }

    public void initProdTable() {
        ProductsBL prodBL = new ProductsBL();
        List<Products> fetchProdList = prodBL.fetchProdList();
        hc.getProdData().setAll(FXCollections.observableArrayList());
        for (Products prod : fetchProdList) {
            hc.getProdData().add(new ProductModal(prod.getProdid(), prod.getProdName(), prod.getProdDesc(), prod.getProdQty(), prod.getProdCost(), prod.getUom(), prod.getQrCode(), ((prod.getBarCode() == null) ? "" : prod.getBarCode()), new SimpleDateFormat("dd-MM-yyyy").format(prod.getDateOfAddition())));
        }
        prodTable.setItems(hc.getProdData());
    }

    @FXML
    public void handleAddProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("addProduct.fxml"));
        BorderPane taxesPane = loader.load();
        AddProductController taxcontroller = loader.getController();
        taxcontroller.setHc(this);
        //taxcontroller.addTableList(this);
        Stage addDialogueStage = new Stage();
        addDialogueStage.setTitle("AutomatedBillingSoftware");
        addDialogueStage.initModality(Modality.WINDOW_MODAL);
        addDialogueStage.initOwner(productBorderPane.getScene().getWindow());
        Scene sc = new Scene(taxesPane);

        addDialogueStage.setScene(sc);
        addDialogueStage.setResizable(false);
        addDialogueStage.setAlwaysOnTop(true);
        addDialogueStage.setFullScreen(false);
        addDialogueStage.showAndWait();
    }

    public void setProductsSettings(HomeController controller) {
        this.hc = controller;
        prodTable.setItems(controller.getProdData());
    }

    @FXML
    public void handleSearchProductByQRCode(ActionEvent event) throws IOException, FileNotFoundException, NotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an logo file");
        fileChooser.setInitialDirectory(new File(".//res//"));
        boolean addAll = fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(maxQty.getScene().getWindow());

        if (selectedFile != null) {
            String absolutePath = selectedFile.getAbsolutePath();
            String readQRCode = QRCodeHelper.readQRCode(absolutePath);

            ProductsBL prodBl = new ProductsBL();
            List<Products> fetchProductByQRCode = prodBl.fetchProductByQRCode(readQRCode);
            if (fetchProductByQRCode != null || fetchProductByQRCode.size() > 0) {

                hc.getProdData().setAll(FXCollections.observableArrayList());
                for (Products prod : fetchProductByQRCode) {
                    hc.getProdData().add(new ProductModal(prod.getProdid(), prod.getProdName(), prod.getProdDesc(), prod.getProdQty(), prod.getProdCost(), prod.getUom(), prod.getQrCode(), ((prod.getBarCode() == null) ? "" : prod.getBarCode()), new SimpleDateFormat("dd-MM-yyyy").format(prod.getDateOfAddition())));
                }
                prodTable.setItems(hc.getProdData());
            }

        }
    }
}
