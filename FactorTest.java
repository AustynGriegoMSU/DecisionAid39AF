import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class FactorTest.
 *
 * @author  Dr. Jody Paul
 * @version 20241113.1
 */
public class FactorTest {
    private Factor fact01;

    /**
     * Set up the test fixture.
     */
    @BeforeEach
    public void setUp() {
        fact01 = new Factor("Factor One name");
    }

    @Test
    public void getNameTest() {
        assertEquals("Factor One name", fact01.getName());
    }

    @Test
    public void getRankTest() {
        assertEquals(Factor.DEFAULT_RANK, fact01.getRank());
    }

    @Test
    public void setAndGetName() {
        fact01.setRank(42);
        assertEquals(42, fact01.getRank());
        fact01.setRank(-5);
        assertEquals(-5, fact01.getRank());
    }
}

