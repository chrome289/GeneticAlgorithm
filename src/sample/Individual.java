package sample;

/**
 * Created by Siddharth on 16-06-2015.
 */
public class Individual {

    static int defaultGeneLength = 64;
    private char[] genes = new char[defaultGeneLength];
    // Cache
    private int fitness = 0;

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }

    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            int y = (int) Math.ceil(Math.random() * 100) % 4;
            if (y == 0)
                genes[i] = 'A';
            else if (y == 1)
                genes[i] = 'C';
            else if (y == 2)
                genes[i] = 'G';
            else
                genes[i] = 'T';
        }
    }

    public char getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, char value) {
        genes[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
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