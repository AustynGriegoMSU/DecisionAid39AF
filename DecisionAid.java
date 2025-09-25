import java.util.List;
import java.util.Collections;
/**
 * Decision support aid.
 * @author Dr. Jody Paul
 * @version 20241115.1.3c
 */
public final class DecisionAid {
    /** Copyright notice */
    private static final String COPYRIGHT = "Copyright (c) Dr. Jody Paul, All Rights Reserved";

    /** Default standard value for two-way comparisons. */
    private static final int STANDARD = 10;

    /** Default maximum value of top choice post-normalization. */
    private static final int TOP_CHOICE_VALUE = 100;

    /** Utility class hides constructor. */
    private DecisionAid() { }

    /**
     * Decision support aid execution entry.
     * @param args ignored
     */
    public static void main(final String[] args) {
        List<Alternative> alternatives;
        List<Factor> factors;
        double[][] crossRankings; // [Alternative][Factor]
        UserInterface ui;
        ui = new JPGUI();  // Use graphical user interface
        // ui = new JPCUI();  // Use console user interface
        // ui = new AntCUI(); // Use console user interface for Ant
        ui.showIntroduction();
        alternatives = ui.getAlternatives();
        factors = ui.getFactors();
        ui.getFactorRankings(factors, STANDARD);
        crossRankings = ui.getCrossRankings(alternatives, factors, STANDARD);
        calculateFinalScores(alternatives, factors, crossRankings);
        sortDescending(alternatives);
        ui.showResults(alternatives);
    }

    /**
     * Compute the weighted sums and normalize.
     * @param alternatives the collection of alternative choices
     * @param factors the collection of factors affecting decision
     * @param crossRankings the matrix of ranking values
     *                      (alternatives x factors)
     */
    private static void calculateFinalScores(
                            final List<Alternative> alternatives,
                            final List<Factor> factors,
                            final double[][] crossRankings) {
        // Normalize within each factor
        double[][] normalizedRankings =
                        normalizeWithinEachFactor(crossRankings);

        // Compute weighted sum for each alternative
        double[] scores =
                    computeWeightedSumForEachAlternative(factors,
                                                         normalizedRankings);

        // Normalize and set the final scores
        normalizeAndSetFinalScore(alternatives, scores);
    }

    /**
     * Normalize cross-rankings within each factor.
     * @param crossRankings matrix of cross-ranking values
     * @return factor-based normalized cross-ranking values
     */
    public static double[][] normalizeWithinEachFactor(
                                 final double[][] crossRankings) {
        double[][] normalizedRankings =
                new double[crossRankings.length][crossRankings[0].length];
        for (int fIndex = 0; fIndex < crossRankings[0].length; fIndex++) {
            double sum = 0.0;
            for (int aIndex = 0; aIndex < crossRankings.length; aIndex++) {
                sum += crossRankings[aIndex][fIndex];
            }
            for (int aIndex = 0; aIndex < crossRankings.length; aIndex++) {
                normalizedRankings[aIndex][fIndex] =
                    crossRankings[aIndex][fIndex] / sum;
            }
        }
        return normalizedRankings;
    }

    /**
     * Compute the weighted sum of scores for each alternative.
     * @param factors the collection of factors as source of relative weights
     * @param normalizedRankings cross-rankings matrix normalized within factors
     * @return weighted sums for alternatives suitable for use in normalization
     */
    public static double[] computeWeightedSumForEachAlternative(
                                    final List<Factor> factors,
                                    final double[][] normalizedRankings) {
        int numAlternatives = normalizedRankings.length;
        int numFactors = factors.size();
        double[] scores = new double[numAlternatives];
        for (int altIndex = 0; altIndex < numAlternatives; altIndex++) {
            double score = 0.0;
            for (int factorIndex = 0; factorIndex < numFactors; factorIndex++) {
                score += normalizedRankings[altIndex][factorIndex]
                         * factors.get(factorIndex).getRank();
            }
            scores[altIndex] = score;
        }
        return scores;
    }

    /**
     * Set the final score for each alternative after normalization.
     * Uses the default maximum value for a top choice and the
     * weighted sum scores.
     * @param alternatives the collection of alternatives
     * @param scores the weighted sum scores for each alternative
     * @return collection of alternatives that include final score values
     */
    public static List<Alternative> normalizeAndSetFinalScore(
                        final List<Alternative> alternatives,
                        final double[] scores) {
        double max = 0.0;
        for (double s : scores) {
            if (max < s) {
                max = s;
            }
        }
        for (int altIndex = 0; altIndex < alternatives.size(); altIndex++) {
            alternatives.get(altIndex).setFinalScore(
                        (int) (TOP_CHOICE_VALUE * (scores[altIndex] / max)));
        }
        return alternatives;
    }

    /**
     * Sort a list of alternatives into descending order.
     * @param listToSort the collection of alternatives to be sorted
     */
    public static void sortDescending(final List<Alternative> listToSort) {
        Collections.sort(listToSort);
        Collections.reverse(listToSort);
    }
}
