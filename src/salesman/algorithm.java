package salesman;

/**
 * Created by Siddharth on 17-06-415.
 */
public class algorithm {

    public static boolean elitism = true;
    public static int tournamentsize=30;
    private static int noOfCities=100;
    public static float mutationRate=0.010f;

    public static population evolve(population pop) {
        population newpop = new population(pop.size(), false);

        //keep fittest
        if (elitism)
            newpop.saveindividual(0, pop.getFittest());

        for (int i = 1; i < pop.size(); i++) {
            individual indiv1 = selection(pop);
            individual indiv2 = selection(pop);
            individual newindiv = crossover(indiv1, indiv2);
            newindiv=mutate(newindiv);
            newpop.saveindividual(i, newindiv);
        }
        return newpop;
    }

    private static individual mutate(individual newindiv) {
        for(int i=0;i<5;i++){
            if(Math.random()<mutationRate){
                int t1= (int) Math.round(Math.random()*(noOfCities-1)),t2= (int) Math.round(Math.random()*(noOfCities-1));
                int t3=newindiv.city[t1];
                newindiv.city[t1]=newindiv.city[t2];
                newindiv.city[t2]=t3;
            }
        }
        return newindiv;
    }

    private static individual crossover(individual indiv1, individual indiv2) {
        individual indiv3 = new individual();
        int t1= (int) Math.round(Math.random()*(noOfCities/2));
        int t2=t1+Math.round(noOfCities / 3);

        for(int i=t1;i<t2;i++)
            indiv3.city[i] = indiv1.city[i];

        for(int i=0;i<noOfCities;i++){
            if(i<t1 || i>=t2){
                for(int j=0;j<noOfCities;j++){
                    boolean exists=false;
                    for(int k=0;k<noOfCities;k++){
                        if(indiv3.city[k]==indiv2.city[j]) {
                            exists = true;
                            break;
                        }
                    }
                    if(!exists){
                        indiv3.city[i]=indiv2.city[j];
                        break;
                    }
                }
            }
        }
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
        if(Math.round(Math.random()*100)%10!=0)
            return fittest;
        else
            return tournament.getIndividual(0);
    }
}