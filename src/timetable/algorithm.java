package timetable;

public class algorithm {

    public static boolean elitism = true;
    public static int tournamentsize = 15;
    public static float mutationRate = 0.015f;
    private static int noOfClasses = 4;
    private static int noOfBatches = 6;
    private static int noOfSubjects = 6;

    public static population evolve(population pop) {
        population newpop = new population(pop.size(), false);

        //keep fittest
        if (elitism)
            newpop.saveindividual(0, pop.getFittest());

        for (int i = 1; i < pop.size(); i++) {
            individual indiv1 = selection1(pop);
            individual indiv2 = selection2(pop);
            individual newindiv = crossover(indiv1, indiv2);
            newindiv = mutate(newindiv);

            newpop.saveindividual(i, newindiv);
        }
        return newpop;
    }

    private static individual mutate(individual newindiv) {
        /*String temp1 = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                temp1 = temp1 + newindiv.schedule[i][j];
            }
        }
        if (temp1.length() < 540) {
            System.out.print("we are fucked");
            System.exit(741);

        }*/
        for (int i = 0; i < 50; i++) {
            if (Math.random() > 0.0) {
                int[][] t = new int[600][2];
                int t2;
                t = newindiv.getDetailedFitness();
                t2 = newindiv.getFitness();
                for (int j = 0; j < t2; j = j + 2) {

                    if (mutationRate > 0.00 && Math.random() > 0.5) {
                        if (!(t[j + 1][0] == t[j][0] && t[j + 1][1] == t[j][1])) {
                            String temp = newindiv.schedule[t[j][0]][t[j][1]].substring(0, 6);
                            String temp2 = newindiv.schedule[t[j + 1][0]][t[j + 1][1]].substring(3, 9);
                            String temp3 = newindiv.schedule[t[j + 1][0]][t[j + 1][1]].substring(0, 3);
                            String temp4 = newindiv.schedule[t[j + 1][0]][t[j + 1][1]].substring(9);
                            String temp5 = newindiv.schedule[t[j][0]][t[j][1]].substring(6);
                            newindiv.schedule[t[j][0]][t[j][1]] = temp5 + temp2;
                            newindiv.schedule[t[j + 1][0]][t[j + 1][1]] = temp + temp4 + temp3;
                        }

                    } else {
                        int k1 = (int) Math.round(Math.random() * 5);
                        int k2 = (int) Math.round(Math.random() * 5);
                        if (!(k1 == t[j][0] && k2 == t[j][1])) {
                            //System.out.print("-" + newindiv.schedule[k1][k2] + "," + newindiv.schedule[t[j][0]][t[j][1]] + "-");
                            String temp = newindiv.schedule[t[j][0]][t[j][1]].substring(0, 6);
                            String temp2 = newindiv.schedule[k1][k2].substring(3, 9);
                            String temp3 = newindiv.schedule[k1][k2].substring(0, 3);
                            String temp4 = newindiv.schedule[k1][k2].substring(9, noOfClasses * 3);
                            String temp5 = newindiv.schedule[t[j][0]][t[j][1]].substring(6, noOfClasses * 3);
                            newindiv.schedule[t[j][0]][t[j][1]] = temp5 + temp2;
                            newindiv.schedule[k1][k2] = temp4 + temp3 + temp;
                        }
                    }
                }
            }
        }
        /*temp1 = "";
        boolean f = false;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                temp1 = temp1 + newindiv.schedule[i][j];
                if (newindiv.schedule[i][j].contains("---"))
                    f = true;
            }
        }
        if (temp1.length() < 540 || f == true) {
            System.out.print("we are fucked 2");
            System.exit(741);

        }*/
        return newindiv;
    }

    private static individual crossover(individual indiv1, individual indiv2) {

        individual indiv3 = new individual();
        String temp1 = "", temp2 = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                temp1 = temp1 + indiv1.schedule[i][j];
                temp2 = temp2 + indiv2.schedule[i][j];
            }
        }
        String[] arr1 = new String[6 * 6 * noOfClasses];
        String[] arr2 = new String[6 * 6 * noOfClasses];
        for (int i = 0; i < 6 * 6 * noOfClasses * 3; i = i + 3) {
            arr1[i / 3] = temp1.substring(i, i + 3);
            arr2[i / 3] = temp2.substring(i, i + 3);
            //System.out.print(i+" ");
        }

        String[] arr3 = new String[6 * 6 * noOfClasses];
        for (int i = 0; i < arr3.length; i++)
            arr3[i] = "";

        int h[] = indiv1.bestSequence();
        int t1 = h[0];
        int t2 = h[1];     //edit it

        for (int i = t1; i < t2; i++)
            arr3[i] = arr1[i];

        /*System.out.println("!!!!!!!!!!!!!!!!!!!!");
        for (int i = 0; i < 180; i++)
            System.out.print("#" + arr3[i] + "#");
        System.out.println();*/

        for (int i = 0; i < arr3.length; i++) {
            boolean isthere = false;
            for (int j = 0; j < arr3.length; j++) {
                if (arr3[j].contains(arr2[i])) {
                    //System.out.println("$" + arr3[j] + "$");
                    isthere = true;
                    j = 0;
                    break;
                }
            }

            if (!isthere) {
                for (int j = 0; j < arr3.length; j++) {
                    if (arr3[j].isEmpty()) {
                        arr3[j] = arr2[i];
                        j = 0;
                        isthere = true;
                        break;
                    }
                }
            }
        }


        int k = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                indiv3.schedule[i][j] = "";
                for (int y = 0; y < noOfClasses; y++) {
                    indiv3.schedule[i][j] = indiv3.schedule[i][j] + arr3[k];
                    k++;
                }
            }
        }
       /* System.out.println("Genes:");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(indiv3.getGene(i, j) + " ");
            }
        }*/
       /* temp1 = "";
        boolean f = false;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                temp1 = temp1 + indiv3.schedule[i][j];
                if (indiv3.schedule[i][j].contains("---"))
                    f = true;
            }
        }
        if (temp1.length() < 540 || f == true) {
            System.out.print("we are fucked 3"+temp1.length()+"-"+f);
            System.exit(741);

        }*/
        return indiv3;
    }


    private static individual selection1(population pop) {
        population tournament = new population(tournamentsize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentsize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveindividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        individual fittest = tournament.getFittest();
        //return fittest or random individual
        if (Math.round(Math.random() * 100) % 10 != 0)
            return fittest;
        else
            return tournament.getIndividual(0);
    }

    private static individual selection2(population pop) {
        population tournament = new population(tournamentsize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentsize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveindividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        individual fittest = tournament.getFittest();
        //return fittest or random individual
        if (Math.round(Math.random() * 100) % 10 == 0)
            return fittest;
        else
            return tournament.getIndividual(0);
    }
}