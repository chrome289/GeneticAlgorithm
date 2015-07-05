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
    public static int noOfCities = 439;
    public static double [][] t = new double[noOfCities][noOfCities];
    public static int[][] coor = new int[noOfCities][2];
    public static int[] x = new int[noOfCities], y = new int[noOfCities];
    public static individual ind;
    public static int fitnessHigh = 0;
    public static int popSize=1200;

    public ga() {
    }
    public static boolean intersection(
                int x1,int y1,int x2,int y2,
                int x3, int y3, int x4,int y4
              ) {
        int d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (d == 0) {
            //System.out.println("dsfsdfs ");
            return false;
        }

        int xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
        int yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
        if (xi == x1 && xi == y1)
            return false;
        else if (xi == x2 && xi == y2)
            return false;
        else if (xi == x3 && xi == y3)
            return false;
        else if (xi == x4 && xi == y4)
            return false;
        else
            return true;
    }

    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D gr2 = (Graphics2D) gr;
        gr2.drawString("Fitness   ---->    " + fitnessHigh, 600, 50);
        double g1 = 120.0 / 1366.0;
        double g2 = 45.0 / 768.0;//graph scaling factor
        for (int i = 0; i < noOfCities; i++) {
            gr2.setStroke(new BasicStroke(4));
            gr2.drawLine((int) (x[i] * g1), (int) (y[i] * g2), (int) (x[i] * g1), (int) (y[i] * g2));
        }
        for (int i = 0; i < noOfCities - 1; i++) {
            gr2.setStroke(new BasicStroke(1));
            int q = ind.getGene(i + 1);
            int u = ind.getGene(i);
            gr2.drawLine((int) (x[q] * g1), (int) (y[q] * g2), (int) (x[u] * g1), (int) (y[u] *g2));
        }
    }

    public static void main(String args[]) throws InterruptedException {
        File file = new File("d:/pr439.tsp");
        try {
            Scanner scanner = new Scanner(file);
            for (int t = 0; t < 6; t++) {
                String line = scanner.nextLine();
            }
            for (int i = 0; i < noOfCities; i++) {
                String line = scanner.nextLine();
                String[] s = line.split(" ");
                coor[i][0] = Integer.parseInt(s[1]); //using tsplib format change for custom
                coor[i][1] = Integer.parseInt(s[2]);
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
                t[i][j] = d;
            }
            //System.out.println("");
        }

        fitnessCalc.setSolution(t);

        population pop = new population(popSize, true);

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
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //f.setUndecorated(true);
        f.setVisible(true);
        while (pop.getFittest().getFitness() > fitnessCalc.getMaxFitness() && generationCount < 4000) {

            //reseeding after 100 gen
            if (generationCount > 100) {
                population newpop = new population(popSize, true);
                newpop.saveindividual(0, pop.getFittest());
                pop.removeIndividual(pop.getFittestpos());
                for (int c = 1; c < popSize/24; c++) {
                    ind = pop.getFittest();
                    pop.removeIndividual(pop.getFittestpos());
                    boolean intersect = true;
                    int a = 0;
                    while (a!=100) {
                        a++;
                        intersect = false;
                        for (int i = 0; i < ga.noOfCities - 1; i++) {
                            int q = ind.getGene(i + 1);
                            int u = ind.getGene(i);
                            for (int j = 0; j < ga.noOfCities - 1; j++) {
                                int q2 = ind.getGene(j + 1);
                                int u2 =ind.getGene(j);
                                intersect = ga.intersection(ga.x[q], ga.y[q], ga.x[u], ga.y[u], ga.x[q2], ga.y[q2], ga.x[u2], ga.y[u2]);
                                if (intersect) {
                                    int t1 = q;
                                    ind.setGene(i + 1, u2);
                                    ind.setGene(j, t1);
                                    break;
                                }
                            }
                            if (intersect)
                                break;
                        }
                    }
                    newpop.saveindividual(c, ind);
                }
                pop = newpop;
                generationCount = 0;
            }
            generationCount++;

            ind = pop.getFittest();
            curValue = ind.getFitness();

            //display current path
            if (generationCount % 20 == 0) {
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
