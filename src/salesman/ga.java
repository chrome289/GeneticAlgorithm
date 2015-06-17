package salesman;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Siddharth on 17-06-2015.
 */
public class ga {
    public static int noOfCities=100;
    boolean duplicates(final int[] zipcodelist)
    {
        Set<Integer> lump = new HashSet<Integer>();
        for (int i : zipcodelist)
        {
            if (lump.contains(i))
                return true;
            lump.add(i);
        }
        return false;
    }
    public static void main(String args[]){
        int [][]t=new int[noOfCities][noOfCities];
        for(int i=0;i<noOfCities;i++){
            for(int j=0;j<noOfCities;j++){
                if(i==j)
                    t[i][j]=0;
                else
                    t[i][j]= (int) Math.round(Math.random() * 100);
            }
        }
        fitnessCalc.setSolution(t);

        population pop=new population(2000,true);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while (pop.getFittest().getFitness() > fitnessCalc.getMaxFitness() && generationCount<200) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + pop.getFittest().getFitness());
            //dead=0;
            if(new ga().duplicates(pop.getFittest().city)) {
                System.out.print("Fuck this shit");
               break;
            }
            for(int i=0;i<noOfCities;i++)
                System.out.print(pop.getFittest().getGene(i)+" ");
            System.out.println("");
            pop = algorithm.evolve(pop);
        }
        if(pop.size()<2)
            System.out.println("Everyone is dead");
        else {
            System.out.println("Solution found!");
            System.out.println("Generation: " + generationCount);
            System.out.println("Genes:");
            for(int i=0;i<noOfCities;i++)
                System.out.print(pop.getFittest().getGene(i)+" ");
            System.out.println("");
        }
    }
}
