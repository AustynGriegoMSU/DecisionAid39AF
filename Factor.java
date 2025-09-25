/**
 * A decision-making factor (characteristic).
 * Implements Serializable to facilitate saving and restoring
 * collections of Factor objects.
 * @author Dr. Jody Paul
 * @version 20241113.3
 */
public class Factor implements java.io.Serializable {
    /** Serialization identifier. */
    private static final long serialVersionUID = 202411130601L;

    /** The default ranking of a Factor. */
    public static final int DEFAULT_RANK = 1;

    /** The descriptive name of this factor. */
    private String name;
    /** The relative ranking of importance of this factor. */
    private int rank;

    /**
     * Constructor that establishes the factor's name
     * and sets the ranking to the default.
     * @param theName the name of this factor
     */
    public Factor(final String theName) {
        this.name = theName;
        this.rank = DEFAULT_RANK;
    }

    /**
     * Modify the ranking of this factor.
     * @param newRank the new value for ranking
     */
    public void setRank(final int newRank) {
        this.rank = newRank;
    }

    /**
     * Access the name of this factor.
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Access the rank of this alternative.
     * @return the ranking
     */
    public int getRank() {
        return this.rank;
    }
}
