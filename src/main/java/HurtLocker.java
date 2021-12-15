import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HurtLocker {
    private String milkReg = "(milk)";
    private String breadReg = "(bread)";
    private String cookiesReg = "(c.{5}s)";
    private String applesReg = "(a.{4}s)";
    private String errorReg = "((name)[:;]{2})|((price)[:;]{2})";
    private String priceReg = "([:;]price[:;]+)([0-9]\\.[0-9]+)";

    private Map<String, Map<String,Integer>> groceryMapMap = new HashMap<>();

    public HurtLocker() {

    }

    public String delimitersToColon(String input) {
        Pattern delimiters = Pattern.compile("[:;^@%*]");
        Matcher delimitMatcher = delimiters.matcher(input);

        return delimitMatcher.replaceAll(":");
    }

    public Integer foodFinder(String input, String pattern) {
        Pattern food = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher foodMatcher = food.matcher(input);
        Integer counter = 0;

        while (foodMatcher.find()) {
            counter++;
        }
        return counter;
    }

    public String nameLineFormat(String name, Integer count) {
        if (count > 1) {
            return String.format("name:%8s \t \t seen: %s times\n", name, count);
        } else {
            return String.format("name:%8s \t \t seen: %s time\n", name, count);
        }
    }

    public String priceLineFormat(String price, Integer count) {
        if (count > 1) {
            return String.format("Price:%7s \t\t seen: %s times\n", price, count);
        } else {
            return String.format("Price:%7s \t\t seen: %s time\n", price, count);
        }
    }

    public String theHurtLocker(String input) {
        String reformattedData = delimitersToColon(input);

        // food and error lines
        String milk = String.format("name:%8s \t\t seen: %s times\n", "Milk", foodFinder(reformattedData, milkReg+priceReg));
        String bread = nameLineFormat("Bread", foodFinder(reformattedData, breadReg+priceReg));
        String cookies = nameLineFormat("Cookies", foodFinder(reformattedData, cookiesReg+priceReg));
        String apples = nameLineFormat("Apples", foodFinder(reformattedData, applesReg+priceReg));
        String errors = String.format("%s       \t \t seen: %s times", "Errors", foodFinder(reformattedData, errorReg));

        // price lines
        String milk323 = priceLineFormat("3.23", foodFinder(reformattedData, milkReg+"([:;]price[:;]+)(3.23)"));
        String milk123 = priceLineFormat("1.23", foodFinder(reformattedData, milkReg+"([:;]price[:;]+)(1.23)"));
        String breadPrice = priceLineFormat("1.23", foodFinder(reformattedData, breadReg+"([:;]price[:;]+)(1.23)"));
        String cookiePrice = priceLineFormat("2.25", foodFinder(reformattedData, cookiesReg+"([:;]price[:;]+)(2.25)"));
        String apples025 = priceLineFormat("0.25", foodFinder(reformattedData, applesReg+"([:;]price[:;]+)(0.25)"));
        String apples023 = priceLineFormat("0.23", foodFinder(reformattedData, applesReg+"([:;]price[:;]+)(0.23)"));

        StringBuilder output = new StringBuilder();

        return output
                .append(milk)
                .append("============= \t \t =============\n")
                .append(milk323)
                .append("-------------\t\t -------------\n")
                .append(milk123)
                .append("\n")
                .append(bread)
                .append("=============\t\t =============\n")
                .append(breadPrice)
                .append("-------------\t\t -------------\n")
                .append("\n")
                .append(cookies)
                .append("=============     \t =============\n")
                .append(cookiePrice)
                .append("-------------        -------------\n")
                .append("\n")
                .append(apples)
                .append("=============     \t =============\n")
                .append(apples025)
                .append("-------------     \t -------------\n")
                .append(apples023)
                .append("\n")
                .append(errors)
                .toString();
    }

}
