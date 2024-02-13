import java.util.ArrayList;
import java.util.List;

public class Main {

    // array to store the Count occurrences of each sum (2-12)
    private static int[] sumCount = new int[13];

    // Calculate the total number of combinations
    private static int calculateTotalCombinations(int diceASize, int diceBSize) {
        return diceASize * diceBSize;
    }

    // Possible combinations of dice A
    private static void diceAPossibleCombinations(List<Integer> arr, int length, List<Integer> present,
            List<List<Integer>> diceACombinations) {
        if (present.size() == length) {
            diceACombinations.add(new ArrayList<>(present));
            return;
        }

        for (int element : arr) {
            present.add(element);
            diceAPossibleCombinations(arr, length, present, diceACombinations);
            present.remove(present.size() - 1);
        }
    }

    // Possible combinations of dice B
    private static void diceBPossibleCombinations(List<Integer> arr, int length, int start, List<Integer> present,
            List<List<Integer>> diceBCombinations) {
        if (present.size() == length) {
            diceBCombinations.add(new ArrayList<>(present));
            return;
        }

        for (int i = start; i < arr.size(); i++) {
            present.add(arr.get(i));
            diceBPossibleCombinations(arr, length, i + 1, present, diceBCombinations);
            present.remove(present.size() - 1);
        }
    }

    // Calculate and display the distribution of combinations
    private static void calculateCombinationDistribution(List<Integer> diceA, List<Integer> diceB) {
        int numSides = 6;
        int[][] sumMatrix = new int[numSides][numSides]; // 6 x 6 matrix

        for (int i = 0; i < numSides; ++i) {
            for (int j = 0; j < numSides; ++j) {

                sumMatrix[i][j] = diceA.get(i) + diceB.get(j); // sum value
            }
        }

        // Distribution matrix
        System.out.println("Distribution Matrix (i, j):");
        System.out.println("======================================================================");
        for (int i = 0; i < numSides; ++i) {
            for (int j = 0; j < numSides; ++j) {
                System.out.printf("(%d, %d)\t", i + 1, j + 1);
            }
            System.out.println();
        }
        System.out.println("======================================================================");

        // Display the sum matrix
        System.out.println("Distribution Sum Matrix:");
        System.out.println("======================================================================");
        for (int i = 0; i < numSides; ++i) {
            for (int j = 0; j < numSides; ++j) {
                System.out.print(sumMatrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("======================================================================");
    }

    // Calculate the probability of sums
    private static void calculateProbabilityOfSums(List<Integer> diceA, List<Integer> diceB) {

        for (int i = 0; i < diceA.size(); ++i) {
            for (int j = 0; j < diceB.size(); ++j) {
                int sum = diceA.get(i) + diceB.get(j);
                ++sumCount[sum];
            }
        }

        // Print the custom probabilities
        int totalCombinations = calculateTotalCombinations(diceA.size(), diceB.size());
        System.out.println("Probabilities of sums before transformation:");
        System.out.println("======================================================================");
        for (int s = 2; s <= 12; ++s) {
            double probability = (double) sumCount[s] / totalCombinations;
            System.out.printf("P(Sum = %d) = %d/%d = %.7f\n", s, sumCount[s], totalCombinations, probability);
        }
    }

    // Transform Function to undoom the dice
    private static void undoomDice(List<Integer> diceA, List<Integer> diceB) {
        System.out.println("=====================================================================");
        System.out.println("Undooming dice and Reattaching the spots :");

        // generating the Dice_A combinations
        List<Integer> customDiceA = List.of(1, 2, 3, 4);
        List<Integer> presentA = new ArrayList<>();
        List<List<Integer>> diceACombinationList = new ArrayList<>();
        diceAPossibleCombinations(customDiceA, 6, presentA, diceACombinationList);

        // generating the Dice_B combinations
        List<Integer> customDiceB = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> presentB = new ArrayList<>();
        List<List<Integer>> diceBCombinationList = new ArrayList<>();
        diceBPossibleCombinations(customDiceB, 6, 0, presentB, diceBCombinationList);

        // Iterate through all combinations to find the matches with probability
        // distribution before transformation
        for (List<Integer> combinationA : diceACombinationList) {
            for (List<Integer> combinationB : diceBCombinationList) {
                int[] tempSumCount = new int[13];

                // Calculate the sum of each combination
                for (int k : combinationA) {
                    for (int l : combinationB) {
                        int sum = k + l;
                        // Check if the sum is within the valid range
                        if (sum >= 2 && sum <= 12) {
                            ++tempSumCount[sum];
                        }
                    }
                }

                // Check if the probability distribution matches the probability distribution
                // before transformation
                boolean distributionMatches = true;
                for (int s = 2; s <= 12; ++s) {
                    if (tempSumCount[s] != sumCount[s]) {
                        distributionMatches = false;
                        break;
                    }
                }

                // If the distribution matches, print the transformed dice and probabilities
                if (distributionMatches) {
                    System.out.println("======================================================================");
                    System.out.print("Transformed DiceA : ");
                    System.out.println(combinationA);
                    System.out.println("======================================================================");

                    System.out.print("Transformed DiceB : ");
                    System.out.println(combinationB);
                    System.out.println("======================================================================");
                    System.out.print("Probability of sums After Transforming:");
                    System.out.println("\n======================================================================");

                    int totalCombinations = calculateTotalCombinations(diceA.size(), diceB.size());

                    for (int s = 2; s <= 12; ++s) {
                        double probability = (double) tempSumCount[s] / totalCombinations;
                        System.out.printf("P(Sum = %d) = %d/%d = %.7f\n", s, tempSumCount[s], totalCombinations,
                                probability);
                    }
                    return; // No need to continue searching
                }
            }
        }
    }

    public static void main(String[] args) {
        // Original dice values
        List<Integer> originalDiceA = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> originalDiceB = List.of(1, 2, 3, 4, 5, 6);

        System.out.println("======================================================================");
        System.out.print("Dice A before transformation : ");
        System.out.println(originalDiceA);
        System.out.println("======================================================================");

        System.out.print("Dice B before transformation: ");
        System.out.println(originalDiceB);
        System.out.println("======================================================================");

        int combinations = calculateTotalCombinations(originalDiceA.size(), originalDiceB.size());
        System.out.println("Total Combinations : " + combinations);
        System.out.println("======================================================================");

        calculateCombinationDistribution(originalDiceA, originalDiceB);

        // Probabilities before transformation
        calculateProbabilityOfSums(originalDiceA, originalDiceB);

        // Call the function to undo the transformation and print the results
        undoomDice(originalDiceA, originalDiceB);
    }
}
