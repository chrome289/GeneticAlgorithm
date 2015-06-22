package salesman;

/**
 * Created by Siddharth on 17-06-415.
 */
public class algorithm extends Thread {

    public static boolean elitism = true;
    public static int tournamentsize = 30;
    public static float mutationRate = 0.015f;
    private static int noOfCities = 200;
    public int low, high;
    public population pop1, pop2;

    public algorithm(int l, int h, population p) {
        this.low = l;
        this.high = h;
        this.pop1 = p;
    }

    public static population evolve(population pop) throws InterruptedException {
        population newpop = new population(10000, false);

        //keep fittest
        if (elitism)
            newpop.saveindividual(0, pop.getFittest());
        algorithm t1 = new algorithm(0, pop.size() / 5, pop);
        t1.setName("thread 1");
        t1.setPriority(10);
        Thread u1 = new Thread(t1);
        u1.start();

        algorithm t2 = new algorithm(0, pop.size() / 5, pop);
        t2.setName("thread 2");
        t2.setPriority(10);
        Thread u2 = new Thread(t2);
        u2.start();

        algorithm t3 = new algorithm(0, pop.size() / 5, pop);
        t3.setName("thread 3");
        t3.setPriority(10);
        Thread u3 = new Thread(t3);
        u3.start();

        algorithm t4 = new algorithm(0, pop.size() / 5, pop);
        t4.setName("thread 4");
        t4.setPriority(10);
        Thread u4 = new Thread(t4);
        u4.start();

        algorithm t5 = new algorithm(0, pop.size() / 5, pop);
        t5.setName("thread 5");
        t5.setPriority(10);
        Thread u5 = new Thread(t5);
        u5.start();

        u1.join();
        u2.join();
        u3.join();
        u4.join();
        u5.join();
        newpop = combine(t1.pop2, t2.pop2, t3.pop2, t4.pop2, t5.pop2);


        //System.out.println(newpop.size());
        return newpop;
    }

    private static individual mutate(individual newindiv) {
        for (int i = 0; i < 20; i++) {
            if (Math.random() < mutationRate) {
                int t1 = (int) Math.round(Math.random() * (noOfCities - 1)), t2 = (int) Math.round(Math.random() * (noOfCities - 1));
                int t3 = newindiv.city[t1];
                newindiv.city[t1] = newindiv.city[t2];
                newindiv.city[t2] = t3;
            }
        }
        return newindiv;
    }

    private static individual[] crossover(individual indiv1, individual indiv2) {
        individual []indiv3 = new individual[2];
        indiv3[0]=new individual();indiv3[1]=new individual();

        int []k=new int[noOfCities];
        indiv3[0].city[0]=indiv1.city[0];k[indiv1.city[0]]=1;
        for(int i=1;i<noOfCities;i++) {
            int x1 = 0, x2 = 0;
            for (int j = 0; j < noOfCities - 1; j++) {                  //get next node on both parents
                if (indiv1.city[j] == indiv3[0].city[i - 1])
                    x1 = indiv1.city[j + 1];
                if (indiv2.city[j] == indiv3[0].city[i - 1])
                    x2 = indiv2.city[j + 1];
            }
            if (ga.t[indiv3[0].city[i - 1]][x1] < ga.t[indiv3[0].city[i - 1]][x2] && k[x1] == 0 && ga.t[indiv3[0].city[i - 1]][x1] != 0) {
                indiv3[0].city[i] = x1;
                k[x1] = 1;
            } else if (ga.t[indiv3[0].city[i - 1]][x2] < ga.t[indiv3[0].city[i - 1]][x1] && k[x2] == 0 && ga.t[indiv3[0].city[i - 1]][x2] != 0) {
                indiv3[0].city[i] = x2;
                k[x2] = 1;
            } else if (k[x1] == 0 && ga.t[indiv3[0].city[i - 1]][x1] != 0) {
                indiv3[0].city[i] = x1;
                k[x1] = 1;
            } else if (k[x2] == 0 && ga.t[indiv3[0].city[i - 1]][x2] != 0) {
                indiv3[0].city[i] = x2;
                k[x2] = 1;
            } else {
                for (int x = 0; x < noOfCities; x++) {
                    if (k[x] == 0) {
                        indiv3[0].city[i] = x;
                        k[x] = 1;
                        break;
                    }
                }
            }
        }


       /* k=new int[noOfCities];
        indiv3[1].city[0]=indiv2.city[0];k[indiv2.city[0]]=1;
        for(int i=1;i<noOfCities;i++) {
            int x1 = 0, x2 = 0;
            for (int j = 0; j < noOfCities-1; j++) {                  //get next node on both parents

                if (indiv1.city[j] == indiv3[1].city[i - 1])
                    x1 = indiv1.city[j + 1];
                if (indiv2.city[j] == indiv3[1].city[i - 1])
                    x2 = indiv2.city[j + 1];
            }
            if (ga.t[indiv3[1].city[i - 1]][x1] < ga.t[indiv3[1].city[i - 1]][x2] && k[x1] == 0 && ga.t[indiv3[1].city[i - 1]][x1] != 0) {
                indiv3[1].city[i] = x1;
                k[x1] = 1;
            } else if (ga.t[indiv3[1].city[i - 1]][x2] < ga.t[indiv3[1].city[i - 1]][x1] && k[x2] == 0 && ga.t[indiv3[1].city[i - 1]][x2] != 0) {
                indiv3[1].city[i] = x2;
                k[x2] = 1;
            } else {
                for (int x = 0; x < noOfCities; x++) {
                    if (k[x] == 0) {
                        indiv3[1].city[i] = x;
                        k[x] = 1;
                        break;
                    }
                }
            }
        }*/
        return indiv3;
    }

    private static individual selection(population pop) {
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

    private static population combine(population p1, population p2, population p3, population p4, population p11) {
        population p5 = new population(500, false);
        int t1 = 0, t2, t3, t4;
        t2 = p1.size();
        t3 = p1.size() * 2;
        t4 = p1.size() * 3;
        for (int i = 0; i < p1.size(); i++) {
            p5.saveindividual(t1 + i, p1.getIndividual(i));
            p5.saveindividual(t2 + i, p2.getIndividual(i));
            p5.saveindividual(t3 + i, p3.getIndividual(i));
            p5.saveindividual(t4 + i, p4.getIndividual(i));
            p5.saveindividual(t4 + i + t2, p11.getIndividual(i));

        }
        return p5;
    }

    public void run() {
        pop2 = new population(pop1.size() / 5, false);
        for (int i = 0; i < pop1.size() / 5; i=i+1) {

            individual indiv1 = selection(pop1);
            individual indiv2 = selection(pop1);
            individual []newindiv = crossover(indiv1, indiv2);

            newindiv[0] = mutate(newindiv[0]);
            pop2.saveindividual(i, newindiv[0]);

            //newindiv[1] = mutate(newindiv[1]);
            //pop2.saveindividual(i+1, newindiv[1]);
        }
    }
}