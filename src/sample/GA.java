package sample;
/**
 * Created by Siddharth on 16-06-2015.
 */
public class GA {

    public static int dead=0;
    public static void main(String[] args) {

        // Set a candidate solution
        FitnessCalc.setSolution("ACGTACGTACGTACGTACGTACGTACGTACGTACGTACGTACGTACGTACGTACGTACGTACGT");
        // Create an initial population
        Population myPop = new Population(10000, true);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness() && myPop.size()>1) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness()+" Pop size "+myPop.size()+" Dead "+dead);
            //dead=0;

            for(int i=0;i<64;i++)
                System.out.print(myPop.getFittest().getGene(i));
            System.out.println("");
            myPop = Algorithm.evolvePopulation(myPop);
        }
        if(myPop.size()<2)
            System.out.println("Everyone is dead");
        else {
            System.out.println("Solution found!");
            System.out.println("Generation: " + generationCount);
            System.out.println("Genes:");
            System.out.println(myPop.getFittest());
        }
    }
}