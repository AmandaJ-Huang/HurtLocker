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

    public static void main(String[] args) throws Exception{
        HurtLocker hurtLocker = new HurtLocker();
        String output = hurtLocker.theHurtLocker((new Main()).readRawDataToString());
        writeToFile(output);
        System.out.println(output);
    }

    public static void writeToFile(String input) {
        try {
            FileWriter writer = new FileWriter("output-test.txt");
            writer.write(input);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
