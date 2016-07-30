/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.CurrencyToWordsConverter;
import automatedbillingsoftware.helper.HtmlToPdf;
import automatedbillingsoftware.helper.UserSession;
import automatedbillingsoftware_modal.Challan;
import automatedbillingsoftware_modal.ChallanGenerated;
import automatedbillingsoftware_modal.InvoiceReport;
import automattedbillingsoftware_BL.InvoiceReport_BL;
import com.itextpdf.text.DocumentException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Arka
 */
public class GenRetailInvoiceController implements Initializable {

    @FXML
    private TextField billNo;

    @FXML
    private TextField vatRate;

    @FXML
    private TextArea siteAddr;

    @FXML
    private TextField order_No;

    @FXML
    private TextField emailid;

    @FXML
    private Label errMsg;

    private ChallanGenerated challanGen;

    private String billNumber;

    private String orderNo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String billNo = UUID.randomUUID().toString();
        billNumber = billNo;
        this.order_No.setText(orderNo);
        order_No.setDisable(true);
        this.billNo.setText(billNo);
        this.billNo.setDisable(true);
    }

    public void showErrMessage(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Invalid Field Value");
//        alert.setContentText(message);
//        alert.showAndWait();

        errMsg.setText(message);
    }

    @FXML
    private void handleGenReport(ActionEvent event) throws IOException, DocumentException {

        try {

            if (vatRate.getText().isEmpty()) {
                showErrMessage("Please Enter Vat Rate");
            } else if (siteAddr.getText().isEmpty()) {
                showErrMessage("Please Enter Site Address");
            } else if (emailid.getText().isEmpty()) {
                showErrMessage("Please Enter Email Address");
            } else {
                Date billDate = new Date();
                InvoiceReport invReport = new InvoiceReport();
                invReport.setBillNo(billNumber);
                invReport.setVatNo(Double.parseDouble(vatRate.getText()));
                invReport.setEmail(emailid.getText());
                invReport.setOrderNo(challanGen.getId());
                invReport.setBillDate(billDate);
                invReport.setChallanGen(challanGen);
                invReport.setStatus(1);

                DirectoryChooser directoryChooser = new DirectoryChooser();
                //    directoryChooser.setInitialDirectory(new File(".\\res\\"));
                directoryChooser.setTitle("Select InvoiceReport file save location");
                File selectedDirectory
                        = directoryChooser.showDialog(order_No.getScene().getWindow());
                String path = "";
                if (selectedDirectory == null) {
                    showErrMessage("Please select an directory for storing Report");
                } else {
                    path = selectedDirectory.getAbsolutePath();
                }
                HashMap<String, Object> scopes = new HashMap<>();
                scopes.put("companyName", challanGen.getCompanyName());
                scopes.put("address", challanGen.getAddress());
                scopes.put("phone", UserSession.getCompany().getPhone());
                scopes.put("email", emailid.getText());
                scopes.put("ms", challanGen.getChallanFor());
                scopes.put("orderDate", new SimpleDateFormat("dd-MM-yyyy").format(challanGen.getDate()));
                scopes.put("billdate", new SimpleDateFormat("dd-MM-yyyy").format(billDate));
                scopes.put("billNo", billNumber.subSequence(0, 7));
                scopes.put("orderNo", challanGen.getId());
                scopes.put("siteAddress", siteAddr.getText());
                scopes.put("vat", challanGen.getVatNo());
                scopes.put("cst", challanGen.getCSTNo());

                BigDecimal totalamnt = new BigDecimal(BigInteger.ZERO);

                ArrayList<HashMap<String, Object>> invRptList = new ArrayList<>();
                List<Challan> challanList = challanGen.getChallanList();
                for (int i = 0; i < challanList.size(); i++) {
                    Challan challan = challanList.get(i);
                    HashMap<String, Object> hm = new HashMap<>();
                    hm.put("slNo", i + 1);
                    hm.put("desc", challan.getDescription());
                    hm.put("qty", challan.getQty());
                    hm.put("rate", challan.getProduct().getProdCost());
                    hm.put("vatRate", vatRate.getText() + "%");
                    double vatrate = Double.parseDouble(vatRate.getText());
                    double vatamnt = ((challan.getProduct().getProdCost() * challan.getQty()) * ((double) vatrate / (double) 100));
                    System.out.println(challan.getProduct().getProdid() + "tax=>" + challan.getProduct().getTax());
                    double taxamnt = ((challan.getProduct().getTax().getTaxValue() * challan.getProduct().getProdCost() * challan.getQty()) / 100);
                    double discamnt = ((challan.getProduct().getCategory().getDiscount() * challan.getProduct().getProdCost() * challan.getQty()) / 100);
                    double totamt = (challan.getProduct().getProdCost() * challan.getQty()) + vatamnt + taxamnt - discamnt;
                    totalamnt = totalamnt.add(new BigDecimal(totamt).setScale(4, RoundingMode.HALF_EVEN));
                    hm.put("totamnt", new BigDecimal(totamt).setScale(2, RoundingMode.HALF_EVEN).toString());
                    hm.put("vatAmnt", new BigDecimal(vatamnt).setScale(2, RoundingMode.HALF_EVEN).toString());
                    hm.put(path, i);

                    invRptList.add(hm);
                }
                scopes.put("totAmnt", totalamnt);
                scopes.put("invRptList", invRptList);
                scopes.put("totAmnts", totalamnt.setScale(2, RoundingMode.HALF_EVEN).doubleValue());
                String convertedCurrency = CurrencyToWordsConverter.convertCurrency(totalamnt.setScale(2, RoundingMode.HALF_EVEN).doubleValue() + "");
                StringBuilder sb = new StringBuilder();
                int x = 1;
                List<String> wordList = new LinkedList<>();
                for (String s : convertedCurrency.split(" ")) {
                    sb.append(s).append(" ");
                    if (x % 3 == 0) {
                        wordList.add(sb.toString());
                        sb = new StringBuilder();
                    }
                    x++;
                }
                sb.append(" Only");
                System.out.println("wordList=>" + wordList);
                for (String str : wordList) {
                    System.out.println("str=>" + str);
                }
                scopes.put("amntInWords", wordList.get(0));
                if (wordList.size() > 1) {
                    scopes.put("amntInWords1", wordList.get(1));
                }

                List<HashMap> lst = new LinkedList<>();

                for (int i = 2; i < wordList.size(); i++) {
                    HashMap hm = new HashMap();
                    hm.put("amnt", wordList.get(i));
                    lst.add(hm);
                }

                scopes.put("amntList", lst);

                boolean generatePdf = HtmlToPdf.generatePdf(path + "\\" + "Invoice-" + challanGen.getChallanFor() + "-" + challanGen.getCompanyName() + "-" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf", "src/resources/taxinvoice.html", scopes);
                if (generatePdf) {
                    String pdfFileLoc = path + "\\" + "Invoice-" + challanGen.getChallanFor() + "-" + challanGen.getCompanyName() + "-" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf";

                    if (Desktop.isDesktopSupported()) {
                        try {
                            File myFile = new File(pdfFileLoc);
                            Desktop.getDesktop().open(myFile);
                        } catch (IOException ex) {
                            ex.printStackTrace();               // no application registered for PDFs
                        }
                    }

                    InvoiceReport_BL invRptBL = new InvoiceReport_BL();
                    invRptBL.addInvoiceReport(invReport);

                    Stage stage = (Stage) billNo.getScene().getWindow();
                    stage.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setContentText("Invoice Report Generated");
                    alert.showAndWait();

                } else {
                    showErrMessage("OOps SOme Error ocurred while creating invoice report");
                }
            }
        } catch (Exception ex) {
            Stage stage = (Stage) billNo.getScene().getWindow();
            stage.close();

            Alert alert = new Alert(AlertType.ERROR);
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

    public TextField getBillNo() {
        return billNo;
    }

    public void setBillNo(TextField billNo) {
        this.billNo = billNo;
    }

    public TextField getVatRate() {
        return vatRate;
    }

    public void setVatRate(TextField vatRate) {
        this.vatRate = vatRate;
    }

    public TextArea getSiteAddr() {
        return siteAddr;
    }

    public void setSiteAddr(TextArea siteAddr) {
        this.siteAddr = siteAddr;
    }

    public TextField getOrder_No() {
        return order_No;
    }

    public void setOrder_No(TextField order_No) {
        this.order_No = order_No;
    }

    public ChallanGenerated getChallanGen() {
        return challanGen;
    }

    public void setChallanGen(ChallanGenerated challanGen) {
        this.challanGen = challanGen;
        orderNo = challanGen.getId() + "";
    }
}
