package salesman;

/**
 * Created by Siddharth on 17-06-415.
 */
public class fitnessCalc {


    private static int noOfCities = 500;
    static int[][] solution = new int[noOfCities][noOfCities];

    /* Public methods */
    // Set a candidate solution as a byte array
    public static void setSolution(int[][] newSolution) {
        solution = newSolution;
    }

    /*static void setSolution(int [][] newSolution) {
        solution = new int[newSolution.length()][newSolution.length()];
        // Loop through each character of our string and save it in our byte
        // array
        for (int i = 0; i < newSolution.length()/4; i++) {
            for(int j=0;j<newSolution.length()/4;j++) {
                solution[i][j] = newSolution.charAt((i*4)+j);
            }
        }
    }*/
    public static int getFitness(individual individual) {
        int fitness = 0;
        for (int i = 0; i < (noOfCities - 1); i++) {
            fitness += solution[individual.city[i]][individual.city[i + 1]];
        }
        return fitness;
    }

    // Get optimum fitness
    static int getMaxFitness() {
        int maxFitness = 0;
        return maxFitness;
    }
}
