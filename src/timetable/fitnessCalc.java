package timetable;

/**
 * Created by Siddharth on 18-06-2015.
 */
public class fitnessCalc {


    private static int noOfCities=100;
    static int[][] solution = new int[noOfCities][noOfCities];

    /* Public methods */
    // Set a candidate solution as a byte array
    public static void setSolution(int [][] newSolution) {
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
        int fitness=0;
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                //System.out.println(individual.schedule[i][j].charAt(0));
                if(individual.schedule[i][j].charAt(0)==individual.schedule[i][j].charAt(2))
                    fitness++;
                if(individual.schedule[i][j].charAt(2)==individual.schedule[i][j].charAt(4))
                    fitness++;
                if(individual.schedule[i][j].charAt(0)==individual.schedule[i][j].charAt(4))
                    fitness++;
            }
        }
        return fitness;
    }

    // Get optimum fitness
    static int getMaxFitness() {
        int maxFitness=0;
        return maxFitness;
    }
}