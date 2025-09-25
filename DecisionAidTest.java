import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/**
 * Tests for class DecisionAid.
 *
 * @author  Dr. Jody Paul
 * @version 20241117.1
 */
public class DecisionAidTest {
    /** Default precision for comparing doubles, */
    private static final double EPSILON = Math.pow(10, -5);

    private double[][] crossRankings1 = {{10.0, 10.0 }, {8.0, 4.0}, {3.0, 9.0}};
    private double[][] normWithinFac1 = {{0.4761905, 0.4347826},
                                         {0.3809524, 0.1739130},
                                         {0.1428571, 0.3913043}};
    private List<Factor> factors1 = new ArrayList<Factor>();
    private double[] weightedSum1 = {9.9792961, 5.8964803, 6.1242236};
    private List<Alternative> alternatives1 = new ArrayList<Alternative>();
    private int[] finalScores = {100, 59, 61};
    private double[][] crossRankings2 = {{10.0, 10.0}, {12.0, 6.0}, {9.0, 11.0}};
    /**
     * Default constructor for test class DecisionAidTest
     */
    public DecisionAidTest() {
        factors1.add(new Factor("Factor A"));
        factors1.get(0).setRank(10);
        factors1.add(new Factor("Factor B"));
        factors1.get(1).setRank(12);
        alternatives1.add(new Alternative("Alternative 1"));
        alternatives1.add(new Alternative("Alternative 2"));
        alternatives1.add(new Alternative("Alternative 3"));
    }

    @Test
    public void normalizeWithinEachFactorTest() {
        double[][] normed = DecisionAid.normalizeWithinEachFactor(crossRankings1);
        for (int i = 0; i < normed.length; i++) {
            for (int j = 0; j < normed[0].length; j++) {
                assertEquals(normWithinFac1[i][j], normed[i][j], EPSILON);
            }
        }
    }

    @Test
    public void computeWeightedSumForEachAlternativeTest() {
        double[] scores = DecisionAid.computeWeightedSumForEachAlternative(factors1, normWithinFac1);
        for (int i = 0; i < scores.length; i++) {
            assertEquals(weightedSum1[i], scores[i], EPSILON);
        }
    }

    @Test
    public void normalizeAndSetFinalScoreTest() {
        List<Alternative> finalAlts = DecisionAid.normalizeAndSetFinalScore(alternatives1, weightedSum1);
        for (int i = 0; i < finalAlts.size(); i++) {
            assertEquals(finalScores[i], finalAlts.get(i).getFinalScore());
        }
    }

    @Test
    public void sortDescendingTest() {
        Random rand = new java.util.Random();
        List<Alternative> alts = new ArrayList<Alternative>();
        for (int i = 0; i < 7; i++) {
            Alternative a = new Alternative("alt" + i);
            a.setFinalScore(rand.nextInt(101));
            alts.add(a);
        }
        DecisionAid.sortDescending(alts);
        for (int i = 1; i < alts.size(); i++) {
            assertTrue(alts.get(i).getFinalScore() <= alts.get(i - 1).getFinalScore());
        }
    }
}

