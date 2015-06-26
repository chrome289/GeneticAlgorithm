package timetable;

import java.util.HashSet;

public class fitnessCalc {

    private static int noOfClasses = 4;
    private static int noOfBatches = 6;
    private static int noOfSubjects = 6;

    public static int getFitness(individual individual) {
        int fitness = 0;
        HashSet<Character> s = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < noOfClasses * 3; k = k + 3) {
                    if (!s.add(individual.schedule[i][j].charAt(k)))
                        fitness++;
                }
                s.clear();
            }
        }
        return fitness;
    }

    public static int[][] getDetailedFitness(individual individual) {
        int[][] fitness = new int[600][2];
        int x = 0;
        HashSet<Character> s = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < noOfClasses * 3; k = k + 3) {
                    if (!s.add(individual.schedule[i][j].charAt(k))) {
                        fitness[x][0] = i;
                        fitness[x][1] = j;
                        x++;
                        break;
                    }
                }
                s.clear();
            }
        }
        return fitness;
    }

    public static int[] bestSequence(individual individual) {
        int fitness = 0, cur = 0, prev = 0, t1 = 0, t2 = 0, t3 = 0;
        int t[] = new int[2];
        HashSet<Character> s = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < noOfClasses * 3; k = k + 3) {
                    t3++;
                    if (!s.add(individual.schedule[i][j].charAt(k))) {
                        if (prev < cur) {
                            prev = cur;
                            t2 = t3;
                            t1 = t3 - prev;
                        }
                        cur = 0;
                    } else
                        cur++;
                }

                s.clear();
            }
        }
        t[0] = t1;
        t[1] = t2;
        return t;
    }

    // Get optimum fitness
    static int getMaxFitness() {
        int maxFitness = 0;
        return maxFitness;
    }
}