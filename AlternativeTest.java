import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Tests for class Alternative.
 *
 * @author  Dr. Jody Paul
 * @version 20241113.2
 */
public class AlternativeTest {
    private Alternative alt01;
    /**
     * Set up the test fixture.
     */
    @BeforeEach
    public void setUp() {
        alt01 = new Alternative("Alternative One descriptor");
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("Alternative One descriptor", alt01.getDescriptor());
    }

    @Test
    public void getFinalScoreTest() {
        assertEquals(Alternative.DEFAULT_SCORE, alt01.getFinalScore());
    }

    @Test
    public void setAndGetFinalScore() {
        alt01.setFinalScore(42);
        assertEquals(42, alt01.getFinalScore());
    }

    @Test
    public void toStringTest() {
        assertEquals("Alternative One descriptor == 0", alt01.toString());
    }
}

