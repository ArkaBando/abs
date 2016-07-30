/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware.helper;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Arka
 */
public class HtmlToPdf1 {

    private static String DEST = "result/challan.pdf";
    private static String HTML = "resources/challan.html";

    public static boolean Pdfconvert(String htmlFileLoc, String pdfFileLoc, HashMap<String, Object> scopes) throws IOException, DocumentException {
        boolean isCreated = false;
        File file = new File(DEST);
        DEST = pdfFileLoc;
        HTML = htmlFileLoc;
        file.getParentFile().mkdirs();
        new HtmlToPdf1().createPdf(DEST,scopes);
        isCreated = true;
        return isCreated;
    }

    /**
     * Creates a PDF with the words "Hello World"
     *
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
    public void createPdf(String file,HashMap<String, Object> scopes) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        writer.setTagged();
        // step 3
        document.open();
        // step 4
        String readHtml = readHtml(HTML);
        String testTemplate = getTestTemplate(readHtml,scopes);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(testTemplate));

        // XMLWorkerHelper.getInstance().parseXHtml(writer, );
        // step 5
        document.close();
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

    public static String getTestTemplate(String templeString,HashMap<String, Object> scopes) {
     //   HashMap<String, Object> scopes = new HashMap<>();
//        ArrayList<HashMap<String, Object>> sites = new ArrayList<>();
//        double u = 8.9;
//
//        HashMap<String, Object> hm = new HashMap<>();
//        hm.put("sitename", u);
//        sites.add(hm);
//        u += 1;
//        hm = new HashMap<>();
//
//        hm.put("sitename", u);
//        sites.add(hm);
//        u += 1;
//        hm = new HashMap<>();
//        hm.put("sitename", u);
//        sites.add(hm);
//
//        scopes.put("sites", sites);
//        scopes.put("firstName", "arka");
        StringWriter writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(templeString), templeString);
        mustache.execute(writer, scopes);
        writer.flush();

        return writer.toString();
    }
}
