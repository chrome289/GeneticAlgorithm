package sample;

/**
 * Created by Siddharth on 16-06-2015.
 */
public class FitnessCalc {

    static char[] solution = new char[64];

    /* Public methods */
    // Set a candidate solution as a byte array
    public static void setSolution(char[] newSolution) {
        solution = newSolution;
    }

    static void setSolution(String newSolution) {
        solution = new char[newSolution.length()];
        // Loop through each character of our string and save it in our byte
        // array
        for (int i = 0; i < newSolution.length(); i++) {
                solution[i] = newSolution.charAt(i);
        }
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static int getFitness(Individual individual) {
        int fitness = 0;
        // Loop through our individuals genes and compare them to our cadidates
        for (int i = 0; i < individual.size() && i < solution.length; i++) {
            if (individual.getGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }

    // Get optimum fitness
    static int getMaxFitness() {
        int maxFitness = solution.length;
        return maxFitness;
    }
}
