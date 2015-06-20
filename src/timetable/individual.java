package timetable;

public class individual {
    private static int noOfClasses = 4;
    private static int noOfBatches = 6;
    private static int noOfSubjects =6;
    public String[][] schedule = new String[6][6];
    public int fitness = 0;
    public int[][]fit=new int[600][2];

    public void generateindividual() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                schedule[i][j] = "";
                for(int k=0;k<noOfClasses;k++)
                    schedule[i][j] += (Integer.toString(i) + Integer.toString(j)+ Integer.toString(k));
                //System.out.println("new "+schedule[i][j]);
            }
        }
        for (int i = 0; i < 100; i++) {
            int x1 = (int) (Math.round(Math.random() * 100) % 5), y1 = (int) (Math.round(Math.random() * 100) % 5);
            int x2 = (int) (Math.round(Math.random() * 100) % 5), y2 = (int) (Math.round(Math.random() * 100) % 5);
            String temp = schedule[x1][y1]+schedule[x2][y2];
            int t=noOfClasses*3;
            schedule[x1][y1]=temp.substring(12,12+t);
            schedule[x2][y2]=temp.substring(0,12)+temp.substring(12+t,t*2);
        }
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

    public int[][] getDetailedFitness() {
        if (fitness == 0) {
            fit = fitnessCalc.getDetailedFitness(this);
        }
        return fit;
    }

    public int[] bestSequence() {

            int []fitt = fitnessCalc.bestSequence(this);

        return fitt;
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
