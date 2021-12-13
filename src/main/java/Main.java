import org.apache.commons.io.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {



    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
    }

    /** TODO: regex for all pattern types
     * all together now...
     * milk - ([Nn]..[Ee]:)([Mm]..[Kk])([:;])([Pp]...[Ee]:)(([0-9]\.[0-9].)|)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*])(expiration:)([0-9]\/[0-9]+\/[0-9]...)([##$]+)
     * cookies - ([Cc].....[Ss])([:;])([Pp]...[Ee]:)(([0-9]\.[0-9].)|)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*%])(expiration:)([0-9]\/[0-9]+\/[0-9]...)([##$]+)
     * bread - ([Bb]...[Dd])([:;])([Pp]...[Ee]:)(([0-9]\.[0-9].)|)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*%@])(expiration:)([0-9]\/[0-9]+\/[0-9]...)([##$]+)
     * apples - ([Aa]....[Ss])([:;])([Pp]...[Ee]:)(([0-9]\.[0-9].)|)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*%@])(expiration:)([0-9]\/[0-9]+\/[0-9]...)([##$]+)
     * null values - (([Nn]..[Ee])([:;]{2})|([Pp]...[Ee])([:;]{2}))
     * price - group(5)
     * type - group(9)
     * expiration - group(12)
     **/
    public static void hurtLocker(String input) {
        String milk = "([Nn]..[Ee]:)([Mm]..[Kk])([:;])([Pp]...[Ee]:)(([0-9]\\.[0-9].)|)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*])(expiration:)([0-9]\\/[0-9]+\\/[0-9]...)([##$]+)";
        String cookies = "([Cc].....[Ss])([:;])([Pp]...[Ee]:)(([0-9]\\.[0-9].)|)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*%])(expiration:)([0-9]\\/[0-9]+\\/[0-9]...)([##$]+)";
        String bread = "([Bb]...[Dd])([:;])([Pp]...[Ee]:)(([0-9]\\.[0-9].)|)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*%@])(expiration:)([0-9]\\/[0-9]+\\/[0-9]...)([##$]+)";
        String apples = "([Aa]....[Ss])([:;])([Pp]...[Ee]:)(([0-9]\\.[0-9].)|)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*%@])(expiration:)([0-9]\\/[0-9]+\\/[0-9]...)([##$]+)";
        String errors = "(([Nn]..[Ee])([:;]{2})|([Pp]...[Ee])([:;]{2}))";
        String allPatterns = milk + "|" + cookies + "|" + bread + "|" + apples + "|" + errors;
        Pattern milkPattern = Pattern.compile(milk);
        Matcher milkMatcher = milkPattern.matcher(input);

        // hashmaps to hold food / price keys and occurrences
        Map<String, List<Integer>> outerMap = new HashMap<>();
        List<Integer> occurrences = new ArrayList<>();

        int counter = 0;
        // put values into hashmaps
        while (milkMatcher.find()) {
            counter++;

            System.out.println("milk, " + counter
                    + ", price: " + milkMatcher.group(5)
                    + ", type: " + milkMatcher.group(9)
                    + ", expiration: " + milkMatcher.group(12));

        }

        // write string to output-test file
        try {
            FileWriter writer = new FileWriter("output-test.txt");
            writer.write("");
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
