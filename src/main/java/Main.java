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
     * milk - 4/7/10
     * cookies - 4/7/10
     * bread - 4/7/10
     * apples - 4/7/10
     **/
    public static void hurtLocker(String input) {
        String milk = "([Mm]..[Kk])([:;])([Pp]...[Ee]:)([0-9]\\.[0-9].)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*])(expiration:)([0-9]\\/[0-9]+\\/[0-9]...)([##$]+)";
        String cookies = "([Cc].....[Ss])([:;])([Pp]...[Ee]:)([0-9]\\.[0-9].)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*%])(expiration:)([0-9]\\/[0-9]+\\/[0-9]...)([##$]+)";
        String bread = "([Bb]...[Dd])([:;])([Pp]...[Ee]:)([0-9]\\.[0-9].)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*%@])(expiration:)([0-9]\\/[0-9]+\\/[0-9]...)([##$]+)";
        String apples = "([Aa]....[Ss])([:;])([Pp]...[Ee]:)([0-9]\\.[0-9].)([:;])([Tt]..[Ee][:;])([Ff]..[Dd])([:;$^!*%@])(expiration:)([0-9]\\/[0-9]+\\/[0-9]...)([##$]+)";
        String errors = "(([Nn]..[Ee])([:;]{2})|([Pp]...[Ee])([:;]{2}))";
        String allPatterns = milk + "|" + cookies + "|" + bread + "|" + apples + "|" + errors;

        Matcher milkMatcher = Pattern.compile(milk).matcher(input);
        Matcher cookiesMatcher = Pattern.compile(cookies).matcher(input);
        Matcher breadMatcher = Pattern.compile(bread).matcher(input);
        Matcher applesMatcher = Pattern.compile(apples).matcher(input);
        Matcher errorsMatcher = Pattern.compile(errors).matcher(input);
        Matcher allMatcher = Pattern.compile(allPatterns).matcher(input);

        // hashmaps to hold food / price keys and occurrences
        String[] foods = new String[]{"Milk", "Cookies", "Bread", "Apples"};
        Map<String, Map<String,Integer>> groceryList = new HashMap<>();
        Map<String, Integer> milkCounter = new HashMap<>();
        Map<String, Integer> cookieCounter = new HashMap<>();
        Map<String, Integer> breadCounter = new HashMap<>();
        Map<String, Integer> appleCounter = new HashMap<>();
        Map<String, Integer> errorCounter = new HashMap<>();

        groceryList.put("Milk", milkCounter);
        groceryList.put("Cookies", cookieCounter);
        groceryList.put("Bread", breadCounter);
        groceryList.put("Apples", appleCounter);

        String price = "";
        String type = "";
        String expiration = "";
        // put values into hashmaps
        while (allMatcher.find()) {
            while (milkMatcher.find()) {
                price = milkMatcher.group(4);
                type = milkMatcher.group(7);
                expiration = milkMatcher.group(10);

                if (!milkCounter.containsKey("milk")
                || !milkCounter.containsKey(price)
                || !milkCounter.containsKey(type)
                || !milkCounter.containsKey(expiration)) {
                    milkCounter.put("milk", 0);
                    milkCounter.put(price, 0);
                    milkCounter.put(type, 0);
                    milkCounter.put(expiration, 0);
                } else {
                    milkCounter.replace("milk", (milkCounter.get("milk")+1));
                    milkCounter.replace(price, (milkCounter.get(price)+1));
                    milkCounter.replace(type, (milkCounter.get(type)+1));
                    milkCounter.replace(expiration, (milkCounter.get(expiration)+1));
                }
            }
        }

        String milkOutput = String.format("%13s, %3s\t, %13s", "name:" + "Milk", "", "seen: " + milkCounter.get("milk") + " times") + "\n" +
                "Price: " + milkCounter.get("3.23");

        // write string to output-test file
        try {
            FileWriter writer = new FileWriter("output-test.txt");
            writer.write(milkOutput);
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
