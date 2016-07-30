/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware;

import automatedbillingsoftware.helper.MachineIdentifierHelper;
import automattedbillingsoftware_BL.Company_BL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author user
 */
public class AutomatedBillingSoftware extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        String FXML_document = "LoginDocument.fxml";
//        if (MachineIdentifierHelper.getMachineIdentifierMap() == null || MachineIdentifierHelper.getMachineIdentifierMap().get("mcId") == null) 
//
        if ((MachineIdentifierHelper.getMachineIdentifierMap() == null
                || MachineIdentifierHelper.getMachineIdentifierMap().get("mcId") == null) && (!new Company_BL().isCompanyReg())) {
            System.out.println("level1");
            if ((!new automattedbillingsoftware_BL.AutomatedBillingSoftware_BL().isRegistered()) || (!new Company_BL().isCompanyReg())) {
                System.out.println("level2");
                FXML_document = "AdminInformationEntry.fxml";
            }
        }

//        String html = "src/resources/taxinvoice.html";
//
//        String htmlstr = readHtml(html);
//
//        Templete temp = new Templete();
//        temp.setName("taxinvoice");
//        byte[] bytes = htmlstr.getBytes();
//        Byte[] byteObjects = new Byte[bytes.length];
//
//        for (int i = 0; i < bytes.length; i++) {
//            byteObjects[i] = bytes[i];
//        }
//
//        temp.setHtmlTemplete(byteObjects);
//        temp.setStatus(1);
//
//        Templete_BL tempBL = new Templete_BL();
//        Templete addTemplete = tempBL.addTemplete(temp);
//
//        if (addTemplete != null) {
//            System.out.println("Add Templete=>" + addTemplete);
//        }
        Parent root = FXMLLoader.load(getClass().getResource(FXML_document));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //System.out.println("path=>" + this.getClass().getResource("image/billing.png"));
        stage.getIcons().add(new Image(this.getClass().getResource("image/billing.png").toString()));
        stage.show();
        stage.setOnCloseRequest(e -> handle(e));
    }

    public void handle(WindowEvent event) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        String htmlinvoice = readHtml("src/resources/taxinvoice.html");
//        String htmlchallan = readHtml("src/resources/challan.html");
//
//        Properties prop = new Properties();
//        prop.put("challan", htmlchallan);
//        prop.put("invoice", htmlinvoice);
//        
//        prop.store(new FileOutputStream(new File("Templete.properties")), null);
        launch(args);
    }

    public static String readHtml(String htmlPageName) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(htmlPageName));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String content = contentBuilder.toString();
        return content;
    }
}
