/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware.helper;

import automatedbillingsoftware_modal.Templete;
import automattedbillingsoftware_BL.Templete_BL;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author Arka
 */
public class HtmlToPdf {

    public static String DEST = "resources/challan.pdf";
    public static String HTML = "src/resources/challan.html";

//        public static void main(String[] args) throws IOException, DocumentException {
//            File file = new File(DEST);
//            file.getParentFile().mkdirs();
//            new testhtmltopdf().createPdf(DEST);
//        }
    /**
     * Creates a PDF with the words "Hello World"
     *
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
    public static void main() throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new HtmlToPdf().createPdf(DEST, new HashMap<String, Object>());
    }

    public static boolean generatePdf(String path, String html, HashMap<String, Object> scopes) throws IOException, DocumentException {
        DEST = path;
        HTML = html;

//        String htmlReaded = "";
//
//        Templete_BL tempBL = new Templete_BL();
//
//        if (path.contains("challan")) {
//            Templete challanTemplete = tempBL.fetchTempleteByName("challan");
//            Byte[] htmlTemplete = challanTemplete.getHtmlTemplete();
//            byte[] htmlByte = new byte[htmlTemplete.length];
//            for (int k = 0; k < htmlTemplete.length; k++) {
//                htmlByte[k] = htmlTemplete[k];
//            }
//            htmlReaded = new String(htmlByte);
//
//        } else if (path.contains("taxinvoice")) {
//            Templete invoiceTemplete = tempBL.fetchTempleteByName("taxinvoice");
//            Byte[] htmlTemplete = invoiceTemplete.getHtmlTemplete();
//            byte[] htmlByte = new byte[htmlTemplete.length];
//            for (int k = 0; k < htmlTemplete.length; k++) {
//                htmlByte[k] = htmlTemplete[k];
//            }
//            htmlReaded = new String(htmlByte);
//        }
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new HtmlToPdf().createPdf(DEST, scopes);
        return true;
    }

    public void createPdf(String file, HashMap<String, Object> scopes) throws IOException, DocumentException {
        // step 1
        Document document = new Document(PageSize.A4);

        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        writer.setTagged();
        // step 3
        document.open();

        // step 4
//        String readHtml = readHtml(HTML);
        System.out.println("challan" + DEST.contains("Challan-"));
        String readHtml = DEST.contains("Challan-") ? getDefaultTempProperties().getProperty("challan")
                : getDefaultTempProperties().getProperty("invoice");
        System.out.println("readHtml=>" + readHtml);
        String testTemplate = getTestTemplate(readHtml, scopes);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(testTemplate));

        //  XMLWorkerHelper.getInstance().parseXHtml(writer, );
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

    public static String getTestTemplate(String templeString, HashMap<String, Object> scopes) {
        StringWriter writer = new StringWriter();

//       HashMap<String, Object> scopes = new HashMap<>();
//        ArrayList<HashMap<String, String>> sites = new ArrayList<>();
//
//        HashMap<String, String> hm = new HashMap<>();
//        hm.put("sitename", "site1");
//        sites.add(hm);
//
//        hm = new HashMap<>();
//        hm.put("sitename", "site1");
//        sites.add(hm);
//
//        hm = new HashMap<>();
//        hm.put("sitename", "site1");
//        sites.add(hm);
//
//        scopes.put("sites", sites);
//        scopes.put("firstName", "arka");
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(templeString), templeString);
        StringWriter execute = (StringWriter) mustache.execute(writer, scopes);

        writer.flush();

        return writer.toString();
    }

    public static void setDefaultTemplete() throws IOException {
        String challan = "<html>    <head>    </head>    <body style='font-family: Verdana;font-size: 10px;'>        <table border='0' width=\"95%\" align=\"center\" style=' border-collapse: collapse;'>            <tr style=\" white-space: nowrap;line-height: 0px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" style=\"font-family: Verdana;font-size: 12px;\">Challan No                 </td>                <td class=\"col\" align=\"left\" width='10%' style=\"font-family: Verdana;font-size: 12px;white-space: nowrap;margin-left:10%;\">                    {{challanNo}}                </td>                <td class=\"col\" height=\"10\" align=\"center\" style=\"white-space: nowrap;font-weight:bolder;font-family: Verdana;font-size: 12px;\" width=\"27%\"><u>CHALLAN</u></td>                 <td class=\"col\" height=\"10\"  align=\"left\" width=\"14%\" style=\"white-space: nowrap; margin-right: 10%;font-family: Verdana;font-size: 12px;\">Your Order No</td>                <td class=\"col\" height=\"10\"  align=\"right\" width=\"10%\" style=\"white-space: nowrap;margin-left:10%;font-family: Verdana;font-size: 12px;white-space: nowrap; margin-right: 10%;\">{{orderNo}}</td>            </tr>            <tr style=\" height:25px;white-space: nowrap;line-height: 0px;\">                <td class=\"col\" align=\"left\" height=\"10\" style=\"white-space: nowrap;margin-left:10%;\" width=\"10%\" >                 </td>                <td class=\"col\" align=\"left\" width='10%' style=\"white-space: nowrap;margin-left:10%;\">                </td>                <td class=\"col\" height=\"10\" align=\"center\" style=\"white-space: nowrap;font-weight:bolder;\" width=\"27%\"><u></u></td>                 <td class=\"col\" height=\"10\"  align=\"left\" width=\"14%\" style=\"padding-left:10%;white-space: nowrap;\"></td>                <td class=\"col\" height=\"10\"  align=\"right\" width=\"10%\" style=\"white-space: nowrap;white-space: nowrap;\"></td>            </tr>                 </table>        <table border=\"0\"  width=\"98%\" align=\"center\" style=' border-collapse: collapse;'>            <tr style=\"height:25px;\">                <td  class=\"col\" width=\"5%\" align=\"left\"></td>                <td class=\"col\" width=\"15%\" align=\"left\" ></td>                <td   class=\"col\" width=\"5%\" align='center' ></td>                <td  class=\"col\" width=\"13%\" ></td>                <td class=\"col\" width=\"7%\" align=\"left\"></td>                <td class=\"col\" align=\"left\" width=\"13%\" ></td>            </tr>            <tr style=\"height:25px;\">                <td  class=\"col\" width=\"4%\" align=\"left\" style=\"font-family: Verdana;font-size: 12px;\">Date</td>                <td class=\"col\" width=\"4%\" align=\"left\" style=\"white-space: nowrap;font-family: Verdana;font-size: 12px;white-space: nowrap;\">{{date}}</td>                <td   class=\"col\" width=\"8%\" align='left' style=\"font-family: Verdana;font-size: 12px;white-space: nowrap; margin-right: 10%; font:  bold 30px Verdana, serif;\"></td>                <td  class=\"col\" width=\"59%\" ></td>                <td class=\"col\" width=\"4%\" align=\"left\" style=\"font-family: Verdana;font-size: 12px;\">Date</td>                <td class=\"col\" align=\"left\" width=\"13%\" style=\"font-family: Verdana;font-size: 12px;white-space: nowrap;white-space: nowrap;\">{{date}}</td>            </tr>            <tr style=\"height:25px;\">                <td  class=\"col\" width=\"5%\" align=\"left\"></td>                <td class=\"col\" width=\"15%\" align=\"left\" ></td>                <td   class=\"col\" width=\"5%\" align='center' ></td>                <td  class=\"col\" width=\"13%\" ></td>                <td class=\"col\" width=\"7%\" align=\"left\"></td>                <td class=\"col\" align=\"left\" width=\"13%\" ></td>            </tr>        </table>        <table border='0' width=\"100%\" align=\"center\" style=' border-collapse: collapse;'>            <tr style=\"height:25px;\">                <td  class=\"col\" width=\"10%\"></td>                <td   class=\"col\" width=\"80%\" align='center' style=\"white-space: nowrap; margin-right: 10%; font:  bold 30px Verdana, serif;\">{{companyName}}</td>                <td  class=\"col\" width=\"10%\"></td>            </tr>            <tr style=\"height:25px;\">                <td  class=\"col\"  width=\"10%\"></td>                <td  class=\"col\" width=\"80%\" align='center' style=\"white-space: nowrap; margin-right: 10%; font: 13px Verdana, serif;\"><span style='font:bold'> Head Office</span>:{{address}}</td>                <td  class=\"col\" width=\"10%\"></td>            </tr>            <tr style=\"height:25px;\">                <td  class=\"col\" width=\"10%\"></td>                <td  class=\"col\" width=\"80%\" align='center' style=\"white-space: nowrap; margin-right: 10%; font:   13px Verdana, serif;\">Phone : {{phone}}</td>                <td  class=\"col\"  width=\"10%\"></td>            </tr>        </table>        <table align='center' width='98%'>            <tr>                <td  class=\"col\"  width=\"5%\">M/s</td>                <td class='col' width='95%' style=\" font:10px Verdana, serif;white-space: nowrap;margin-left:10%;white-space: nowrap; margin-right: 10%;\">{{ms}}</td>            </tr>            <tr style=\"height:25px;\">                <td colspan=\"2\" class='col' width='100%' style=\"font:10px Verdana, serif;white-space: nowrap;margin-left:10%;white-space: nowrap; margin-right: 10%;\" ></td>            </tr>            <tr style=\"height:25px;\">                <td  class=\"col\"  width=\"5%\"></td>                <td class='col' width='95%' ></td>            </tr>            <tr style=\"height:25px;\">                <td  class=\"col\"  width=\"5%\"></td>                <td class='col' width='95%' ></td>            </tr>        </table>        <br/>        <br/>        <table width=\"97%\" align=\"center\" style=' border-collapse: collapse;'>            <tr >                <td  class=\"col\" width=\"5%\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-left: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\" align=\"center\">Sl. No</td>                <td class=\"col\" width=\"70%\" align=\"center\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\">DESCRIPTION</td>                <td class=\"col\" width=\"15%\" align=\"center\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\">Quantity</td>                <td class=\"col\" width=\"10%\" align=\"center\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\">Rate</td>            </tr>            <tr style=\"border:none;height:25px;\" >                <td class=\"col\" width=\"5%\" style=\" border-left: 1px solid black; border-right: 1px solid black; \" align=\"center\"></td>                <td class=\"col\" width=\"70%\" style=\" border-right: 1px solid black; \" align=\"center\"></td>                <td class=\"col\" width=\"15%\" style=\"  border-right: 1px solid black; \" align=\"center\"></td>                <td class=\"col\" width=\"10%\" style=\"  border-right: 1px solid black; \" align=\"center\"></td>            </tr>         {{#cList}}           <tr style=\"border:none;height:25px;\">                <td class=\"col\" width=\"5%\" style=\"border-right: 1px solid black;font-family: Verdana;font-size: 9px; border-left: 1px solid black; border-bottom: 1px solid black; \" align=\"center\">{{slNo}}</td>                <td class=\"col\" width=\"70%\" style=\" border-right: 1px solid black; font-family: Verdana;font-size: 10px; border-bottom: 1px solid black;\" align=\"center\">{{desc}}</td>                <td class=\"col\" width=\"15%\" style=\" border-right: 1px solid black; font-family: Verdana;font-size: 10px; border-bottom: 1px solid black;\" align=\"center\">{{qty}}</td>                <td class=\"col\" width=\"10%\" style=\" border-right: 1px solid black; font-family: Verdana;font-size: 10px; border-bottom: 1px solid black;\" align=\"center\">{{rate}}Rs.</td>            </tr>            {{/cList}}            <tr style=\"border:none;height:25px;\">                <td class=\"col\" width=\"5%\" style=\"border-right: 1px solid black; border-left: 1px solid black; border-bottom: 1px solid black; \" align=\"center\"></td>                <td class=\"col\" width=\"70%\" style=\" border-right: 1px solid black; border-bottom: 1px solid black;\" align=\"center\"></td>                <td class=\"col\" width=\"15%\" style=\" border-right: 1px solid black; font-family: Verdana;font-size: 10px; border-bottom: 1px solid black;\" align=\"center\"><span>Total Tax:</span>{{ttax}}</td>                <td class=\"col\" width=\"10%\" style=\" border-right: 1px solid black; font-family: Verdana;font-size: 10px; border-bottom: 1px solid black;\" align=\"center\">Total Price:{{ttval}} Rs</td>            </tr>        </table>        <table align='center' width='98%'>            <tr style=\"height:25px;\">                <td  class=\"col\"  width=\"5%\"></td>                <td class='col' width='95%' ></td>            </tr>        </table>        <table width=\"98%\" align=\"center\">            <tr>                <td class=\"col\" width=\"20%\" align=\"left\" style=\"font-family: Verdana;font-size: 10px;font-weight: bold;\">Delivery Date</td>                <td class=\"col\" width=\"15%\" align=\"left\" style=\"font-family: Verdana;font-size: 10px;white-space: nowrap;margin-left:10%;\">                    {{delivaryDate}}                </td>                <td class=\"col\" width=\"25%\"></td>                <td class=\"col\" width=\"35%\"></td>            </tr>        </table>        <table width='98%' align='center' style='white-space: nowrap;'>            <tr>                <td class=\"col\" width=\"35%\" style=\"font-family: Verdana;font-size: 10px;\">VAT No. {{vat}}</td>                <td class=\"col\" width=\"35%\"></td>                <td class=\"col\" width=\"30%\" style=\"font-family: Verdana;font-size: 10px;\">For <span style='font-weight: bold;font-family: Verdana;font-size: 12px;'>{{companyName}}</span></td>            </tr>            <tr>                <td class='col' style=\"font-family: Verdana;font-size: 10px;\" width='35%'>CST No. {{cst}}</td>                <td class='col' width='35%'></td>                <td class='col' width='30%'></td>            </tr>        </table>    </body></html>";
        String invoice = "<!DOCTYPE html><!--To change this license header, choose License Headers in Project Properties.To change this template file, choose Tools | Templatesand open the template in the editor.--><html>    <head>    </head>    <body style='font-family: Verdana;font-size: 8px;'>        <table border='0' width=\"95%\" align=\"center\" style=' border-collapse: collapse;'>            <tr style=\" white-space: nowrap;line-height: 0px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" >Bill No                 </td>                <td class=\"col\" align=\"left\" width='10%' style=\"white-space: nowrap;margin-left:10%;\">                    {{billNo}}                </td>                <td class=\"col\" height=\"10\" align=\"center\" style=\"white-space: nowrap;font-size:14px;  font-weight:bolder;\" width=\"27%\"><u>TAX INVOICE</u></td>                 <td class=\"col\" height=\"10\"  align=\"left\" width=\"14%\" style=\"white-space: nowrap; margin-right: 10%;\">Your Order No</td>                <td class=\"col\" height=\"10\"  align=\"right\" width=\"10%\" style=\"white-space: nowrap;margin-left:10%;white-space: nowrap; margin-right: 10%;\">{{orderNo}}</td>            </tr>        </table>        <table border='0' width=\"95%\" align=\"center\" style=' border-collapse: collapse;'>            <tr style=\" white-space: nowrap;line-height: 0px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"7%\" >Date                </td>                <td class=\"col\" align=\"left\" width='13%' style=\"white-space: nowrap;margin-left:10%;\">                    {{billdate}}                </td>                <td class=\"col\" height=\"10\" align=\"center\" style=\"white-space: nowrap;font-weight:bolder;font-size:14px;\" width=\"27%\">CASH / CREDIT</td>                 <td class=\"col\" height=\"10\"  align=\"left\" width=\"7%\" style=\"white-space: nowrap; margin-right: 10%;\">Date</td>                <td class=\"col\" height=\"10\"  align=\"right\" width=\"17%\" style=\"white-space: nowrap;margin-left:10%;white-space: nowrap; margin-right: 10%;\">                    {{orderDate}}                </td>            </tr>        </table>        <table border='0' width=\"95%\" align=\"center\" style=' border-collapse: collapse;'>            <tr style=\" white-space: nowrap;line-height: 0px;height:15px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" >                </td>                <td class=\"col\" align=\"left\" width='10%' >                </td>                <td class=\"col\" height=\"10\" align=\"center\" style=\"white-space: nowrap;font-weight:bolder;font-size:18px;\" width=\"27%\"></td>                 <td class=\"col\" height=\"10\"  align=\"left\" width=\"14%\" style=\"white-space: nowrap; margin-right: 10%;\"></td>                <td class=\"col\" height=\"10\"  align=\"right\" width=\"10%\" style=\"\"></td>            </tr>            <tr style=\" white-space: nowrap;line-height: 0px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" >                </td>                <td class=\"col\" align=\"left\" width='10%' >                </td>                <td class=\"col\" height=\"10\" align=\"center\" style=\"white-space: nowrap;font-weight:bolder;font-size:18px;\" width=\"27%\">{{companyName}}</td>                 <td class=\"col\" height=\"10\"  align=\"left\" width=\"14%\" style=\"white-space: nowrap; margin-right: 10%;\"></td>                <td class=\"col\" height=\"10\"  align=\"right\" width=\"10%\" style=\"\"></td>            </tr>            <tr style=\" white-space: nowrap;line-height: 0px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" >                </td>                <td class=\"col\" align=\"left\" width='10%' >                </td>                <td class=\"col\" height=\"10\" align=\"center\" style=\"white-space: nowrap;font-weight:bolder;font-size:12px;\" width=\"27%\">{{address}}</td>                 <td class=\"col\" height=\"10\"  align=\"left\" width=\"14%\" style=\"white-space: nowrap; margin-right: 10%;\"></td>                <td class=\"col\" height=\"10\"  align=\"right\" width=\"10%\" style=\"\"></td>            </tr>            <tr style=\" white-space: nowrap;line-height: 0px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" >                </td>                <td class=\"col\" align=\"left\" width='10%' >                </td>                <td class=\"col\" height=\"10\" align=\"center\" style=\"white-space: nowrap;font-weight:bolder;font-size:12px;\" width=\"27%\">{{phone}}</td>                 <td class=\"col\" height=\"10\"  align=\"left\" width=\"14%\" style=\"white-space: nowrap; margin-right: 10%;\"></td>                <td class=\"col\" height=\"10\"  align=\"right\" width=\"10%\" style=\"\"></td>            </tr>            <tr style=\" white-space: nowrap;line-height: 0px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" >                </td>                <td class=\"col\" align=\"left\" width='10%' >                </td>                <td class=\"col\" height=\"10\" align=\"center\" style=\"white-space: nowrap;font-weight:bolder;font-size:12px;\" width=\"27%\">{{email}}</td>                 <td class=\"col\" height=\"10\"  align=\"left\" width=\"14%\" style=\"white-space: nowrap; margin-right: 10%;\"></td>                <td class=\"col\" height=\"10\"  align=\"right\" width=\"10%\" style=\"\"></td>            </tr>        </table>        <table border='0' width=\"95%\" align=\"center\" style=' border-collapse: collapse;font-size:14px;font-weight:bolder;'>            <tr style=\" white-space: nowrap;line-height: 0px;height:25px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"5%\" >M/s.                 </td>                <td class=\"col\" align=\"left\" height=\"20\" width='95%' style=\"white-space: nowrap;margin-left:10%;\" >                    {{ms}}                </td>            </tr>            <tr style=\" white-space: nowrap;line-height: 0px;height:25px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"5%\" style=\"white-space: nowrap;margin-left:10%;\" >                </td>                <td class=\"col\" align=\"left\" height=\"20\" width='95%' style=\"white-space: nowrap;margin-left:10%;\"  >                </td>            </tr>        </table>        <table width=\"97%\" align=\"center\" style='line-break:initial; border-collapse: collapse;'>            <tr style='height:25px;'>                <td colspan=\"4\"></td>            </tr>            <tr>                <td  class=\"col\" width=\"5%\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-left: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\" align=\"center\">Sl. No</td>                <td class=\"col\" width=\"30%\" align=\"center\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\">DESCRIPTION</td>                <td class=\"col\" width=\"10%\" align=\"center\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\">Quantity</td>                <td class=\"col\" width=\"10%\" align=\"center\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\">Rate</td>                <td class=\"col\" width=\"10%\" align=\"center\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\">VAT Rate</td>                <td class=\"col\" width=\"10%\" align=\"center\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\">VAT Amount</td>                <td class=\"col\" width=\"25%\" align=\"center\" style=\"font-weight:bold; font-family: Verdana;font-size: 10px;border-right: 1px solid black; border-bottom: 1px solid black; border-top: 1px solid black;\"> Amount Rs &nbsp; &nbsp; .P</td>            </tr>            {{#invRptList}}            <tr style=\"border:none;height:25px;\" >                <td class=\"col\" width=\"5%\" style=\" border-left: 1px solid black; border-right: 1px solid black; \" align=\"center\">{{slNo}}</td>                <td class=\"col\" width=\"30%\" style=\" border-right: 1px solid black; \" align=\"center\">{{desc}}</td>                <td class=\"col\" width=\"10%\" style=\"  border-right: 1px solid black; \" align=\"center\">{{qty}}</td>                <td class=\"col\" width=\"10%\" style=\"  border-right: 1px solid black; \" align=\"center\">{{rate}}</td>                <td class=\"col\" width=\"10%\" style=\"  border-right: 1px solid black; \" align=\"center\">{{vatRate}}</td>                 <td class=\"col\" width=\"10%\" style=\"  border-right: 1px solid black; \" align=\"center\">{{vatAmnt}}</td>                 <td class=\"col\" width=\"25%\" style=\"  border-right: 1px solid black; \" align=\"center\">{{totamnt}}</td>            </tr>            {{/invRptList}}            <tr style=\"border:none;height:25px;\" >                <td class=\"col\" width=\"5%\" style=\" border-left: 1px solid black; border-right: 1px solid black; \" align=\"center\"></td>                <td class=\"col\" width=\"30%\" style=\" border-right: 1px solid black; \" align=\"center\"></td>                <td class=\"col\" width=\"10%\" style=\"  border-right: 1px solid black;\" align=\"center\"></td>                <td class=\"col\" width=\"10%\" style=\"  border-right: 1px solid black; \" align=\"center\"></td>                <td class=\"col\" width=\"10%\" style=\"  border-right: 1px solid black;\" align=\"center\"></td>                 <td class=\"col\" width=\"10%\" style=\"  border-right: 1px solid black;\" align=\"center\"></td>                 <td class=\"col\" width=\"25%\" style=\"  border-right: 1px solid black; \" align=\"center\"></td>            </tr>            <tr style=\"border:none;height:25px;\">                <td class=\"col\" width=\"5%\" style=\"border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; border-top: 1px solid black; \" align=\"center\"></td>                <td class=\"col\" width=\"30%\" style=\"border-bottom: 1px solid black; border-right: 1px solid black; border-top: 1px solid black;\" align=\"center\"></td>                <td class=\"col\" width=\"10%\" style=\"border-bottom: 1px solid black;  border-right: 1px solid black; border-top: 1px solid black;\" align=\"center\"></td>                <td class=\"col\" width=\"10%\" style=\"border-bottom: 1px solid black;  border-right: 1px solid black;border-top: 1px solid black; \" align=\"center\"></td>                <td class=\"col\" width=\"10%\" style=\"border-bottom: 1px solid black;  border-right: 1px solid black; border-top: 1px solid black;\" align=\"center\"></td>                 <td class=\"col\" width=\"10%\" style=\" border-bottom: 1px solid black; border-right: 1px solid black;border-top: 1px solid black; \" align=\"center\">Total:</td>                 <td class=\"col\" width=\"25%\" style=\" border-bottom: 1px solid black; border-right: 1px solid black; border-top: 1px solid black;\" align=\"center\">{{totAmnts}}</td>            </tr>        </table>        <table border='0' width=\"95%\" align=\"center\" style=' border-collapse: collapse;'>            <tr style=\" white-space: nowrap;line-height: 0px;height:25px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" >Rupees                 </td>                <td class=\"col\" align=\"left\" height=\"20\" width='23%' style=\" white-space: nowrap;margin-left:10%;\" >                    {{amntInWords}}                </td>                <td class='col' width='60%' align='center'>E. & O.E.</td>            </tr>            <tr style=\" white-space: nowrap;line-height: 0px;height:25px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" style=\"white-space: nowrap;margin-left:10%;\">                 </td>                <td class=\"col\" align=\"left\" height=\"20\" width='23%' style=\"white-space: nowrap;margin-left:10%;\" >                    {{amntInWords1}}                </td>                <td class='col'  width='60%' align='center' style='font-size:14px; font-weight:bolder;'>For {{companyName}}</td>            </tr>            {{#amntList}}            <tr style=\" white-space: nowrap;line-height: 0px;height:25px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"10%\" style=\"white-space: nowrap;margin-left:10%;\">                 </td>                <td class=\"col\" align=\"left\" height=\"20\" width='23%' style=\"white-space: nowrap;margin-left:10%;\" >                    {{amnt}}                </td>                <td class='col'  width='60%' align='center' style='font-size:14px; font-weight:bolder;'></td>            </tr>            {{/amntList}}        </table>        <table border='0' width=\"95%\" align=\"center\" style=' border-collapse: collapse;'>            <tr style=\" white-space: nowrap;line-height: 0px;height:25px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"20%\" >                 </td>                <td class=\"col\" align=\"left\" height=\"20\" width='30%' style=\"white-space: nowrap;margin-left:10%;\" >                </td>                <td class='col' align='center' width='47%'></td>            </tr>            <tr style=\" white-space: nowrap;line-height: 0px;height:25px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"20%\" >Site Address                 </td>                <td class=\"col\" align=\"left\" height=\"20\" width='30%' style=\"white-space: nowrap;margin-left:10%;\" >                    {{siteAddress}}                </td>                <td class='col' align='center' width='47%'></td>            </tr>            <tr style=\" white-space: nowrap;line-height: 0px;height:25px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"13%\" >Vat No. :                 </td>                <td class=\"col\" align=\"left\" height=\"20\" width='40%' style=\"white-space: nowrap;\" >                    <span>{{vat}}</span>                </td>                <td class='col' align='center' width='47%'></td>            </tr>            <tr style=\" white-space: nowrap;line-height: 0px;height:25px;\">                <td class=\"col\" align=\"left\" height=\"20\" width=\"13%\" >CST No. :                </td>                <td class=\"col\" align=\"left\" height=\"20\" width='40%' style=\"white-space: nowrap;\" >                    <span>{{cst}}</span>                </td>                <td class='col' align='left' width='47%' style='font-size:9px;'>All subject to Kolkata Jurisdicdiction only.</td>            </tr>        </table>    </body></html>";

        Properties prop = new Properties();
        prop.put("challan", challan);
        prop.put("invoice", invoice);
        prop.put("hello", "hello");
        File file = new File("Templete.properties");
        FileOutputStream fileOut = new FileOutputStream(file);
        prop.store(fileOut, null);
    }

    public static Properties getDefaultTempProperties() throws FileNotFoundException {
        Properties propObj = new Properties();
        InputStream in = new FileInputStream(new File("Templete.properties"));
        //   System.out.println("in=>"+in);
        try {
            if (in != null) {
                propObj.load(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return propObj;
    }

}
