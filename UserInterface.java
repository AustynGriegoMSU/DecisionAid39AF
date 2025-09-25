import java.util.List;
/**
 * User interface for decision support application.
 * @author Dr. Jody Paul
 * @version 20241114.2
 */
public abstract class UserInterface {
    /** Introductory text. */
    static final String INTRODUCTION =
        "\nDecision Assistant\n\n"
        + "I can help if you are uncertain about a decision you are facing.\n"
        + "This might be selecting an item out of multiple items available,\n"
        + "or choosing which action to take from among several options.\n\n"
        + "After asking just a few questions, I will be able to show you\n"
        + "which of the alternatives you most prefer.\n\n"
        + "-----\n\n"
        + "Note that I have no internal knowledge other than basic\n"
        + "cognitive principles to help elicit your preferences and\n"
        + "basic decision theory formulas for interpreting your responses.\n\n"
        + "I simply can help reveal what you indicate is your preferred choice."
        + "\n\n-----\n\n";

    /** Prompt for type of decision. */
    static final String TYPE_PROMPT =
        "Please tell me what kind of things you need to choose from.\n"
        + "For example...\n"
        + "  if considering different software apps enter: apps\n"
        + "  for what to do this evening you could enter: evening plans\n"
        + "\nChoose from among what type of things?";

    /** Prompt for first alternative. */
    static final String FIRST_ALT_PROMPT =
        "Please enter one of the alternatives that you are considering";

    /** Prompt for additional alternative. */
    static final String ADDITIONAL_ALT_PROMT =
        "Please enter another alternative"
        + " (leave blank if all have been entered)";

    /** Prompt for first factor. */
    static final String FIRST_FACTOR_PROMPT =
        "Please enter one factor that would influence"
        + " your choice among alternatives";

    /** Prompt for additional factors. */
    static final String ADDITIONAL_FACTOR_PROMPT =
        "Please enter another factor"
        + " (leave blank if all have been entered)";

    /**
     * Show introduction.
     */
    public abstract void showIntroduction();

    /**
     * Elicit alternatives.
     * @return a collection of alternatives
     */
    public abstract List<Alternative> getAlternatives();

    /**
     * Elicit factors.
     * @return a collection of factors
     */
    public abstract List<Factor> getFactors();

    /**
     * Elicit and update relative weights of factors.
     * @param factors the factors whose weights are to be updated
     * @param defaultVal the default value to use as pairwise comparison base
     */
    public abstract void getFactorRankings(List<Factor> factors,
                                           int defaultVal);

    /**
     * Elicit cross-rankings of alternatives x factors.
     * @param alternatives the alternatives to be considered
     * @param factors the factors to use in ranking alternatives
     * @param defaultValue the default value to use as pairwise comparison base
     * @return matrix of cross-rankings
     */
    public abstract double[][] getCrossRankings(List<Alternative> alternatives,
                                                List<Factor> factors,
                                                int defaultValue);

    /**
     * Present the alternatives and their final rank ordering.
     * @param alternatives the collection of alternatives to be presented
     */
    public abstract void showResults(List<Alternative> alternatives);
}
