package com;
import java.util.ArrayList;

public class Knapsack {

    /*GLOBAL*/
    private final static int population_size = 5000;
    private final static int test_cases = 20;
    private static final float Pc = (float) 0.4;

    public static void main(String[] args) throws Exception
    {
        FileOperations fileOperation = new FileOperations();
        ArrayList<TestCase> allTestCases = fileOperation.fillAllTestCases();

        /*for each iteration , read a test case from the file*/
        for (int i = 0 ; i < test_cases ; i++ )
        {
            /*VARIABLES*/
            int iterations = 5000; //max num of iterations

            /*COLLECTIONS*/
            ArrayList<Chromosome> individuals = new ArrayList<>(population_size); //holding all generations.

            /*initialise the population*/
            TestCase testCase = allTestCases.get(i);

            initializePopulation(individuals , testCase.getSize());

            while (iterations != 0)
            {
                if(crossover(individuals , selection(individuals , testCase) , 0 ))
                {
                    mutation(individuals , (float) 0.1 );
                    replacement(individuals , testCase );
                    iterations --;
                    continue;
                }
                mutation(individuals , (float) 0.1 );

                iterations --;
            }
            System.out.println("case"+ "("+(i+1)+"): "  + (int)bestSolution(individuals , testCase));
        }
    }

    private static void initializePopulation(ArrayList<Chromosome> individuals , int cells)
    {
        for (int i = 0 ; i < population_size ; i++){
            Chromosome member = new Chromosome(cells);
            member.initialize((float) 0.50);
            individuals.add(member);
        }
    }

    //returns indices of best individuals.
    private static ArrayList<Integer> selection(ArrayList<Chromosome> individuals , TestCase testCase)
    {
        ArrayList<Integer> returned = new ArrayList<>(2);
        ArrayList<Float> cumulativeFitness = new ArrayList<>(testCase.getSize()); //build the roulette wheel

        int index1;
        int index2;

        //evaluate fitness for each chromosome then add it to cumulative
        for (int i = 0 ; i < population_size ; i++)
        {
            Chromosome chromosome = individuals.get(i);
            float cFitness = chromosome.evaluate_fitness(testCase);

            if (i == 0)
                cumulativeFitness.add(cFitness);
            else
                cumulativeFitness.add(cFitness +cumulativeFitness.get(i-1));
        }

        do {
            float one = (float) (Math.random());
            float two = (float) (Math.random());

             index1 = 11111;
             index2 = 11111;

            one *= cumulativeFitness.get(population_size - 1);
            two *= cumulativeFitness.get(population_size - 1);

            for (int i = 0; i < population_size; i++) {

                if ((one <= cumulativeFitness.get(i)) && (index1 == 11111))
                    index1 = i;

                if ((two <= cumulativeFitness.get(i)) && (index2 == 11111))
                    index2 = i;
            }
        }while (index1 == index2);

        returned.add(index1);
        returned.add(index2);

        return returned;
    }

    /*takes indices of parents to reproduce based on selection */
    private static boolean crossover(ArrayList<Chromosome> individuals , ArrayList<Integer> parentsIndices , float probability){

        Chromosome parent1 = individuals.get(parentsIndices.get(0));
        Chromosome parent2 = individuals.get(parentsIndices.get(1));

        Chromosome offspring1 = new Chromosome(parent1.getCells());
        offspring1.setContents(parent1.getContents());

        Chromosome offspring2 = new Chromosome(parent2.getCells());
        offspring2.setContents(parent2.getContents());

        int limit = 0;

        // do a single point crossOver , location changes depending
        // on number of  cells in chromosome.
        probability = (float) (Math.random());
        if(probability < Pc)
        {
            return false;
        }

        float point = (float) (Math.random());
        point *= parent1.getContents().size();
        limit = (int) point;

        for (int i = parent1.getCells() - 1 ; i > limit  ; i--)
        {
            String temp = offspring1.getContents().get(i);
            offspring1.getContents().set(i , offspring2.getContents().get(i));
            offspring2.getContents().set(i , temp);
        }

        individuals.add(offspring1);
        individuals.add(offspring2);

        return true;
    }

    private static void mutation(ArrayList<Chromosome> individuals , float prob) {

        int cells = individuals.get(0).getCells();

        for (int i = 0 ; i < 2 ; i++)
        {
            // last two chromosomes added by crossOver
            Chromosome chromosome = individuals.get(individuals.size() - 2 + i);

            for (int j = 0 ; j < cells ; j++)
            {
                float rand =(float) (Math.random());
                if (rand < prob)
                {
                    if (chromosome.getContents().get(j).equals("1"))
                        chromosome.getContents().set(j , "0");

                    else
                        chromosome.getContents().set(j , "1");
                }
            }
            individuals.remove(individuals.size() - 2 + i);
            individuals.add(individuals.size() - 1 + i , chromosome);
        }
    }

    private static void replacement(ArrayList<Chromosome> individuals , TestCase testCase) {

            for (int i = 0; i < individuals.size(); i++) {

                Chromosome chromosome = individuals.get(i);
                int chromosomeWeight = chromosome.check_weight(testCase);

                //remove a chromosome exceeding the max weight.
                if (chromosomeWeight > testCase.getMaxWeight())
                    individuals.remove(chromosome);

                //break when original pop size reached
                if (individuals.size() == population_size)
                    break;
            }

            //remove chromosome with least fitness.
            while (individuals.size() != population_size)
            {
                individuals.remove(getLeastFit(individuals , testCase));

                //break when original pop size reached
                if (individuals.size() == population_size)
                    break;

            }
    }

    private static int getLeastFit(ArrayList<Chromosome> individuals , TestCase testCase)
    {
        float minFitness = 1000000;
        int index = 0;

        for (int i = 0 ; i < individuals.size() ; i++)
        {
            float currentFitness = individuals.get(i).evaluate_fitness(testCase);

            if (minFitness > currentFitness) {

                minFitness = currentFitness;
                index = i;
            }
        }
        return index;
    }

    private static float bestSolution(ArrayList<Chromosome> individuals , TestCase testCase)
    {
        float maxValue = 0;

        for (int i = 0 ; i < individuals.size() ; i++) {
            float currentValue = individuals.get(i).chromosomeValue(testCase);

            if (maxValue < currentValue)
            {
                maxValue = currentValue;
            }
        }
        return maxValue;
    }
}