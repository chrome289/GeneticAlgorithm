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
    public static int noOfCities = 200;
    public static int[][] t = new int[noOfCities][noOfCities];
    public static int[][]coor=new int[noOfCities][2];

    public static void main(String args[]) throws InterruptedException {
        /*for(int i=0;i<noOfCities;i++){
            for(int j=0;j<noOfCities;j++){
                if(i==j)
                    t[i][j]=0;
                else
                    t[i][j]= (int) Math.round(Math.random() * 100);
            }
        }*/
        File file = new File("d:/filename.txt");
        try {
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < noOfCities; i++) {
                String line = scanner.nextLine();
                String[] s = line.split(" ");
                coor[i][0] = Integer.parseInt(s[0]);
                coor[i][1] = Integer.parseInt(s[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i=0;i<noOfCities;i++) {
            for (int j = 0; j < noOfCities; j++) {
                int x=Math.abs(coor[i][0]-coor[j][0]);
                int y=Math.abs(coor[i][1]-coor[j][1]);
                x= (int) Math.pow(x,2);y= (int) Math.pow(y,2);
                x=x+y;
                double d=Math.sqrt(x);
                t[i][j]=x;
            }
            System.out.println("");
        }

        fitnessCalc.setSolution(t);

        population pop = new population(500, true);

        // Evolve our population until we reach an optimum solution

        System.out.println(" " + pop.getFittest().getFitness() + " ");
        int generationCount = 0;
        int prevValue = 0, curValue = 0;
        Thread t1=new Thread();
        while (pop.getFittest().getFitness() > fitnessCalc.getMaxFitness() && generationCount < 400) {

            //reseeding after 50 gen
            if (generationCount > 100) {
                population newpop = new population(500, true);
                for (int c = 0; c < 200; c++) {
                    newpop.saveindividual(c, pop.getFittest());
                    pop.removeIndividual(pop.getFittestpos());
                }
                pop = newpop;
                generationCount = 0;
            }
            generationCount++;
            curValue = pop.getFittest().getFitness();
            //increasing mutation if growth stagnant
            /*if(curValue==prevValue)
                algorithm.mutationRate+=0.006f;
            else
                algorithm.mutationRate+=0.002f;
            prevValue=curValue;*/

            System.out.println("Generation: " + generationCount + " Fittest: " + curValue + " Mutation Rate: " + algorithm.mutationRate);
            //dead=0;
            if(new ga().duplicates(pop.getFittest().city)) {
                System.out.print("Fuck this shit");
               break;
            }
            /*for(int i=0;i<noOfCities;i++)
                System.out.print(pop.getFittest().getGene(i)+" ");
            System.out.println("");*/
            pop = algorithm.evolve(pop);
        }
        if (pop.size() < 2)
            System.out.println("Everyone is dead");
        else {
            System.out.println("Solution found!");
            System.out.println("Generation: " + generationCount);
            System.out.println("Genes:");
            for (int i = 0; i < noOfCities; i++)
                System.out.print(pop.getFittest().getGene(i) + " ");
            System.out.println("");
        }
    }

    boolean duplicates(final int[] zipcodelist) {
        Set<Integer> lump = new HashSet<Integer>();
        for (int i : zipcodelist) {
            if (lump.contains(i))
                return true;
            lump.add(i);
        }
        return false;
    }
}
