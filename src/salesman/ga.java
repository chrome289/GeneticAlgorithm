package salesman;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Siddharth on 17-06-2015.
 */

public class ga extends JPanel {
    public static int noOfCities = 500;
    public static int[][] t = new int[noOfCities][noOfCities];
    public static int[][] coor = new int[noOfCities][2];
    public static int []x=new int[noOfCities],y=new int[noOfCities];
    public static individual ind;
    public static int fitnessHigh=0;
    public ga() {
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawString("Fitness   ---->    " + fitnessHigh, 600, 650);
        for (int i = 0; i < noOfCities; i++) {
            g2.setStroke(new BasicStroke(4));
            g2.drawLine(x[i],y[i] , x[i], y[i]);
        }
        for (int i = 0; i < noOfCities-1; i++) {
            g2.setStroke(new BasicStroke(1));
            int q=ind.getGene(i+1);
            int u=ind.getGene(i);
            g2.drawLine(x[q],y[q] , x[u], y[u]);
        }
    }
    public static void main(String args[]) throws InterruptedException {
        /*
*/
        File file = new File("d:/filename.txt");
        try {
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < noOfCities; i++) {
                String line = scanner.nextLine();
                String[] s = line.split(" ");
                coor[i][0] = Integer.parseInt(s[0]);
                coor[i][1] = Integer.parseInt(s[1]);
                ga.x[i] = coor[i][0];
                ga.y[i] = coor[i][1];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < noOfCities; i++) {
            for (int j = 0; j < noOfCities; j++) {
                int x = Math.abs(coor[i][0] - coor[j][0]);
                int y = Math.abs(coor[i][1] - coor[j][1]);
                x = (int) Math.pow(x, 2);
                y = (int) Math.pow(y, 2);
                x = x + y;
                double d = Math.sqrt(x);
                t[i][j] = x;
            }
            //System.out.println("");
        }

        fitnessCalc.setSolution(t);

        population pop = new population(600, true);

        // Evolve our population until we reach an optimum solution

        System.out.println(" " + pop.getFittest().getFitness() + " ");
        int generationCount = 0;
        int prevValue = 0, curValue = 0;

        //initialize JFrame
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new ga());
        f.setTitle("Genetic Algorithm TSP");
        f.setSize(1366, 768);
        f.setLocation(0, 0);
        f.setVisible(true);

        while (pop.getFittest().getFitness() > fitnessCalc.getMaxFitness() && generationCount < 400) {


            //reseeding after 100 gen
            if (generationCount > 100) {

                population newpop = new population(600, true);
                for (int c = 0; c < 300; c++) {
                    newpop.saveindividual(c, pop.getFittest());
                    pop.removeIndividual(pop.getFittestpos());
                }
                pop = newpop;
                generationCount = 0;
            }
            generationCount++;
            ind = pop.getFittest();
            curValue = ind.getFitness();


            //display current path
            if (generationCount % 10 == 0) {
                fitnessHigh = curValue;
                f.paint(f.getGraphics());
            }
            System.out.println("Generation: " + generationCount + " Fittest: " + curValue + " Mutation Rate: " + algorithm.mutationRate);
            //dead=0;
            if (duplicates(pop.getFittest().city)) {
                System.out.print("no shit");
                break;
            }
            /*for(int i=0;i<noOfCities;i++)
                System.out.print(pop.getFittest().getGene(i)+" ");
            System.out.println("");*/
            pop = algorithm.evolve(pop);
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:");
        for (int i = 0; i < noOfCities; i++)
            System.out.print(pop.getFittest().getGene(i) + " ");
        System.out.println("");
    }
    //get if there are duplicates
    static boolean duplicates(final int[] list) {
        Set<Integer> lump = new HashSet<Integer>();
        for (int i : list) {
            if (lump.contains(i))
                return true;
            lump.add(i);
        }
        return false;
    }
}
