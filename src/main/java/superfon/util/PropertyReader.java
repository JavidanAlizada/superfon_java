package superfon.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertyReader {

    public static List<Map.Entry<String, String>> getAllProps() {
        String fileName = Constants.PROPFILE.getPropfile();
        List<Map.Entry<String, String>> allProps = new ArrayList<>();
        Map<String, String> propKeyValue = new HashMap<>();
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(fileName)) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find " + fileName);
                return null;
            }
            prop.load(input);
            prop.forEach((key, value) -> {
                propKeyValue.put((String) key, (String) value);
            });
            allProps.addAll(propKeyValue.entrySet());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return allProps;
    }

    public static String getPropByKey(String name) {
        String fileName = Constants.PROPFILE.getPropfile();
        String propVal = null;
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(fileName)) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find " + fileName);
                return null;
            }
            prop.load(input);

            propVal = prop.getProperty(name);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return propVal;
    }

//    public static void main(String[] args) {
//        System.out.println(Constants.PROPFILE.getPropfile());
//        System.out.println(PropertyReader.getAllProps());
//        System.out.println(PropertyReader.getPropByKey("db_user"));
//    }
}
