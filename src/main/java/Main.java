import org.apache.commons.io.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private Map<String, Integer> groceryMap = new HashMap<>();

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static String hurtLocker(String input) {
        Pattern delimiters = Pattern.compile("[:;^@%*]");
        Matcher dataMatcher = delimiters.matcher(input);

        String matched = dataMatcher.replaceAll("\n");

        try {
            FileWriter writer = new FileWriter("output-test.txt");
            writer.write(matched);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        hurtLocker(output);
        System.out.println(output);
    }
}
