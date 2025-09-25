import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Console user interface for decision support application.
 *
 * @author Dr. Jody Paul
 * @version 20241114.1
 */
public class AntCUI extends UserInterface {
    @Override
    public void showIntroduction() {
        System.out.println(INTRODUCTION);
    }

    @Override
    public List<Alternative> getAlternatives() {
        List<Alternative> alternativeList = new ArrayList<Alternative>();
        Scanner sc = new Scanner(System.in);
        System.out.println(FIRST_ALT_PROMPT + ": ");
        String alternative = sc.nextLine();
        while (alternative != null && !("".equals(alternative))) {
            alternativeList.add(new Alternative(alternative));
            System.out.println(ADDITIONAL_ALT_PROMT + ": ");
            alternative = sc.nextLine();
        }
        return alternativeList;
    }

    @Override
    public List<Factor> getFactors() {
        List<Factor> factorList = new ArrayList<Factor>();
        Scanner sc = new Scanner(System.in);
        System.out.println(FIRST_FACTOR_PROMPT + ": ");
        String factor = sc.nextLine();
        while (factor != null && !("".equals(factor))) {
            factorList.add(new Factor(factor));
            System.out.println(ADDITIONAL_FACTOR_PROMPT);
            factor = sc.nextLine();
        }
        return factorList;
    }

    @Override
    public void getFactorRankings(final List<Factor> factors,
                                  final int standard) {
        Scanner sc = new Scanner(System.in);
        // Use a mid-point baseline of 50 so the comparee is not set to the max.
        final int baseline = Math.min(50, standard);
        for (int i = 0; i < factors.size(); i++) {
            while (true) {
                System.out.println("How important is '" + factors.get(i).getName() + "' on a scale 0.." + standard + " (default " + baseline + "): ");
                String importance = sc.nextLine();
                if (importance == null || "".equals(importance)) {
                    factors.get(i).setRank(baseline);
                    break;
                }
                try {
                    int v = Integer.parseInt(importance);
                    if (v < 0 || v > standard) {
                        System.out.println("Please enter a whole number between 0 and " + standard + ".");
                        continue;
                    }
                    factors.get(i).setRank(v);
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter a whole number for importance, or press Enter to accept default (" + baseline + ")");
                }
            }
        }
    }

    @Override
    public double[][] getCrossRankings(final List<Alternative> alternatives,
                                       final List<Factor> factors,
                                       final int standard) {
        Scanner sc = new Scanner(System.in);
        double[][] crossRankings =
                    new double[alternatives.size()][factors.size()];
        for (int i = 0; i < factors.size(); i++) {
            System.out.println("\n\nConsidering factor: " + factors.get(i).getName());
            for (int j = 0; j < alternatives.size(); j++) {
                while (true) {
                    System.out.println("Enter a value for '" + alternatives.get(j).getDescriptor() + "' on a scale 0.." + standard + " (default 10): ");
                    String rank = sc.nextLine();
                    if (rank == null || "".equals(rank)) {
                        crossRankings[j][i] = 10.0;
                        break;
                    }
                    try {
                        double v = Double.parseDouble(rank);
                        if (v < 0 || v > standard) {
                            System.out.println("Please enter a numeric value between 0 and " + standard + ".");
                            continue;
                        }
                        crossRankings[j][i] = v;
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.println("Please enter a numeric value (or press Enter to accept default 10)");
                    }
                }
            }
        }
        return crossRankings;
    }

    @Override
    public void showResults(final List<Alternative> alternatives) {
        Alternative preferredAlternative = alternatives.get(0);
        System.out.println("\n\n================\nPreferred choice: ");
        System.out.println(preferredAlternative.getDescriptor());
        System.out.println("-----");
        for (Alternative a : alternatives) {
            System.out.println(a);
        }
    }

    @Override
    public int getScale() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter baseline scale for comparisons (positive integer, default 100): ");
        String s = sc.nextLine();
        if (s == null || "".equals(s)) {
            return 100;
        }
        try {
            int v = Integer.parseInt(s);
            return v > 0 ? v : 100;
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid input. Using default scale 100.");
            return 100;
        }
    }
}
