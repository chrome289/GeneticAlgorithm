package salesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
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
        /*for(int i=0;i<noOfCities;i++){
            for(int j=0;j<noOfCities;j++){
                if(i==j)
                    t[i][j]=0;
                else
                    t[i][j]= (int) Math.round(Math.random() * 100);
            }
        }*/
        File file = new File("d:/filename.txt");
        int x=0;
        try {
            Scanner scanner = new Scanner(file);
            for(int i=0;i<noOfCities;i++){
                String line = scanner.nextLine();
                String[]s=line.split(" ");
                for(int j=0;j<noOfCities;j++)
                    t[i][j] = Integer.parseInt(s[j]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*for(int i=0;i<noOfCities;i++) {
            for (int j = 0; j < noOfCities; j++) {
                System.out.print(t[i][j]+" ");
            }
            System.out.println("");
        }*/
        fitnessCalc.setSolution(t);

        population pop=new population(10000,true);

        // Evolve our population until we reach an optimum solution

        System.out.println(" "+pop.getFittest().getFitness()+" ");
        int generationCount=0;int prevValue=0,curValue=0;
        while (pop.getFittest().getFitness() > fitnessCalc.getMaxFitness() && generationCount<400) {
            generationCount++;
            curValue=pop.getFittest().getFitness();
            //increasing mutation if growth stagnant
            if(curValue==prevValue)
                algorithm.mutationRate+=.006f;
            prevValue=curValue;

            System.out.println("Generation: " + generationCount + " Fittest: " + curValue+" Mutation Rate: "+algorithm.mutationRate);
            //dead=0;
            /*if(new ga().duplicates(pop.getFittest().city)) {
                System.out.print("Fuck this shit");
               break;
            }*/
            /*for(int i=0;i<noOfCities;i++)
                System.out.print(pop.getFittest().getGene(i)+" ");
            System.out.println("");*/
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
