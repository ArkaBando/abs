/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.UserSession;
import automatedbillingsoftware.modal.CategoryModal;
import automatedbillingsoftware.modal.ChallanModal;
import automatedbillingsoftware_modal.Categories;
import automatedbillingsoftware_modal.Challan;
import automatedbillingsoftware_modal.ChallanGenerated;
import automatedbillingsoftware_modal.Products;
import automatedbillingsoftware_modal.Tax;
import automattedbillingsoftware_BL.CategoriesBL;
import automattedbillingsoftware_BL.ChallanBL;
import automattedbillingsoftware_BL.ProductsBL;
import automattedbillingsoftware_BL.TaxBL;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class ChallanController implements Initializable {

    private ChallanGenerated challanGen;

    public ChallanGenerated getChallanGen() {
        return challanGen;
    }

    public void setChallanGen(ChallanGenerated aChallanGen) {
        challanGen = aChallanGen;
    }

    private DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private HomeController hc;

    @FXML
    private TextField clientName;

    @FXML
    private TextField docName;

    @FXML
    private DatePicker dateid;

    @FXML
    private DatePicker shippingDateid;

    @FXML
     ComboBox<String> prodNameId;

    @FXML
    private TextArea descriptionId;

    @FXML
    private ComboBox<String> uomId;

    @FXML
    private ComboBox<String> categoryId;

    @FXML
    private TextField docNumId;

    @FXML
    private TableView<ChallanModal> challandataTable;

    @FXML
    private TextArea companyTitle;

    @FXML
     TextField qtyId;

    @FXML
    private Label header;

    @FXML
    private Label headertxt;

    @FXML
    private TableColumn<ChallanModal, Integer> slNo;

    @FXML
    private TableColumn<ChallanModal, String> docNoId;

    @FXML
    private TableColumn<ChallanModal, String> deescId;

    @FXML
    private TableColumn<ChallanModal, String> productId;

    @FXML
    private TableColumn<ChallanModal, Double> priceId;

    @FXML
    private TableColumn<ChallanModal, Double> discountId;

    @FXML
    private TableColumn<ChallanModal, Double> taxId;

    @FXML
    private TableColumn<ChallanModal, String> docNameId;

    @FXML
    private TableColumn<ChallanModal, String> companyTitleId;

    @FXML
    private TableColumn<ChallanModal, String> dateId;

    @FXML
    private TableColumn<ChallanModal, String> shippingDate;

    @FXML
    private TableColumn<ChallanModal, Double> quantityId;

    @FXML
    private TableColumn<ChallanModal, ChallanModal> actionId;

    @FXML
    private ComboBox<String> taxidlist;

    @FXML
    private Button gri;

    @FXML
    private void handleGenRetailInvoice(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ChallanController.class.getResource("Generate_RetailInvoice.fxml"));
        System.out.println("loader=>" + loader);
        BorderPane taxesPane = loader.load();
        GenRetailInvoiceController invoice_controller = loader.getController();
        invoice_controller.getOrder_No().setText(challanGen.getId() + "");
        UUID randomUUID = UUID.randomUUID();
        String billNumber = randomUUID.toString();
        invoice_controller.getBillNo().setText(billNumber);
        invoice_controller.getSiteAddr().setText(UserSession.getCompany().getAddress());
        invoice_controller.setChallanGen(challanGen);
//        setTaxesData();
//        taxcontroller.addTableList(this);
        Stage addDialogueStage = new Stage();
        addDialogueStage.setTitle("Genarate Invoice Report");
        addDialogueStage.initModality(Modality.WINDOW_MODAL);
        addDialogueStage.initOwner(header.getScene().getWindow());
        Scene sc = new Scene(taxesPane);

        addDialogueStage.setScene(sc);
        addDialogueStage.setResizable(false);
        addDialogueStage.setAlwaysOnTop(true);
        addDialogueStage.setFullScreen(false);
        addDialogueStage.showAndWait();

    }

    public Button getGri() {
        return gri;
    }

    public void setGri(Button gri) {
        this.gri = gri;
    }

    @FXML
    private Button addChallanBtn;

    @FXML
    private Button genChallanBtn;

    @FXML
    private ComboBox<String> taxDrpDown;

    private ObservableList<ChallanModal> challanData = FXCollections.observableArrayList();

    ObservableList<Double> discountList = FXCollections.observableArrayList();

    private ObservableList<String> taxesList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    private ObservableList<String> catListData = FXCollections.observableArrayList();

    private ObservableList<String> prodListData = FXCollections.observableArrayList();

    private ObservableList<String> uomListData = FXCollections.observableArrayList("Boxes",
            "Centimeters",
            "CubicMeters",
            "Gram",
            "Kilogram",
            "Pound",
            "Litre",
            "Meter");

    @FXML
    private void handleGenChallan(ActionEvent ae) throws IOException {

        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("genChallan.fxml"));
        BorderPane taxesPane = loader.load();
        GenChallanController taxcontroller = loader.getController();
        taxcontroller.setCc(this);
        // setTaxesData();
        //  taxcontroller.addTableList(this);
        Stage addDialogueStage = new Stage();
        addDialogueStage.setTitle("Taxes");
        addDialogueStage.initModality(Modality.WINDOW_MODAL);
        addDialogueStage.initOwner(clientName.getScene().getWindow());
        Scene sc = new Scene(taxesPane);

        addDialogueStage.setScene(sc);
        addDialogueStage.setResizable(false);
        addDialogueStage.setAlwaysOnTop(true);
        addDialogueStage.setFullScreen(false);
        addDialogueStage.showAndWait();
    }

    public void showErrMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Field Value");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setChallanData(FXCollections.observableArrayList());
        initializeEdit();
    }

    private void initializeEdit() {
        slNo.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        docNoId.setCellValueFactory(cellData -> cellData.getValue().getDocNo());
        deescId.setCellValueFactory(cellData -> cellData.getValue().getDescription());
        productId.setCellValueFactory(cellData -> cellData.getValue().getProductName());
        priceId.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        discountId.setCellValueFactory(cellData -> cellData.getValue().getDiscount().asObject());
        docNameId.setCellValueFactory(cellData -> cellData.getValue().getClientName());
        taxId.setCellValueFactory(cellData -> cellData.getValue().getTax().asObject());
        companyTitleId.setCellValueFactory(cellData -> cellData.getValue().getCompanyTitle());
        dateId.setCellValueFactory(cellData -> cellData.getValue().getDate());
        shippingDate.setCellValueFactory(cellData -> cellData.getValue().getModifiedDate());
        quantityId.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());

        productId.setCellFactory(TextFieldTableCell.forTableColumn());
        productId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ChallanModal, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ChallanModal, String> t) {
//                System.out.println("cat name");
                ((ChallanModal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setProductName(t.getNewValue());
                // int id = t.getRowValue().getId().getValue();

                String docNo = t.getRowValue().getDocNo().getValue();
                ChallanBL challanBl = new ChallanBL();
                Challan challanObj = challanBl.fetchChallanByDocNo(docNo);
                ProductsBL prodBL = new ProductsBL();
                Products product = prodBL.fetchProductByName(t.getRowValue().getProductName().getValue());
                if (product == null) {
                    showErrMessage("product Not Found By :" + t.getRowValue().getProductName().getValue());
                } else if (product.getProdQty() < t.getRowValue().getQuantity().getValue()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Quantity exceed stock quantity");
                    alert.setContentText(" " + t.getRowValue().getProductName().getValue() + "does not exsists");
                    alert.showAndWait();
                }
                challanObj.setProduct(prodBL.fetchProductByName(t.getRowValue().getProductName().getValue()));
                challanBl.updateChallan(challanObj);
            }
        });

        actionId.setMinWidth(40);
        actionId.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actionId.setCellFactory(param -> new TableCell<ChallanModal, ChallanModal>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(ChallanModal person, boolean empty) {
                super.updateItem(person, empty);

                if (person == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> {

                    ChallanBL challenBl = new ChallanBL();

                    Challan challan = challenBl.fetchChallanById(person.getId().getValue());
                    ProductsBL pbl = new ProductsBL();
                    Products prod = pbl.fetchProductByName(person.getProductName().getValue());
                    double tax = new TaxBL().fetchTaxByName(getTaxDrpDown().getSelectionModel().getSelectedItem()).getTaxValue();

                    desetTableFooter(challan, tax, person.getId().getValue());
                    getTableView().getItems().remove(person);

                    challenBl.deleteChallan(person.getId().getValue());
//                    ProductsBL prodBL = new ProductsBL();
//                    Products product = prodBL.fetchProductByName(prodNameId.getSelectionModel().getSelectedItem());
//                    System.out.println("tot quantity=>" + (product.getProdQty() + Double.parseDouble(qtyId.getText())));
//                    product.setProdQty((product.getProdQty() + Double.parseDouble(qtyId.getText())));
//
//                    prodBL.updateProduct(product);
//                    System.out.println("product=>"+product.getProdQty());
//                            CategoriesBL categoriesBL = new CategoriesBL();
//                            Categories category = categoriesBL.fetchCategoriesbyId(person.getId().getValue());
//                            categoriesBL.deleteCategory(category);
                }
                );
            }
        });

        TaxBL taxbl = new TaxBL();
        taxbl = new TaxBL();
        List<Tax> fetchAllTaxes = taxbl.fetchAllTaxes();
        List<String> taxList = new ArrayList<>();

        for (Tax tax : fetchAllTaxes) {
            taxList.add(tax.getTaxName());
        }

        taxesList.addAll(taxList);
        taxDrpDown.setItems(taxesList);

        shippingDateid.setValue(shippingDateid.getValue());
        dateid.setValue(dateid.getValue());
        uomId.setItems(uomListData);
        categoryId.valueProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
//                System.out.println(ov);
//                System.out.println(t);
//                System.out.println(t1);

                if (t1 != null) {
                    ArrayList<String> al = new ArrayList<>();
                    if (!categoryId.getSelectionModel().getSelectedItem().equals(categoryId.getPromptText())) {
                        ProductsBL prodBL = new ProductsBL();
                        List<Products> fetchProdList = prodBL.fetchProdList();

                        for (Products prod : fetchProdList) {
                            if (prod.getProdQty() > 0 && prod.getCategory().getCatName().trim().equalsIgnoreCase(getCategoryId().getSelectionModel().getSelectedItem())) {
                                al.add(prod.getProdName());
                            }
                        }
                    }
                    getProdListData().setAll(al);
                    getProdNameId().setItems(getProdListData());
                    getProdNameId().disableProperty().set(false);
                }
            }

        });

//        categoryId.valueProperty().addListener(new ChangeListener<String>() {
//        @Override 
//        public void changed(ObservableValue ov, String t, String t1) {
//
//        }    
//    });
        CategoriesBL catBL = new CategoriesBL();
        List<Categories> fetchCategoriesesList = catBL.fetchCategoriesesList();
        ArrayList<String> categoryList = new ArrayList<>();
        for (Categories cat : fetchCategoriesesList) {
            categoryList.add(cat.getCatName());
        }
        catListData.addAll(categoryList);
        categoryId.setItems(catListData);
    }

    public ObservableList<Double> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(ObservableList<Double> discountList) {
        this.discountList = discountList;
    }

    @FXML
    private void handleCatList(ActionEvent event) {

    }

    @FXML
    private TextField totalprice;

    @FXML
    private TextField totaltax;

    @FXML
    private TextField netPrice;

    private BigDecimal totalprc = new BigDecimal(BigInteger.ZERO);
    private BigDecimal netprc = new BigDecimal(BigInteger.ZERO);
    private BigDecimal totaltaxs = new BigDecimal(BigInteger.ZERO);

    @FXML
    private void handleChallan(ActionEvent event) {
        if (clientName.getText().trim().isEmpty()) {
            showErrMessage("Please enters Client Names");
        } else if (docName.getText().trim().isEmpty()) {
            showErrMessage("Please enters Document Name");
        } else if (dateid.getValue() == null) {
            showErrMessage("Please enters date Value");
        } else if (shippingDateid.getValue() == null) {
            showErrMessage("Please enters Shipping date Value");
        } else if (prodNameId.getSelectionModel().getSelectedItem() == null) {
            showErrMessage("Please enters product Name");
        } else if (docNumId.getText().isEmpty()) {
            showErrMessage("Please enters document Number");
        } else if (descriptionId.getText().isEmpty()) {
            showErrMessage("Please enters description");
        } else if (companyTitle.getText().isEmpty()) {
            showErrMessage("Please enters company Title");
        } else if (qtyId.getText().isEmpty()) {
            showErrMessage("Please enters qty");
        } else if (uomId.getSelectionModel().getSelectedItem() == null) {
            showErrMessage("Please select Unit of Measurement Value");
        } else if (categoryId.getSelectionModel().getSelectedItem() == null) {
            showErrMessage("Please select Category Value");
        } else if (prodNameId.getSelectionModel().getSelectedItem() == null) {
            showErrMessage("Please select Product Name Value");
        } else if (taxDrpDown.getSelectionModel().getSelectedItem() == null) {
            showErrMessage("Please select Tax Value");
        } else {
            ChallanBL challanbl = new ChallanBL();

            Challan challan = new Challan();
            challan.setCategory(new CategoriesBL().fetchCategoriesByName(categoryId.getSelectionModel().getSelectedItem()));
            challan.setClientName(clientName.getText());
            challan.setCompanyTitle(companyTitle.getText());
            challan.setDescription(descriptionId.getText());
            challan.setDocumentName(docName.getText());
            challan.setDate(new Date());
            challan.setDocNo(docNumId.getText());
            challan.setProduct(new ProductsBL().fetchProductByName(prodNameId.getSelectionModel().getSelectedItem()));
            challan.setShippingDate(new Date());
            challan.setUom(uomId.getSelectionModel().getSelectedItem());

            if ((Double.parseDouble(qtyId.getText()))
                    > (new ProductsBL().fetchProductByName(prodNameId.getSelectionModel().getSelectedItem()).getProdQty())) {
                showErrMessage(prodNameId.getSelectionModel().getSelectedItem() + " qty exceed stock limit of" + new ProductsBL().fetchProductByName(prodNameId.getSelectionModel().getSelectedItem()).getProdQty());
                return;
            }

            challan.setQty(Double.parseDouble(qtyId.getText()));
            challan.setStatus(1);
            challan.setTax(new TaxBL().fetchTaxByName(taxDrpDown.getValue()));
            challan.setStatus(1);
            challan = challanbl.addChallan(challan);

            if (challan != null) {
                Challan cata = challan;
                double tax = 0;
                //autodeletion

                Tax fetchTaxByName = new TaxBL().fetchTaxByName(taxDrpDown.getValue());
                tax = fetchTaxByName.getTaxValue();

                //  System.out.println("prod discount=>" + new ProductsBL().fetchProductByName(cata.getProduct().getProdName()).getCategory().getDiscount());
                getChallanData().add(new ChallanModal(cata.getId(), cata.getClientName(), cata.getDescription(),
                        cata.getDocumentName(), cata.getUom(), cata.getDocNo(), cata.getCompanyTitle(),
                        cata.getCategory().getCatName(), cata.getProduct().getProdName(),
                        cata.getProduct().getProdCost(), new ProductsBL().fetchProductByName(cata.getProduct().getProdName()).getCategory().getDiscount(), cata.getQty(),
                        tax, new SimpleDateFormat("dd-MM-yyyy").format(cata.getDate()),
                        new SimpleDateFormat("dd-MM-yyyy").format(cata.getShippingDate())));
                challandataTable.setItems(challanData);
                setTableFooter(cata, tax);
            }
        }
    }

    private void setTableFooter(Challan cata, double tax) {
        totaltaxs = new BigDecimal(BigInteger.ZERO);
        totalprc = new BigDecimal(BigInteger.ZERO);
        netprc = new BigDecimal(BigInteger.ZERO);
        if (challandataTable.getItems().size() > 0) {

            double totalprice1 = cata.getProduct().getProdCost() - (cata.getProduct().getProdCost() * (tax / 100));
            String totalpricetxt = Double.toString(totalprice1);
            totalprice.setText(totalpricetxt);

            for (int j = 0; j < challandataTable.getItems().size(); j++) {
                ChallanModal get = challandataTable.getItems().get(j);
                Double tax1 = get.getTax().getValue();
                Double price = get.getPrice().getValue();
                String productName = get.getProductName().getValue();
                ProductsBL prodBL = new ProductsBL();
                Products prod = prodBL.fetchProductByName(productName);
                if (prod != null) {
                    double qty = get.getQuantity().getValue();
                    double discount = prod.getCategory().getDiscount();
                    totalprc = totalprc.add(new BigDecimal(price * qty, MathContext.DECIMAL64).setScale(6, RoundingMode.UP));
                    totaltaxs = totaltaxs.add(new BigDecimal((qty * tax1 * price) / 100, MathContext.DECIMAL64).setScale(6, RoundingMode.UP));
                    netprc = netprc.add(new BigDecimal(((qty * price) + ((qty * tax1 * price) / 100)) - (((qty * price * discount) / 100)), MathContext.DECIMAL64).setScale(6, RoundingMode.UP));
                }
            }

        }
        totaltax.setText(totaltaxs.doubleValue() + "");
        totalprice.setText(netprc.doubleValue() + "");
        netPrice.setText(totalprc.doubleValue() + "");
        //     challandataTable.scrollTo();
    }

    private void desetTableFooter(Challan cata, double tax, int delid) {
//        totaltaxs = new BigDecimal(Double.parseDouble(netPrice.getText()), MathContext.DECIMAL64);
//        totalprc = new BigDecimal(Double.parseDouble(totaltax.getText()), MathContext.DECIMAL64);
//        netprc = new BigDecimal(Double.parseDouble(totalprice.getText()), MathContext.DECIMAL64);

        totaltaxs = new BigDecimal(BigInteger.ZERO);
        totalprc = new BigDecimal(BigInteger.ZERO);
        netprc = new BigDecimal(BigInteger.ZERO);
        if (challandataTable.getItems().size() > 0) {
            System.out.println("cata=>" + cata);
            double totalprice1 = cata.getProduct().getProdCost() - (cata.getProduct().getProdCost() * (tax / 100));
            String totalpricetxt = Double.toString(totalprice1);
            totalprice.setText(totalpricetxt);

            for (int j = 0; j < challandataTable.getItems().size(); j++) {
                ChallanModal get = challandataTable.getItems().get(j);
                if (get.getId().getValue() != delid) {
                    Double tax1 = get.getTax().getValue();
                    Double price = get.getPrice().getValue();
                    String productName = get.getProductName().getValue();
                    ProductsBL prodBL = new ProductsBL();
                    Products prod = prodBL.fetchProductByName(productName);
                    if (prod != null) {
                        double qty = get.getQuantity().getValue();
                        double discount = prod.getCategory().getDiscount();
                        totalprc = totalprc.add(new BigDecimal(price * qty, MathContext.DECIMAL64).setScale(6, RoundingMode.UP));
                        totaltaxs = totaltaxs.add(new BigDecimal((qty * tax1 * price) / 100, MathContext.DECIMAL64).setScale(6, RoundingMode.UP));
                        netprc = netprc.add(new BigDecimal(((qty * price) + ((qty * tax1 * price) / 100)) - (((qty * price * discount) / 100)), MathContext.DECIMAL64).setScale(6, RoundingMode.UP));
                    }
                }

            }
            totaltax.setText(totaltaxs.doubleValue() + "");
            totalprice.setText(netprc.doubleValue() + "");
            netPrice.setText(totalprc.doubleValue() + "");
        }
        //     challandataTable.scrollTo();
    }

    public void setChallanSettings(HomeController hc) {
        this.hc = hc;
        //hc.setChallanData();
        //challandataTable.setItems(hc.getChallanData());
    }

    public ObservableList<ChallanModal> getChallanData() {
        return challanData;
    }

    public void setChallanData(ObservableList<ChallanModal> challanData) {
        this.challanData = challanData;
    }

    public TableView<ChallanModal> getChallandataTable() {
        return challandataTable;
    }

    public void setChallandataTable(TableView<ChallanModal> challandataTable) {
        this.challandataTable = challandataTable;
    }

    public TextField getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(TextField totalprice) {
        this.totalprice = totalprice;
    }

    public TextField getTotaltax() {
        return totaltax;
    }

    public void setTotaltax(TextField totaltax) {
        this.totaltax = totaltax;
    }

    public TextField getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(TextField netPrice) {
        this.netPrice = netPrice;
    }

    public DateTimeFormatter getOfPattern() {
        return ofPattern;
    }

    public void setOfPattern(DateTimeFormatter ofPattern) {
        this.ofPattern = ofPattern;
    }

    public ComboBox<String> getProdNameId() {
        return prodNameId;
    }

    public void setProdNameId(ComboBox<String> prodNameId) {
        this.prodNameId = prodNameId;
    }

    public ComboBox<String> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ComboBox<String> categoryId) {
        this.categoryId = categoryId;
    }

    public ComboBox<String> getTaxDrpDown() {
        return taxDrpDown;
    }

    public void setTaxDrpDown(ComboBox<String> taxDrpDown) {
        this.taxDrpDown = taxDrpDown;
    }

    public ObservableList<String> getTaxesList() {
        return taxesList;
    }

    public void setTaxesList(ObservableList<String> taxesList) {
        this.taxesList = taxesList;
    }

    public ObservableList<String> getProdListData() {
        return prodListData;
    }

    public void setProdListData(ObservableList<String> prodListData) {
        this.prodListData = prodListData;
    }

    public BigDecimal getTotalprc() {
        return totalprc;
    }

    public void setTotalprc(BigDecimal totalprc) {
        this.totalprc = totalprc;
    }

    public BigDecimal getNetprc() {
        return netprc;
    }

    public void setNetprc(BigDecimal netprc) {
        this.netprc = netprc;
    }

    public BigDecimal getTotaltaxs() {
        return totaltaxs;
    }

    public void setTotaltaxs(BigDecimal totaltaxs) {
        this.totaltaxs = totaltaxs;
    }

    public HomeController getHc() {
        return hc;
    }

    public void setHc(HomeController hc) {
        this.hc = hc;
    }

    public TextField getClientName() {
        return clientName;
    }

    public void setClientName(TextField clientName) {
        this.clientName = clientName;
    }

    public TextField getDocName() {
        return docName;
    }

    public void setDocName(TextField docName) {
        this.docName = docName;
    }

    public DatePicker getDateid() {
        return dateid;
    }

    public void setDateid(DatePicker dateid) {
        this.dateid = dateid;
    }

    public DatePicker getShippingDateid() {
        return shippingDateid;
    }

    public void setShippingDateid(DatePicker shippingDateid) {
        this.shippingDateid = shippingDateid;
    }

    public TextArea getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(TextArea descriptionId) {
        this.descriptionId = descriptionId;
    }

    public ComboBox<String> getUomId() {
        return uomId;
    }

    public void setUomId(ComboBox<String> uomId) {
        this.uomId = uomId;
    }

    public TextField getDocNumId() {
        return docNumId;
    }

    public void setDocNumId(TextField docNumId) {
        this.docNumId = docNumId;
    }

    public TextArea getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(TextArea companyTitle) {
        this.companyTitle = companyTitle;
    }

    public TextField getQtyId() {
        return qtyId;
    }

    public void setQtyId(TextField qtyId) {
        this.qtyId = qtyId;
    }

    public TableColumn<ChallanModal, Integer> getSlNo() {
        return slNo;
    }

    public void setSlNo(TableColumn<ChallanModal, Integer> slNo) {
        this.slNo = slNo;
    }

    public TableColumn<ChallanModal, String> getDocNoId() {
        return docNoId;
    }

    public void setDocNoId(TableColumn<ChallanModal, String> docNoId) {
        this.docNoId = docNoId;
    }

    public TableColumn<ChallanModal, String> getDeescId() {
        return deescId;
    }

    public void setDeescId(TableColumn<ChallanModal, String> deescId) {
        this.deescId = deescId;
    }

    public TableColumn<ChallanModal, String> getProductId() {
        return productId;
    }

    public void setProductId(TableColumn<ChallanModal, String> productId) {
        this.productId = productId;
    }

    public TableColumn<ChallanModal, Double> getPriceId() {
        return priceId;
    }

    public void setPriceId(TableColumn<ChallanModal, Double> priceId) {
        this.priceId = priceId;
    }

    public TableColumn<ChallanModal, Double> getDiscountId() {
        return discountId;
    }

    public void setDiscountId(TableColumn<ChallanModal, Double> discountId) {
        this.discountId = discountId;
    }

    public TableColumn<ChallanModal, Double> getTaxId() {
        return taxId;
    }

    public void setTaxId(TableColumn<ChallanModal, Double> taxId) {
        this.taxId = taxId;
    }

    public TableColumn<ChallanModal, String> getDocNameId() {
        return docNameId;
    }

    public void setDocNameId(TableColumn<ChallanModal, String> docNameId) {
        this.docNameId = docNameId;
    }

    public TableColumn<ChallanModal, String> getCompanyTitleId() {
        return companyTitleId;
    }

    public void setCompanyTitleId(TableColumn<ChallanModal, String> companyTitleId) {
        this.companyTitleId = companyTitleId;
    }

    public TableColumn<ChallanModal, String> getDateId() {
        return dateId;
    }

    public void setDateId(TableColumn<ChallanModal, String> dateId) {
        this.dateId = dateId;
    }

    public TableColumn<ChallanModal, String> getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(TableColumn<ChallanModal, String> shippingDate) {
        this.shippingDate = shippingDate;
    }

    public TableColumn<ChallanModal, Double> getQuantityId() {
        return quantityId;
    }

    public void setQuantityId(TableColumn<ChallanModal, Double> quantityId) {
        this.quantityId = quantityId;
    }

    public TableColumn<ChallanModal, ChallanModal> getActionId() {
        return actionId;
    }

    public void setActionId(TableColumn<ChallanModal, ChallanModal> actionId) {
        this.actionId = actionId;
    }

    public ComboBox<String> getTaxidlist() {
        return taxidlist;
    }

    public void setTaxidlist(ComboBox<String> taxidlist) {
        this.taxidlist = taxidlist;
    }

    public ObservableList<String> getCatListData() {
        return catListData;
    }

    public void setCatListData(ObservableList<String> catListData) {
        this.catListData = catListData;
    }

    public ObservableList<String> getUomListData() {
        return uomListData;
    }

    public void setUomListData(ObservableList<String> uomListData) {
        this.uomListData = uomListData;
    }

    public Button getAddChallanBtn() {
        return addChallanBtn;
    }

    public void setAddChallanBtn(Button addChallanBtn) {
        this.addChallanBtn = addChallanBtn;
    }

    public Button getGenChallanBtn() {
        return genChallanBtn;
    }

    public void setGenChallanBtn(Button genChallanBtn) {
        this.genChallanBtn = genChallanBtn;
    }

    public Label getHeader() {
        return header;
    }

    public void setHeader(Label header) {
        this.header = header;
    }

    public Label getHeadertxt() {
        return headertxt;
    }

    public void setHeadertxt(Label headertxt) {
        this.headertxt = headertxt;
    }
}
