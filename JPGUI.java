import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Font;
/**
 * Graphical user interface for decision support application.
 *
 * @author Dr. Jody Paul
 * @version 20241114.3
 */
public class JPGUI extends UserInterface {
    /** Default point size for JOptionPane dialog text. */
    private static final int DEFAULT_FONT_SIZE = 12;

    static { // Establish a common font for JOptionPane dialogs.
      Font font = new Font("Monospaced", Font.PLAIN, DEFAULT_FONT_SIZE);
      UIManager.put("OptionPane.messageFont", font);
    }

    @Override
    public void showIntroduction() {
        JOptionPane.showMessageDialog(null,
                                      INTRODUCTION,
                                      "Decision Support Aid",
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public List<Alternative> getAlternatives() {
        List<Alternative> alternativeList = new ArrayList<Alternative>();
        String alternative = JOptionPane.showInputDialog(
            FIRST_ALT_PROMPT + " (Cancel to quit.)");
        while (alternative != null && !("".equals(alternative))) {
            alternativeList.add(new Alternative(alternative));
            alternative = JOptionPane.showInputDialog(ADDITIONAL_ALT_PROMT);
        }
        if (alternativeList.size() < 1) {
            System.err.println("No alternatives entered.");
            System.exit(2);
        }
        return alternativeList;
    }

    @Override
    public List<Factor> getFactors() {
        List<Factor> factorList = new ArrayList<Factor>();
        String factor = JOptionPane.showInputDialog(FIRST_FACTOR_PROMPT);
        while (factor != null && !("".equals(factor))) {
            factorList.add(new Factor(factor));
            factor = JOptionPane.showInputDialog(ADDITIONAL_FACTOR_PROMPT);
        }
        if (factorList.size() < 1) {
            System.err.println("No factors entered.");
            System.exit(2);
        }
        return factorList;
    }

    @Override
    public void getFactorRankings(final List<Factor> factorList,
                                  final int standard) {
        int lastFactor = factorList.size() - 1;
        // Use a mid-point baseline of 50 so the comparee is not set to the max.
        final int baseline = Math.min(50, standard);
        for (int i = 0; i < lastFactor; i++) {
            // Keep asking until input is blank (use default) or a valid integer
            while (true) {
                String importance = JOptionPane.showInputDialog(
                    "<HTML>If <B><SIZE=+1>"
                    + factorList.get(lastFactor).getName()
                    + "</SIZE></B> has an <I>importance</I> of <B>"
                    + baseline
                    + "</B>,<BR>"
                    + "how important is <B><SIZE=+1>"
                    + factorList.get(i).getName()
                    + "</SIZE></B> on a scale of 0.." + standard + "? (leave blank for default " + baseline + ")</HTML>");
                if (importance == null || "".equals(importance)) {
                    factorList.get(i).setRank(baseline);
                    break;
                }
                try {
                    int v = Integer.parseInt(importance);
                    if (v < 0 || v > standard) {
                        JOptionPane.showMessageDialog(null,
                            "Please enter a whole number between 0 and " + standard + ".",
                            "Invalid input",
                            JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    factorList.get(i).setRank(v);
                    break;
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null,
                        "Please enter a whole number for importance, or leave blank to accept the default (" + baseline + ")",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        factorList.get(lastFactor).setRank(baseline);
    }

    @Override
    public double[][] getCrossRankings(final List<Alternative> alternatives,
                                       final List<Factor> factors,
                                       final int standard) {
        double[][] crossRankings = new double[alternatives.size()]
                                             [factors.size()];
        for (int i = 0; i < factors.size(); i++) {
            StringBuilder header = new StringBuilder();
            header.append("Considering factor: ").append(factors.get(i).getName());
            for (int j = 0; j < alternatives.size(); j++) {
                String rank = JOptionPane.showInputDialog(
                    header.toString() + "\nEnter a value for '" + alternatives.get(j).getDescriptor() + "' on a scale 0.." + standard + " (default 10):");
                if (rank == null || "".equals(rank)) {
                    crossRankings[j][i] = 10.0;
                } else {
                    while (true) {
                        try {
                            double v = Double.parseDouble(rank);
                            if (v < 0 || v > standard) {
                                rank = JOptionPane.showInputDialog(
                                    "Please enter a numeric value between 0 and " + standard + " (or leave blank for default 10):",
                                    rank);
                                if (rank == null || "".equals(rank)) { crossRankings[j][i] = 10.0; break; }
                                continue;
                            }
                            crossRankings[j][i] = v;
                            break;
                        } catch (NumberFormatException nfe) {
                            rank = JOptionPane.showInputDialog(
                                "Invalid input. Please enter a numeric value (or leave blank for default 10):",
                                rank);
                            if (rank == null || "".equals(rank)) {
                                crossRankings[j][i] = 10.0;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return crossRankings;
    }

    @Override
    public int getScale() {
        // Force a 0-100 scale per user request.
        return 100;
    }

    @Override
    public void showResults(final List<Alternative> alternatives) {
        Alternative preferredAlternative = alternatives.get(0);
        String s = "<HTML>Preferred choice: <B>";
        s += preferredAlternative.getDescriptor();
        s += "</B>\n-----\n";
        for (Alternative a : alternatives) {
            s += a + "\n";
        }
        JOptionPane.showMessageDialog(null,
                                      s,
                                     "Decider Results",
                                     JOptionPane.INFORMATION_MESSAGE);
        int choice = JOptionPane.showConfirmDialog(null,
                                                   "Open decision log viewer?",
                                                   "View Log",
                                                   JOptionPane.YES_NO_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            DecisionLogViewer.showViewer(null);
        }
    }
}
