/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.modal.ChallanModal;
import automatedbillingsoftware.modal.RetailInvoiceList;
import automatedbillingsoftware_modal.Challan;
import automatedbillingsoftware_modal.ChallanGenerated;
import automatedbillingsoftware_modal.InvoiceReport;
import automattedbillingsoftware_BL.ChallanBL;
import automattedbillingsoftware_BL.InvoiceReport_BL;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class RetailInvoiceController implements Initializable {

    HomeController hm;

    ObservableList<String> docNoArrayList = FXCollections.observableArrayList();

    ObservableList<String> orderNoArrayList = FXCollections.observableArrayList();

    @FXML
    TableColumn<RetailInvoiceList, RetailInvoiceList> deleteId;

//    @FXML
//    ComboBox<String> docId;
//
//    @FXML
//    ComboBox<String> orderId;
//
//    @FXML
//    DatePicker frmDate;
//
//    @FXML
//    DatePicker toDate;

    @FXML
    TableColumn<RetailInvoiceList, Integer> slNo;

    @FXML
    TableColumn<RetailInvoiceList, String> date;

    @FXML
    TableColumn<RetailInvoiceList, String> docNo;

    @FXML
    TableColumn<RetailInvoiceList, String> clientName;

    @FXML
    TableColumn<RetailInvoiceList, String> docName;

    @FXML
    TableColumn<RetailInvoiceList, Integer> orderNo;

    @FXML
    TableColumn<RetailInvoiceList, RetailInvoiceList> action;

    @FXML
    TableView<RetailInvoiceList> invoiceTable;
    /**
     * Initializes the controller class.
     */
    ArrayList<String> documentNo = new ArrayList<>();

    ArrayList<String> ordList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        slNo.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        date.setCellValueFactory(cellData -> cellData.getValue().getDate());
        docNo.setCellValueFactory(cellData -> cellData.getValue().getDocumentNo());
        docName.setCellValueFactory(cellData -> cellData.getValue().getDocumentName());
        clientName.setCellValueFactory(cellData -> cellData.getValue().getcLientName());
        orderNo.setCellValueFactory(cellData -> cellData.getValue().getOrderNo().asObject());
//
//        frmDate.setValue(LocalDate.now());
//        toDate.setValue(LocalDate.now());
        ChallanBL challanBL = new ChallanBL();

        List<ChallanGenerated> allOrders = challanBL.fetchAllOrders();
        System.out.println("size=>" + allOrders.size());
        int i = 1;
        for (ChallanGenerated cg : allOrders) {

            ordList.add(cg.getId() + "");
            documentNo.add(cg.getChallanList().get(0).getDocNo() + ":" + cg.getChallanList().get(0).getDocumentName());
            ++i;
        }

        docNoArrayList.addAll(documentNo);
        orderNoArrayList.addAll(ordList);
//        orderId.setItems(orderNoArrayList);
//        docId.setItems(docNoArrayList);

        deleteId.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteId.setCellFactory(param -> new TableCell<RetailInvoiceList, RetailInvoiceList>() {
            private final Button delet = new Button("delete");

            @Override
            protected void updateItem(RetailInvoiceList person, boolean empty) {
                super.updateItem(person, empty);

                if (person == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(delet);
                delet.setOnAction(event -> {
                    invoiceTable.getItems().remove(person);
                    ChallanBL challanbl = new ChallanBL();
                    System.out.println("orderNo=>" + person.getOrderNo().getValue());
                    challanbl.deleteChallanGen(person.getOrderNo().getValue());
                });
            }

        });

        //  catChange.setMinWidth(40);
        action.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        action.setCellFactory(param -> new TableCell<RetailInvoiceList, RetailInvoiceList>() {
            private final Button genInvc = new Button("View Invoice");
//            private final Button delet = new Button("delete");

            @Override
            protected void updateItem(RetailInvoiceList person, boolean empty) {
                super.updateItem(person, empty);

                if (person == null) {
                    setGraphic(null);
                    return;
                }

//                setGraphic(delet);
//                delet.setOnAction(event -> {
//                    getTableView().getItems().remove(person);
//                });
                setGraphic(genInvc);
                genInvc.setOnAction(
                        event -> {
                            try {
                                int id = person.getOrderNo().getValue();
                                openChallanView(id);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                );

            }
        });
    }

    public void setHomeController(HomeController hm) {
        this.hm = hm;
        invoiceTable.setItems(FXCollections.observableArrayList());
        invoiceTable.setItems(hm.getRetailArrayList());
        //    System.out.println("invoiceTable=>" + invoiceTable.getSelectionModel().getSelectedItems().size());
    }

    public void openChallanView(int id) throws IOException {
        ChallanBL challanbl = new ChallanBL();
        ChallanGenerated challanGen = challanbl.fetchChallanGenerated(id);
        List<Challan> challanList = challanGen.getChallanList();

        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("Challan.fxml"));
        BorderPane challanPane = (BorderPane) loader.load();
        ChallanController chalanControler = (ChallanController) loader.getController();

        chalanControler.setChallanSettings(hm);
        ObservableList<ChallanModal> challanArrayList = FXCollections.observableArrayList();
        chalanControler.setChallanData(FXCollections.observableArrayList());
        chalanControler.getGri().disableProperty().set(false);
        chalanControler.getDocName().setText(challanGen.getChallanList().get(0).getDocumentName());
        chalanControler.getDocNumId().setText(challanGen.getChallanList().get(0).getDocNo());
        chalanControler.getDescriptionId().setText(challanGen.getChallanList().get(0).getDescription());
        chalanControler.getDateid().setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(challanGen.getDate())));
        chalanControler.getClientName().setText(challanGen.getChallanFor());
        chalanControler.getCompanyTitle().setText(challanGen.getCompanyName());
        chalanControler.getQtyId().setText(challanGen.getChallanList().get(0).getQty() + "");
        chalanControler.getProdNameId().setValue(challanGen.getChallanList().get(0).getProduct().getProdName());
        chalanControler.getAddChallanBtn().setVisible(false);
        chalanControler.getGenChallanBtn().setVisible(false);
        chalanControler.getActionId().setVisible(false);
        chalanControler.getHeader().setText("INVOICE");
        chalanControler.getHeadertxt().setText("RETAIL INVOICE");
        chalanControler.setChallanGen(challanGen);
        //chalanControler.getDiscountList().set(0, Double.NaN)
        //chalanControler.getCategoryId().setItems();
        int i = 1;
        for (Challan challan : challanList) {
            challanArrayList.add(new ChallanModal(i, challanGen.getChallanFor(), challan.getDescription(), challan.getDocumentName(), challan.getUom(), challan.getDocNo(), challan.getCompanyTitle(),
                    challan.getCategory().getCatName(), challan.getProduct().getProdName(), challan.getProduct().getProdCost(), challan.getProduct().getCategory().getDiscount(), challan.getQty(),
                    challan.getTax().getTaxValue(), new SimpleDateFormat("dd-MM-yyyy").format(challan.getDate()), new SimpleDateFormat("dd-MM-yyyy").format(challanGen.getDeliveryDate())));

            i++;
        }
        TableView<ChallanModal> tableView = new TableView<>();
        tableView.setItems(challanArrayList);
        TableView<ChallanModal> challandataTable = chalanControler.getChallandataTable();
        challandataTable.setItems(challanArrayList);

        //   Window window = hm.homeBorderPane.getScene().getWindow();
        hm.homeBorderPane.setCenter(challanPane);
        Stage stage = (Stage) invoiceTable.getScene().getWindow();
        stage.close();
    }

//    public void handleSearchRetailInvoice(ActionEvent event) {
//
//        LocalDate frm_Date = frmDate.getValue();
//        java.util.Date frmdate = new java.util.Date(java.sql.Date.valueOf(frm_Date).getTime());
//        LocalDate to_Date = toDate.getValue();
//        java.util.Date todate = new java.util.Date(java.sql.Date.valueOf(to_Date).getTime());
//        String documentName = (docId.getSelectionModel() == null) ? "" : docId.getSelectionModel().getSelectedItem();
//        int order_Id = Integer.parseInt(orderId.getValue() == null ? "0" : orderId.getSelectionModel().getSelectedItem());
//        List<InvoiceReport> fetchInvoiceReports = new InvoiceReport_BL().fetchInvoiceReports(frmdate, todate, documentName.split(":")[1], order_Id, 0);
//        ChallanGenerated cg = fetchInvoiceReports.get(0).getChallanGen();
//        ObservableList<Object> observableArrayList = FXCollections.observableArrayList();
//        ObservableList<RetailInvoiceList> retailArrayList = FXCollections.observableArrayList();
//        invoiceTable.setItems(retailArrayList);
//        retailArrayList.add(new RetailInvoiceList(cg.getChallanList().get(0).getDocNo(), cg.getChallanList().get(0).getDocumentName(), cg.getChallanList().get(0).getClientName(),
//                new SimpleDateFormat("dd-MM-yyyy").format(cg.getDate()), cg.getId(), 0, cg.getId()));
//
//        invoiceTable.setItems(retailArrayList);
//
//        //  System.out.println("fetchInvoiceReports=>" + fetchInvoiceReports.size());
//    }
}
