package timetable;

/**
 * Created by Siddharth on 18-06-2015.
 */
public class algorithm {

    public static boolean elitism = true;
    public static int tournamentsize=5;
    //private static int noOfCities=100;
    //public static float mutationRate=0.010f;

    public static population evolve(population pop) {
        population newpop = new population(pop.size(), false);

        //keep fittest
        if (elitism)
            newpop.saveindividual(0, pop.getFittest());

        for (int i = 1; i < pop.size(); i++) {
            individual indiv1 = selection(pop);
            individual indiv2 = selection(pop);
            individual newindiv = crossover(indiv1, indiv2);
            //newindiv=mutate(newindiv);
            newpop.saveindividual(i, newindiv);
        }
        return newpop;
    }

   /* private static individual mutate(individual newindiv) {
        for(int i=0;i<5;i++){
            if(Math.random()<mutationRate){
                int t1= (int) Math.round(Math.random()*(noOfCities-1)),t2= (int) Math.round(Math.random()*(noOfCities-1));
                int t3=newindiv.city[t1];
                newindiv.city[t1]=newindiv.city[t2];
                newindiv.city[t2]=t3;
            }
        }
        return newindiv;
    }*/

    private static individual crossover(individual indiv1, individual indiv2) {
        individual indiv3 = new individual();
        String temp1="",temp2="";
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                temp1 =temp1+indiv1.schedule[i][j];temp2 =temp2+indiv2.schedule[i][j];
            }
        }

        int t1= (int) Math.round(Math.random()*(108/2));
        int t2=t1+Math.round(108 / 3);

        String temp3=temp1.substring((t1*2),(t2*2)+2);

        for(int i=t1;i>=0;i--){
            for(int j=0;j<108;j++){
                int y=0;
                for(int k=0;k<temp3.length()/3;k++){
                    if(temp3.substring(k*2,(k*2)+2).equals(temp2.substring((j*2),(j*2)+2)))
                        y++;
                }
                if(y<3)
                    temp3=temp2.substring((j*2),(j*2)+2)+temp3;
            }
        }
        for(int i=t2;i<108;i++){
            for(int j=0;j<108;j++){
                int y=0;
                for(int k=0;k<temp3.length()/3;k++){
                    if(temp3.substring(k*2,(k*2)+1).equals(temp2.substring((j*2),(j*2)+1)))
                        y++;
                }
                if(y<3)
                    temp3=temp3+temp2.substring((j*2),(j*2)+1);
            }
        }
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                indiv3.schedule[i][j]=temp3.substring((i*36)+(j*6),(i*36)+(j*6)+6);
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