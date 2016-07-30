/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.UserSession;
import automatedbillingsoftware.modal.CategoryModal;
import automatedbillingsoftware.modal.ChallanModal;
import automatedbillingsoftware.modal.ProductModal;
import automatedbillingsoftware.modal.RetailInvoiceList;
import automatedbillingsoftware.modal.TaxModal;
import automatedbillingsoftware_modal.Categories;
import automatedbillingsoftware_modal.Challan;
import automatedbillingsoftware_modal.ChallanGenerated;
import automatedbillingsoftware_modal.Products;
import automatedbillingsoftware_modal.Tax;
import automattedbillingsoftware_BL.CategoriesBL;
import automattedbillingsoftware_BL.ChallanBL;
import automattedbillingsoftware_BL.Company_BL;
import automattedbillingsoftware_BL.ProductsBL;
import automattedbillingsoftware_BL.TaxBL;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import static java.time.Clock.system;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Arka
 */
public class HomeController implements Initializable {

    BorderPane initPane;

    @FXML
    ImageView imgId;

  

    @FXML
    Label Infomessage;

    @FXML
    BorderPane homeBorderPane;

    @FXML
    Button product_btn;

//    @FXML
//    private ImageView companyIco;
    @FXML
    Label message;

    private ObservableList<RetailInvoiceList> retailArrayList = FXCollections.observableArrayList();

    private ObservableList<CategoryModal> catData = FXCollections.observableArrayList();

    private ObservableList<TaxModal> taxesData = FXCollections.observableArrayList();

    private ObservableList<ProductModal> prodData = FXCollections.observableArrayList();

    private ObservableList<ChallanModal> challanData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            imgId.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    System.out.println("Tile pressed ");
                    Stage stage = (Stage) product_btn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("home.fxml"));
                    BorderPane pane;
                    try {
                        pane = loader.load();
                        homeBorderPane.setCenter(pane.getCenter());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    event.consume();
                }
            });

//            HashMap<String,Object> scopes =new HashMap<>();
//            scopes.put("challanNo","6578");
//            
//            ArrayList<HashMap<String,Object>> cList=new ArrayList<>();
//            HashMap<String,Object> hm=new HashMap<String,Object>();
//            hm.put("slNo", "1");
//            hm.put("desc", "ghvg");
//            cList.add(hm);
//            scopes.put("cList", cList);
//            HtmlToPdf1.Pdfconvert("resources/challan.html","resources/challan.pdf", scopes);
            initPane = homeBorderPane;
            setImage();
            message.setText("Hello " + UserSession.getCompany().getName());
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void addInvoice(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("RetailInvoice.fxml"));
        BorderPane taxesPane = loader.load();
        System.out.println("taxesPane=>" + taxesPane + "loader=>" + loader.getController());
        RetailInvoiceController invoicecontroller = loader.getController();

        addRetailList();
        invoicecontroller.setHomeController(this);

        Stage addDialogueStage = new Stage();
        addDialogueStage.setTitle("Invoice");
        addDialogueStage.initModality(Modality.WINDOW_MODAL);
        addDialogueStage.initOwner(homeBorderPane.getScene().getWindow());
        Scene sc = new Scene(taxesPane);

        addDialogueStage.setScene(sc);
        addDialogueStage.setResizable(false);
        addDialogueStage.setAlwaysOnTop(true);
        addDialogueStage.setFullScreen(false);
        addDialogueStage.showAndWait();
    }

    public void addRetailList() {
        ChallanBL challanBL = new ChallanBL();
        List<ChallanGenerated> allOrders = challanBL.fetchAllOrders();
        int i = 1;
        System.out.println("allOrders=>" + allOrders.size());
        retailArrayList = FXCollections.observableArrayList();
        for (ChallanGenerated cg : allOrders) {
            retailArrayList.add(new RetailInvoiceList(cg.getChallanList().get(0).getDocNo(), cg.getChallanList().get(0).getDocumentName(), cg.getChallanList().get(0).getClientName(),
                    new SimpleDateFormat("dd-MM-yyyy").format(cg.getDate()), cg.getId(), i, cg.getId()));
            ++i;
        }
        System.out.println("retailArrayList size=>" + retailArrayList.size());
    }

    @FXML
    public void User(ActionEvent event) throws IOException {
        handleUser(event);
    }

    @FXML
    public void handleUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("User.fxml"));
        BorderPane taxesPane = loader.load();
//        TaxesController taxcontroller = loader.getController();
//        setTaxesData();
//        taxcontroller.addTableList(this);
        Stage addDialogueStage = new Stage();
        addDialogueStage.setTitle("User");
        addDialogueStage.initModality(Modality.WINDOW_MODAL);
        addDialogueStage.initOwner(homeBorderPane.getScene().getWindow());
        Scene sc = new Scene(taxesPane);

        addDialogueStage.setScene(sc);
        addDialogueStage.setResizable(false);
        addDialogueStage.setAlwaysOnTop(true);
        addDialogueStage.setFullScreen(false);
        addDialogueStage.showAndWait();
    }

    private void setImage() {
        if (UserSession.getCompany().getLogo() != null) {
            try {
                System.out.println("image path=>" + UserSession.getCompany().getLogo());
                String logo = UserSession.getCompany().getLogo();
//                byte[] imgLogo = new byte[logo.length];
//
//                for (int i = 0; i < logo.length; i++) {
//                    imgLogo[i] = logo[i];
//                }
//
//                ByteArrayInputStream ipstream = new ByteArrayInputStream(imgLogo);
//                FileOutputStream fos = new FileOutputStream("");
//                String property = System.getProperty("user.dir");
//                fos.write(imgLogo);
//                fos.close();
                File file = new File(UserSession.getCompany().getLogo());
                System.out.println("logo path=>" + UserSession.getCompany().getLogo());
                Image img = new Image(file.toURI().toURL().toString(), 30, 30, true, true);
                imgId.setImage(img);
//                getCompanyIco().setImage(img);
//                getCompanyIco().setFitHeight(37);
//                getCompanyIco().setFitWidth(45);
//                getCompanyIco().setPreserveRatio(true);
//                getCompanyIco().setSmooth(true);
//                getCompanyIco().setCache(true);
                System.out.println("file=>" + file + "logo path=>" + UserSession.getCompany().getLogo());
                Infomessage.setText("");
                BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                Background background = new Background(backgroundImage);
//                background.getImages().remove(0);
//                background.getImages().add(0, backgroundImage);
                System.out.println("backgroundImage=>");
                // BufferedImage read = ImageIO.read(new File(UserSession.getCompany().getLogo()));
                // Image image = new Image(new FileInputStream(new File(UserSession.getCompany().getLogo())));

                // homebtn.setBackground(background);
                // UserSession.getCompany().getLogo().replace("\","/");
                // System.out.println("file path=>" + new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().toString().lastIndexOf("\\") - 1));
              //  homebtn.setStyle("-fx-background-image:url(file://" + UserSession.getCompany().getLogo() + ")");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void handlenewChallan(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("Challan.fxml"));
//        setCategoryData();
        BorderPane challanPane = (BorderPane) loader.load();
        ChallanController controller = loader.getController();
//         setProDataList();
//         setCategoryData();
//        controller.setProductsSettings(this);
//        controller.addTableList(this);
        homeBorderPane.setCenter(challanPane);
    }

    public void setChallanData() {
        ChallanBL cat = new ChallanBL();
        List<Challan> fetchCategoriesesList = cat.fetchChallanList();

        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        setChallanData(FXCollections.observableArrayList());
        for (Iterator<Challan> it = fetchCategoriesesList.iterator(); it.hasNext();) {

            Challan cata = it.next();
            getChallanData().add(new ChallanModal(cata.getId(), cata.getClientName(), cata.getDescription(),
                    cata.getDocumentName(), cata.getUom(), cata.getDocNo(), cata.getCompanyTitle(),
                    cata.getCategory().getCatName(), cata.getProduct().getProdName(),
                    cata.getProduct().getProdCost(), 0, cata.getQty(),
                    cata.getProduct().getTax().getTaxValue(), new SimpleDateFormat("dd-MM-yyyy").format(cata.getDate()),
                    new SimpleDateFormat("dd-MM-yyyy").format(cata.getDate())));

        }
    }

    public void setChallanData(ObservableList<ChallanModal> challanData) {
        this.challanData = challanData;
    }

    public ObservableList<ChallanModal> getChallanData() {
        return this.challanData;
    }

    @FXML
    public void addMenuProducts(ActionEvent event) throws IOException {
        addProducts(event);
    }

    @FXML
    public void addProducts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("product.fxml"));

//        setCategoryData();
        BorderPane categoriesPane = (BorderPane) loader.load();
        ProductController controller = loader.getController();
        setProDataList();
        controller.setProductsSettings(this);
//        controller.addTableList(this);

        homeBorderPane.setCenter(categoriesPane);
    }

    @FXML
    public void addMenuCategories(ActionEvent event) {
        addCategory(event);
    }

    @FXML
    public void addCategory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("categories.fxml"));

            setCategoryData();
            BorderPane categoriesPane = (BorderPane) loader.load();
            CategoriesController controller = loader.getController();
            controller.addTableList(this);

            homeBorderPane.setCenter(categoriesPane);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setCategoryData() {

        CategoriesBL cat = new CategoriesBL();
        List<Categories> fetchCategoriesesList = cat.fetchCategoriesesList();

        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        setCatData(FXCollections.observableArrayList());
        for (Iterator<Categories> it = fetchCategoriesesList.iterator(); it.hasNext();) {

            Categories cata = it.next();
            getCatData().add(new CategoryModal(cata.getCatid(), cata.getCatName(), cata.getCatDesc(), cata.getDiscount(), sf.format(cata.getCatModifiedDate())));

        }

//        getCatData().add(new CategoryModal(2, "catName", "cat Desc", Double.parseDouble("7587"), "10-2-2012"));
//        getCatData().add(new CategoryModal(3, "catName", "cat Desc", Double.parseDouble("7587"), "10-2-2012"));
//        getCatData().add(new CategoryModal(4, "catName", "cat Desc", Double.parseDouble("7587"), "10-2-2012"));
//    
    }

    @FXML
    public void addMenuTax(ActionEvent event) throws IOException {
        addTax(event);
    }

    @FXML
    public void addTax(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("taxes.fxml"));
        BorderPane taxesPane = loader.load();
        TaxesController taxcontroller = loader.getController();
        setTaxesData();
        taxcontroller.addTableList(this);
        Stage addDialogueStage = new Stage();
        addDialogueStage.setTitle("Taxes");
        addDialogueStage.initModality(Modality.WINDOW_MODAL);
        addDialogueStage.initOwner(homeBorderPane.getScene().getWindow());
        Scene sc = new Scene(taxesPane);

        addDialogueStage.setScene(sc);
        addDialogueStage.setResizable(false);
        addDialogueStage.setAlwaysOnTop(true);
        addDialogueStage.setFullScreen(false);
        addDialogueStage.showAndWait();
    }

    @FXML
    public void handleMouseClick(MouseEvent event) {
        homeBorderPane = initPane;
    }

    /**
     * @return the catData
     */
    public ObservableList<CategoryModal> getCatData() {
        return catData;
    }

//    @FXML
//    public void handleHome(ActionEvent event) throws IOException {
//        Stage stage = (Stage) product_btn.getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("home.fxml"));
//        BorderPane pane = loader.load();
//        homeBorderPane.setCenter(pane.getCenter());
//    }

    /**
     * @param catData the catData to set
     */
    public void setCatData(ObservableList<CategoryModal> catData) {
        this.catData = catData;
    }

    public ObservableList<TaxModal> getTaxesData() {
        return taxesData;
    }

    public void setTaxDataList(ObservableList<TaxModal> taxModal) {
        this.taxesData = taxModal;
    }

    public void setTaxesData() {
        TaxBL taxbl = new TaxBL();
        List<Tax> fetchAllTaxes = taxbl.fetchAllTaxes();
        setTaxDataList(FXCollections.observableArrayList());
        // getTaxesData().set(FXCollections.observableArrayList());
        for (Tax tax : fetchAllTaxes) {
            getTaxesData().add(new TaxModal(tax.getTaxId(), tax.getTaxName(), tax.getTaxValue(), new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a").format(tax.getTaxAdditionDate())));
        }
        // System.out.println("fetchAllTaxes=>" + fetchAllTaxes.size());
        //   this.taxesData = getTaxesData();
    }

    @FXML
    public void handleCompany(ActionEvent event) throws IOException {
        handleComp(event);
    }

    @FXML
    public void handleComp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("company.fxml"));
        BorderPane taxesPane = loader.load();
        CompanyController comcontroller = loader.getController();
        comcontroller.setHc(this);
//        setTaxesData();
//       taxcontroller.addTableList(this);
        Stage addDialogueStage = new Stage();
        addDialogueStage.setTitle("Company");
        addDialogueStage.initModality(Modality.WINDOW_MODAL);
        addDialogueStage.initOwner(homeBorderPane.getScene().getWindow());
        Scene sc = new Scene(taxesPane);

        addDialogueStage.setScene(sc);
        addDialogueStage.setResizable(false);
        addDialogueStage.setAlwaysOnTop(true);
        addDialogueStage.setFullScreen(false);
        addDialogueStage.showAndWait();
    }

    /**
     * @return the companyIco
     */
//    public ImageView getCompanyIco() {
//        return companyIco;
//    }
//
//    /**
//     * @param companyIco the companyIco to set
//     */
//    public void setCompanyIco(ImageView companyIco) {
//        this.companyIco = companyIco;
//    }
    @FXML
    private void setLogo(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an logo file");
        // fileChooser.setInitialDirectory(new File(".//res//"));
        boolean addAll = fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(Infomessage.getScene().getWindow());
        if (selectedFile != null) {
            BufferedImage read = ImageIO.read(selectedFile);
            String property = System.getProperty("user.dir");
//            //       System.out.println("selectedFile.AbsPath=>" + selectedFile.getAbsolutePath());
//            String logoFilePath = selectedFile.getName();
//            
//            
//            //      System.out.println("imageFile name=>" + logoFilePath.substring(0, logoFilePath.lastIndexOf(".")));
//
////            File f = new File(logoFilePath.substring(0, logoFilePath.lastIndexOf(".")));
//            File f = new File(logoFilePath);
//            byte[] fileData = new byte[(int) f.length()];
//            FileInputStream in = new FileInputStream(f);
//            in.read(fileData);
//
//            //     String absolutePath = f.getAbsolutePath();
////            System.out.println("absolutepathname=>" + absolutePath);
////            System.out.println("path of f=>" + f.getAbsolutePath());
////            boolean write = ImageIO.write(read, "png", f);
////            logoFilePath = new File(logoFilePath).getAbsolutePath();
////            System.out.println("logoFilePath=>" + logoFilePath);
//            Byte[] fileBytes = new Byte[fileData.length];
//            for (int i = 0; i < fileData.length; i++) {
//                fileBytes[i] = Byte.valueOf(fileData[i]);
//            }
            String pdfLoc = "" + property.substring(0, property.lastIndexOf("\\")) + "\\" + selectedFile.getName();
            try {
                BufferedImage readImg = ImageIO.read(selectedFile);
                boolean write = ImageIO.write(readImg, "png", new File(pdfLoc));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            UserSession.getCompany().setLogo(pdfLoc);

            Company_BL comp = new Company_BL();
            comp.updateCompany(UserSession.getCompany());
            setImage();
        }
    }

    public void showErrMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Field Value");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setProDataList() {
        ProductsBL prodBL = new ProductsBL();
        List<Products> fetchProdList = prodBL.fetchProdList();
        prodData.setAll(FXCollections.observableArrayList());
        for (Products prod : fetchProdList) {
            prodData.add(new ProductModal(prod.getProdid(), prod.getProdName(), prod.getProdDesc(), prod.getProdQty(), prod.getProdCost(), prod.getUom(), prod.getQrCode(), ((prod.getBarCode() == null) ? "" : prod.getBarCode()), new SimpleDateFormat("dd-MM-yyyy").format(prod.getDateOfAddition())));
        }
    }

    public ObservableList<ProductModal> getProdData() {
        return prodData;
    }

    public void setProdData(ObservableList<ProductModal> prodData) {
        this.prodData = prodData;
    }

    public ObservableList<RetailInvoiceList> getRetailArrayList() {
        return retailArrayList;
    }

    public void setRetailArrayList(ObservableList<RetailInvoiceList> retailArrayList) {
        this.retailArrayList = retailArrayList;
    }

    public void handleSignOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit ABS");
        alert.setContentText("Do you really want to quit");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) homeBorderPane.getScene().getWindow();
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }
}
