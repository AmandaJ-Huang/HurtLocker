import org.apache.commons.io.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private Map<String, Map<String, Integer>> outerMap = new HashMap<>();
    private Map<String, Integer> innerMap = new HashMap<>();

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
    }

    public static void hurtLocker(String input) {
        Pattern delimiters = Pattern.compile("[:;^@%*]");
        Matcher dataMatcher = delimiters.matcher(input);

        /** TODO: regex for all pattern types
         * regex for each field: milk, bread, cookies, apples
         * ex. m[...]k --> change to Milk
         * regex for each key: name, price, type, expiration
         * ex. n[...]e --> change to name
         * foods may have multiple prices
         * regex for expiration ending with ##$ - maybe?
         * handle null values somehow...there is at least one for each key, except expiration...
        **/
        String matched = dataMatcher.replaceAll("\n");

        try {
            FileWriter writer = new FileWriter("output-test.txt");
            writer.write(matched);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        hurtLocker(output);
        System.out.println(output);
    }
}
