import org.apache.commons.io.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {



    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
    }

    /** TODO: regex for all pattern types
     * name = [nN].+[eE]:{1}([MmBbCcAa][a-zA-Z0-9]+[KkDdSs])[:;]|[nN].+[eE]:;
     * price = [Pp][Rr].+[Ee][:;][0-9]\.[0-9]{2}
     * type = [Tt].+[Ee][:;][Ff].+[Dd]
     * expiration (groups date and ##) = ([0-9]+\/[0-9]+\/[0-9]+)([##$]+)
     **/
    public static void hurtLocker(String input) {
        Pattern name = Pattern.compile("");
        Pattern price = Pattern.compile("");
        Pattern type = Pattern.compile("");
        Pattern expiration = Pattern.compile("");
        Matcher dataMatcher = name.matcher(input);
        String input1 = dataMatcher.replaceAll("\n");

        // food and price patterns


        // hashmaps to hold food / price keys and occurrences
        Map<String, Map<String, Integer>> outerMap = new HashMap<>();
        Map<String, Integer> innerMap = new HashMap<>();
        Map<String, Integer> outerKeySeen = new HashMap<>();


        // put values into hashmaps

        // write string to output-test file
        try {
            FileWriter writer = new FileWriter("output-test.txt");
            writer.write(input1);
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
