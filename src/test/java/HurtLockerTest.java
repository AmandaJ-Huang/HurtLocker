import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;

public class HurtLockerTest {
    private Main main = new Main();
    private HurtLocker hurtLocker = new HurtLocker();
    String rawData;
    String rawDataFormatted = "naMe:Milk:price:3.23:type:Food:expiration:1/25/2016##naME:BreaD:price:1.23:type:Food:expiration:1/02/2016##NAMe:BrEAD:price:1.23:type:Food:expiration:2/25/2016##naMe:MiLK:price:3.23:type:Food:expiration:1/11/2016##naMe:Cookies:price:2.25:type:Food:expiration:1/25/2016##naMe:CoOkieS:price:2.25:type:Food:expiration:1/25/2016##naMe:COokIes:price:2.25:type:Food:expiration:3/22/2016##naMe:COOkieS:price:2.25:type:Food:expiration:1/25/2016##NAME:MilK:price:3.23:type:Food:expiration:1/17/2016##naMe:MilK:price:1.23:type:Food!expiration:4/25/2016##naMe:apPles:price:0.25:type:Food:expiration:1/23/2016##naMe:apPles:price:0.23:type:Food:expiration:5/02/2016##NAMe:BrEAD:price:1.23:type:Food:expiration:1/25/2016##naMe::price:3.23:type:Food:expiration:1/04/2016##naMe:Milk:price:3.23:type:Food:expiration:1/25/2016##naME:BreaD:price:1.23:type:Food:expiration:1/02/2016##NAMe:BrEAD:price:1.23:type:Food:expiration:2/25/2016##naMe:MiLK:priCe::type:Food:expiration:1/11/2016##naMe:Cookies:price:2.25:type:Food:expiration:1/25/2016##naMe:Co0kieS:pRice:2.25:type:Food:expiration:1/25/2016##naMe:COokIes:price:2.25:type:Food:expiration:3/22/2016##naMe:COOkieS:Price:2.25:type:Food:expiration:1/25/2016##NAME:MilK:price:3.23:type:Food:expiration:1/17/2016##naMe:MilK:priCe::type:Food:expiration:4/25/2016##naMe:apPles:prIce:0.25:type:Food:expiration:1/23/2016##naMe:apPles:pRice:0.23:type:Food:expiration:5/02/2016##NAMe:BrEAD:price:1.23:type:Food:expiration:1/25/2016##naMe::price:3.23:type:Food:expiration:1/04/2016##";

    @Before
    public void setup() throws Exception {
        rawData = main.readRawDataToString();
    }

    @Test
    public void delimitersToColonTest() throws Exception {
        // Given
        String expected = rawDataFormatted;

        // When
        String actual = hurtLocker.delimitersToColon(rawData);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void milkPatternTest() throws Exception {
        // Given
        String input = "naMe:Milk:price:3.23:type:Food:expiration:1/25/2016##";
        Integer expected = 1;

        // When
        Integer actual = hurtLocker.foodFinder(input, "(milk)");

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void milkPatternMultipleTest() throws Exception {
        // Given
        String input = rawDataFormatted;
        Integer expected = 6;

        // When
        Integer actual = hurtLocker.foodFinder(input, "(milk)([:;]price[:;]+)([0-9]\\.[0-9]+)");

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void theHurtLockerTest() {
        // Given
        String input = rawDataFormatted;
        String expected = "name:    Milk \t\t seen: 6 times\n" +
                "============= \t \t =============\n" +
                "Price: \t 3.23\t\t seen: 5 times\n" +
                "-------------\t\t -------------\n" +
                "Price:   1.23\t\t seen: 1 time\n" +
                "\n" +
                "name:   Bread\t\t seen: 6 times\n" +
                "=============\t\t =============\n" +
                "Price:   1.23\t\t seen: 6 times\n" +
                "-------------\t\t -------------\n" +
                "\n" +
                "name: Cookies     \t seen: 8 times\n" +
                "=============     \t =============\n" +
                "Price:   2.25        seen: 8 times\n" +
                "-------------        -------------\n" +
                "\n" +
                "name:  Apples     \t seen: 4 times\n" +
                "=============     \t =============\n" +
                "Price:   0.25     \t seen: 2 times\n" +
                "-------------     \t -------------\n" +
                "Price:   0.23  \t \t seen: 2 times\n" +
                "\n" +
                "Errors         \t \t seen: 4 times";

        // When
        String actual = hurtLocker.theHurtLocker(input);

        // Actual
        Assert.assertEquals(expected, actual);
    }

}
