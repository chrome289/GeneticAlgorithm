package timetable;

/**
 * Created by Siddharth on 18-06-2015.
 */
public class ga {
    //public static int noOfCities=100;

    public static void main(String args[]) {

        population pop = new population(1000, true);

        // Evolve our population until we reach an optimum solution

        System.out.println("Fittest :" + pop.getFittest().getFitness() + " ");
        int generationCount = 0;
        int prevValue = 0, curValue = 0;
        while (pop.getFittest().getFitness() > fitnessCalc.getMaxFitness()) {
            //Runtime runtime = Runtime.getRuntime();
            //runtime.gc();
            generationCount++;
            curValue = pop.getFittest().getFitness();
            System.out.println("Generation: " + generationCount + " Fittest: " + curValue);
            pop = algorithm.evolve(pop);
        }
        if (pop.size() < 2)
            System.out.println("Everyone is dead");
        else {
            System.out.println("Solution found!");
            System.out.println("Generation: " + generationCount+" Fitness : "+pop.getFittest().getFitness());
            System.out.println("Genes:");
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    System.out.print(pop.getFittest().getGene(i, j) + " ");
                }
            }
            System.out.println("");
        }
    }
}