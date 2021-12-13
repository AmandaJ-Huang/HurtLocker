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
     * all together now...
     * milkNotNull - ([Nn]..[Ee]:)([Mm]..[Kk])([:;])([Pp]...[Ee]:)([0-9]\.[0-9].)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*])(expiration:)([0-9]\/[0-9]+\/[0-9]...)([##$]+)
     * price - (([Pp][Rr].+[Cc][Ee])(:)([0-9]\.[0-9]{2})(;)|([Pp][Rr].+[Cc][Ee][:;]+))
     * type - (([Tt].+[Ee][:;])([Ff].+[Dd])([;^:%*]))
     * expiration - ((expiration)(:)([0-9]+\/[0-9]+\/[0-9]+)([##$]+))
     **/
    public static void hurtLocker(String input) {
        String milk = "([Nn]..[Ee]:)([Mm]..[Kk])([:;])([Pp]...[Ee]:)(([0-9]\\.[0-9].)|)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*])(expiration:)([0-9]\\/[0-9]+\\/[0-9]...)([##$]+)";
        Pattern milkPattern = Pattern.compile(milk);
        Matcher milkMatcher = milkPattern.matcher(input);

        // hashmaps to hold food / price keys and occurrences
        Map<String, Map<String, Integer>> outerMap = new HashMap<>();
        Map<Matcher, Integer> innerMap = new HashMap<>();
        Map<String, Integer> outerKeySeen = new HashMap<>();

        int counter = 0;
        // put values into hashmaps
        while (milkMatcher.find()) {
            counter++;
            System.out.println("milk, " + counter + ", price" + milkMatcher.group(5));
        }

        // write string to output-test file
        try {
            FileWriter writer = new FileWriter("output-test.txt");
            writer.write(innerMap.toString());
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
