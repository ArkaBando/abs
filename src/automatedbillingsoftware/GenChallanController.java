/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.HtmlToPdf;
import automatedbillingsoftware.helper.UserSession;
import automatedbillingsoftware.modal.ChallanModal;
import automatedbillingsoftware_modal.Challan;
import automatedbillingsoftware_modal.ChallanGenerated;
import automatedbillingsoftware_modal.Company;
import automatedbillingsoftware_modal.Products;

import automattedbillingsoftware_BL.ChallanBL;
import automattedbillingsoftware_BL.ProductsBL;
import com.itextpdf.text.DocumentException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class GenChallanController implements Initializable {

    public class Values {

        private String slNo;
        private String desc;

        public Values(String slNo, String desc, String qty, String rate) {
            this.slNo = slNo;
            this.desc = desc;
            this.qty = qty;
            this.rate = rate;
        }

        public Values() {
        }
        private String qty;
        private String rate;

        public String getSlNo() {
            return slNo;
        }

        public void setSlNo(String slNo) {
            this.slNo = slNo;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }

    private ChallanController cc;

    @FXML
    private Label errMsg;

    @FXML
    private TextField challanNoId;

    @FXML
    private TextField companyName;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;

    @FXML
    private TextField cstno;

    @FXML
    private TextField vatno;

    @FXML
    private DatePicker date;

    @FXML
    private DatePicker deliveryId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        UUID randomUUID = UUID.randomUUID();
        String challanNo = randomUUID.toString();

        challanNoId.setText(challanNo);
        Company company = UserSession.getCompany();
        companyName.setText(company.getCompanyName());
        address.setText(company.getAddress() == null || company.getAddress().isEmpty() ? "" : company.getAddress());
        date.setValue(LocalDate.now());
        deliveryId.setValue(LocalDate.now());
        vatno.setText(company.getVatNo() == null || company.getVatNo().isEmpty() ? "" : company.getVatNo());
        phone.setText(company.getPhone() == null || company.getPhone().isEmpty() ? "" : company.getPhone());
    }

    public ChallanController getCc() {
        return cc;
    }

    public void setCc(ChallanController cc) {
        this.cc = cc;
    }
    String pdfLoc = "";

    @FXML
    private void handleGenerateChallan(ActionEvent ae) throws IOException, DocumentException {

        try {
            if (date.getValue() == null) {
                showErrMessage("Please Enter Date Value");
            } else if (deliveryId.getValue() == null) {
                showErrMessage("Please Enter Delivery Date Value");
            } else if (challanNoId.getText().isEmpty()) {
                showErrMessage("Please Enter challanNo");
            } else if (companyName.getText().isEmpty()) {
                showErrMessage("Please Enter Company Name");
            } else if (address.getText().isEmpty()) {
                showErrMessage("Please Enter Address ");
            } else if (phone.getText().isEmpty()) {
                showErrMessage("Please enter phone number");
            } else if (cstno.getText().isEmpty()) {
                showErrMessage("Please enter your CSTNo");
            } else if (vatno.getText().isEmpty()) {
                showErrMessage("Please Enter VatNo");
            } else {

                DirectoryChooser directoryChooser = new DirectoryChooser();
                //   directoryChooser.setInitialDirectory(new File(".\\res\\"));
                directoryChooser.setTitle("Select Challan file save location");
                File selectedDirectory
                        = directoryChooser.showDialog(cstno.getScene().getWindow());
                String path = "";
                if (selectedDirectory == null) {
                    showErrMessage("Please select an directory for storing Challan File");
                } else {
                    path = selectedDirectory.getAbsolutePath();
                }

                TableView<ChallanModal> challandataTable = cc.getChallandataTable();

                List<Challan> challanList = new ArrayList<Challan>();
                List<Object> cList = new ArrayList<>();
                ObservableList<ChallanModal> items = challandataTable.getItems();
                for (int i = 0; i < challandataTable.getItems().size(); i++) {
                    ChallanModal challanModal = items.get(i);
                    int id = challanModal.getId().getValue();
                    Values v = new Values();

                    HashMap<Object, Object> hm = new HashMap<>();
//                v.setSlNo(i+1);
//                v.setDesc(challanModal.getDescription().getValue());
//                v.setQty(challanModal.getQuantity().getValue());
//                v.setRate(challanModal.getPrice().getValue());
                    hm.put("slNo", (i + 1) + "");
//                hm.put("desc", "23.876");
                    hm.put("desc", challanModal.getDescription().getValue());
                    // double qty = ;
                    double rate = challanModal.getPrice().getValue();
                    hm.put("qty", challanModal.getQuantity().getValue());
                    hm.put("rate", challanModal.getPrice().getValue());
                    ProductsBL prodBL = new ProductsBL();
                    Products prod = prodBL.fetchProductByName(challanModal.getProductName().getValue());
                    prod.setProdQty(prod.getProdQty() - challanModal.getQuantity().getValue());
                    prodBL.updateProduct(prod);
                    cList.add(hm);

                    ChallanBL challanbl = new ChallanBL();
                    Challan challanObj = challanbl.fetchChallanById(id);
                    challanList.add(challanObj);
                }
                Date date1 = new Date();

                {
                    ChallanGenerated challanGenerated = new ChallanGenerated();
                    challanGenerated.setAddress(address.getText());
                    challanGenerated.setCSTNo(cstno.getText());
                    challanGenerated.setChallanFor(challanList.get(0).getClientName());
                    challanGenerated.setChallanList(challanList);

                    date1.setTime(java.sql.Date.valueOf(date.getValue()).getTime());

                    challanGenerated.setDate(date1);

                    date1.setTime(java.sql.Date.valueOf(deliveryId.getValue()).getTime());
                    challanGenerated.setDeliveryDate(date1);
                    challanGenerated.setPhone(phone.getText());
                    challanGenerated.setVatNo(vatno.getText());
                    challanGenerated.setCompanyName(companyName.getText());
                    challanGenerated.setChallanNo(challanNoId.getText());

                    ChallanBL challanBL = new ChallanBL();
                    ChallanGenerated saveChallan = challanBL.saveChallan(challanGenerated);
                    Stage stage = (Stage) challanNoId.getScene().getWindow();
                    stage.close();

                    HashMap<String, Object> scopes = new HashMap<>();
                    scopes.put("challanNo", challanNoId.getText().substring(0, 7));
                    scopes.put("orderNo", saveChallan.getId());
                    scopes.put("date", new SimpleDateFormat("dd-MM-yyyy").format(new Date(date1.getTime())));
                    scopes.put("ms", saveChallan.getChallanFor());
                    scopes.put("companyName", companyName.getText());
                    scopes.put("address", address.getText());
                    scopes.put("phone", phone.getText());
                    Date date2 = new Date(java.sql.Date.valueOf(deliveryId.getValue()).getTime());
                    scopes.put("delivaryDate", new SimpleDateFormat("dd-MM-yyyy").format(date2));
                    scopes.put("vat", vatno.getText());
                    scopes.put("cst", cstno.getText());
                    scopes.put("cList", cList);
                    //   scopes.put("cList", new Values("khj", "qwe", "gf", "jhgvjh"));
                    scopes.put("ttax", cc.getTotaltax().getText());
                    scopes.put("ttval", cc.getTotalprice().getText());
                    // HtmlToPdf.main();
                    boolean generatePdf = false;

                    try {
                        pdfLoc = path + "\\Challan-" + challanList.get(0).getClientName() + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf";
                        generatePdf = HtmlToPdf.generatePdf(path + "\\Challan-" + challanList.get(0).getClientName() + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf", "src/resources/challan.html", scopes);
//                        if (generatePdf) {
//                          
//                        }
                    } catch (Exception ex) {
                        Stage stage1 = (Stage) address.getScene().getWindow();
                        stage1.close();

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Exception ");
                        alert.setHeaderText("Exception while Generating invoice pdf");
                        alert.setContentText(ex.getLocalizedMessage());

                        //   Exception ex = new FileNotFoundException("Could not find file blabla.txt");
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
                    }
//            } else {
//                showErrMessage("Error while parsing pdf");
//            }

                    if (generatePdf) {
                        if (Desktop.isDesktopSupported()) {
                            try {
                                File myFile = new File(pdfLoc);
                                Desktop.getDesktop().open(myFile);
                            } catch (IOException ex) {
                                ex.printStackTrace();               // no application registered for PDFs
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Stage stage = (Stage) address.getScene().getWindow();
            stage.close();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception ");
            alert.setHeaderText("Exception while Generating invoice pdf");
            alert.setContentText(ex.getLocalizedMessage());

            //   Exception ex = new FileNotFoundException("Could not find file blabla.txt");
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
        }
    }

    public void showErrMessage(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Invalid Field Value");
//        alert.setContentText(message);
//        alert.showAndWait();

        errMsg.setText(message);
    }

}
