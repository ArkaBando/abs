package automatedbillingsoftware.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author user
 */
public class MachineIdentifierHelper {

    public static synchronized boolean setMachineIdentifier(HashMap<String, String> mcIdetifyValues) {
        boolean IsMachineIdentifierSetted = false;

        try {
            Properties properties = new Properties();

            for (Map.Entry<String, String> holiday : mcIdetifyValues.entrySet()) {
                String date = holiday.getKey();
                String vaccationDescription = holiday.getValue();
                properties.setProperty(date, vaccationDescription);

                File file = new File("McDetailsList.properties");
                FileOutputStream fileOut = new FileOutputStream(file);
                properties.store(fileOut, null);
                // System.out.println("properties=>" + properties.stringPropertyNames());
                fileOut.close();
            }

            IsMachineIdentifierSetted = true;
        } catch (Exception ex) {
            //   ex.printStackTrace();
        }

        return IsMachineIdentifierSetted;
    }

    public static synchronized HashMap<String, String> getMachineIdentifierMap() {
        HashMap<String, String> hm = null;

        try {
            hm = new LinkedHashMap<String, String>();
            Properties propObj = new Properties();
            InputStream in = new FileInputStream(new File("McDetailsList.properties"));
            //   System.out.println("in=>"+in);
            try {
                if (in != null) {
                    propObj.load(in);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<String> stringPropertyNames = propObj.stringPropertyNames();
            for (String propertyName : stringPropertyNames) {
                hm.put(propertyName, propObj.getProperty(propertyName));
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        } finally {
            return hm;
        }
        //return hm;
    }
}
