package timetable;

/**
 * Created by Siddharth on 18-06-2015.
 */
public class individual {
    private static int noOfCities = 100;
    public static int route_length = noOfCities;
    public String[][] schedule = new String[6][6];
    public int fitness = 0;

    public void generateindividual() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                schedule[i][j] = "";
                schedule[i][j] += (Integer.toString(i) + Integer.toString(j) + Integer.toString(i) + Integer.toString(j)+ Integer.toString(i) + Integer.toString(j));
                //System.out.println(schedule[i][j].substring(2,3));
            }
        }
        for (int i = 0; i < 20; i++) {
            int x1 = (int) (Math.round(Math.random() * 100) % 6), y1 = (int) (Math.round(Math.random() * 100) % 6);
            int x2 = (int) (Math.round(Math.random() * 100) % 6), y2 = (int) (Math.round(Math.random() * 100) % 6);
            String temp = schedule[x1][y1]+schedule[x2][y2];
            schedule[x1][y1]=temp.substring(2,8);
            schedule[x2][y2]=temp.substring(0,2)+temp.substring(8,12);
        }
    }

    public static void setDefaultGeneLength(int length) {
        route_length = length;
    }

    public String getGene(int index1, int index2) {
        return schedule[index1][index2];
    }

    public void setGene(int index1, int index2, String value) {
        schedule[index1][index2] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return schedule.length;
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
            for (int j = 0; j < size(); j++) {
                geneString += getGene(i, j);
            }
        }
        return geneString;
    }
}
