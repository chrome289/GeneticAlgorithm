package salesman;

/**
 * Created by Siddharth on 17-06-415.
 */
public class individual {
    public static int route_length = ga.noOfCities;
    public int[] city = new int[ga.noOfCities];
    public int fitness = 0;

    public static void setDefaultGeneLength(int length) {
        route_length = length;
    }

    //generate individual
    public void generateindividual() {
        for (int i = 0; i < ga.noOfCities; i++)
            city[i] = i;
        //randomize its genes
        for (int i = 0; i < 500; i++) {
            int t1 = (int) (Math.random() * (ga.noOfCities - 1));
            int t2 = (int) (Math.random() * (ga.noOfCities - 1));
            int t3 = this.city[t1];
            this.city[t1] = city[t2];
            this.city[t2] = t3;
        }
    }

    public int getGene(int index) {
        return city[index];
    }

    public void setGene(int index, int value) {
        city[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return city.length;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = fitnessCalc.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}
