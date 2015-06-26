package timetable;

public class ga {
    private static int noOfClasses = 4;

    public static void main(String args[]) {

        population pop = new population(2000, true);

        // Evolve our population until we reach an optimum solution

        System.out.println("Fittest :" + pop.getFittest().getFitness() + " ");
        int generationCount = 0;
        int prevValue = 0, curValue = 0;
        while (pop.getFittest().getFitness() > fitnessCalc.getMaxFitness()) {
            generationCount++;
            curValue = pop.getFittest().getFitness();
            /*if(curValue==prevValue && algorithm.mutationRate<1.00)
                algorithm.mutationRate+=.03f;
            prevValue=curValue;*/
            System.out.println("Generation: " + generationCount + " Fitness: " + curValue + " Mutation Rate : " + algorithm.mutationRate);
            /*System.out.println("Genes:");
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    System.out.print(pop.getFittest().getGene(i, j) + " ");
                }
            }
            System.out.println("");*/
            pop = algorithm.evolve(pop);
        }
        if (pop.size() < 2)
            System.out.println("Everyone is dead");
        else {
            System.out.println("Solution found!");
            System.out.println("Generation: " + generationCount + " Fitness : " + pop.getFittest().getFitness());
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