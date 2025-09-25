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
        int firstAttribute = 0;
        factors.get(firstAttribute).setRank(standard);
        System.out.println("\n\nAssume that "
            + factors.get(firstAttribute).getName()
            + " has an importance of " + standard + ",\n"
            + "    and that higher values are more important.\n");
        for (int i = firstAttribute + 1; i < factors.size(); i++) {
             System.out.println("        How important is "
                              + factors.get(i).getName() + "? ");
             String importance = sc.nextLine();
             if (importance == null || "".equals(importance)) {
                 factors.get(i).setRank(standard);
             } else {
                 factors.get(i).setRank(Integer.valueOf(importance));
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
            int firstAlternative = 0;
            crossRankings[firstAlternative][i] = standard;
            System.out.println("\n\nConsidering " + factors.get(i).getName()
                + " only...");
            System.out.println("    if "
                    + alternatives.get(firstAlternative).getDescriptor()
                    + " has a value of " + standard + "... ");
            for (int j = firstAlternative + 1; j < alternatives.size(); j++) {
                System.out.println("        ...what value would you associate"
                    + "with " + alternatives.get(j).getDescriptor() + ": ");
                String rank = sc.nextLine();
                if (rank == null || "".equals(rank)) {
                    crossRankings[j][i] = standard;
                } else {
                    crossRankings[j][i] = Integer.valueOf(rank);
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
}
