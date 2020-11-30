package com;

import java.util.ArrayList;

public class Chromosome {

    private int cells; //num items per tc.. 0 or 1
    private int chromoWeight;
    private float fitness;

    private ArrayList<String> contents ;

    /*constructor*/
    public Chromosome(int cells)
    {
        this.cells = cells;
        contents = new ArrayList<>(cells);
        chromoWeight = 0;
        fitness = 0;
    }

    /*given a probability , set the contents of the chromosome.. 1 or 0*/
    public void initialize(float probability)
    {
        for(int i = 0 ; i < cells ; i++)
        {
            float odds =(float) (Math.random());
            //check if its greater than or equal given probability
            if (odds >= probability)
                contents.add("1");
            else
                contents.add("0");
        }

    }


    public float evaluate_fitness(TestCase testCase)
    {
        for (int i = 0 ; i < cells ; i++){
            if (contents.get(i).equals("1"))
                fitness += testCase.getValuePerWeight(i);
        }
        return fitness;
    }

    public float chromosomeValue(TestCase testCase)
    {
        float value = 0 ;

        for (int i = 0 ; i < cells ; i++){
            if (contents.get(i).equals("1"))
                value += testCase.getPairVlaue(i);
        }
        return value;
    }

    public int check_weight(TestCase testCase)
    {
        int chrome = 0;
        for (int i = 0 ; i < cells ; i++){
            if (contents.get(i).equals("1"))
                chrome += testCase.getItems().get(i).getKey();
        }
        return chrome;
    }

    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }

    public ArrayList<String> getContents() {
        return contents;
    }

    public int getCells() {
        return cells;
    }
}