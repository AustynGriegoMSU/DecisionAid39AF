/**
 * An alternative item or course of action.
 * Implements Serializable to facilitate saving and restoring
 * collections of Alternative objects.
 * @author Dr. Jody Paul
 * @version 20241113.4
 */
public class Alternative implements Comparable<Alternative>,
                                    java.io.Serializable {
    /** Serialization identifier. */
    private static final long serialVersionUID = 202411130104L;

    /** The default final score is 0. */
    public static final int DEFAULT_SCORE = 0;

    /** The descriptor of this alternative. */
    private String descriptor;
    /** The final score of this alternative. */
    private int finalScore;

    /**
     * Constructor that establishes the descriptor
     * and sets the final score to the default.
     * @param theDescriptor the descriptor of the new alternative
     */
    public Alternative(final String theDescriptor) {
        this.descriptor = theDescriptor;
        this.finalScore = DEFAULT_SCORE;
    }

    /**
     * Modify the final score.
     * @param newScore the new value for final score
     */
    public void setFinalScore(final int newScore) {
        this.finalScore = newScore;
    }

    /**
     * Access the desciptor of this alternative.
     * @return the descriptor
     */
    public String getDescriptor() {
        return this.descriptor;
    }

    /**
     * Access the final score of this alternative.
     * @return the final score
     */
    public int getFinalScore() {
        return this.finalScore;
    }

    @Override
    public String toString() {
        return this.descriptor + " == " + this.finalScore;
    }

    @Override
    public int hashCode() {
        return this.descriptor.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alternative alt = (Alternative) o;
        return this.descriptor.equals(alt.descriptor);
    }

    @Override
    public int compareTo(final Alternative alt) {
        if (this == alt) {
            return 0;
        }
        return Integer.compare(this.finalScore, alt.finalScore);
    }
}
