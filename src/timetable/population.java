package timetable;

import org.apache.commons.lang.ArrayUtils;

public class population {
    individual[] individuals;

    public population(int pop_size, boolean intialize) {
        individuals = new individual[pop_size];
        if (intialize) {
            for (int i = 0; i < pop_size; i++) {
                individual newIndividual = new individual();
                newIndividual.generateindividual();
                saveindividual(i, newIndividual);
            }
        }
    }

    public individual[] removeIndividual(int index) {
        individuals = (individual[]) ArrayUtils.remove(individuals, index);
        return individuals;
    }

    public individual getIndividual(int index) {
        return individuals[index];
    }

    public individual getFittest() {
        individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() >= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    public int getFittestpos() {
        individual fittest = individuals[0];
        int s = 0;
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() >= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
                s = i;
            }
        }
        return s;
    }


    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveindividual(int index, individual indiv) {
        individuals[index] = indiv;
    }
}

