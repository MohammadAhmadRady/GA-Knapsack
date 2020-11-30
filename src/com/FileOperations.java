package com;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FileOperations
{
    //reading from file
    private static String input = "";
    private static String getAllFile() throws Exception
    {
        File file = new File("input_example.txt");
        BufferedReader READ = new BufferedReader(new FileReader(file));
        int q=READ.read();
        String data = "";
        while(q != -1)
        {
            data +=( char)q;
            q=READ.read();
        }
        return data;
    }
    public static ArrayList<TestCase> fillAllTestCases() throws Exception
    {
        input = getAllFile();
        ArrayList<TestCase> testCases = new ArrayList<>();
        String [] allSeparated = input.split("/");

        for (int i=0;i<allSeparated.length;i++)
        {
            String [] divided = allSeparated[i].split(System.lineSeparator());
            int maxW=0;
            int size;
            ArrayList<Pair<Float , Float>> items = new ArrayList<>();
            for (int j=0;j<divided.length ;j++)
            {
                if(j==0)
                    maxW = Integer.valueOf(divided[0]);
                else
                {
                    String [] row = divided[j].split(" ");
                    float weight = Float.valueOf(row[0]);
                    float value = Float.valueOf(row[1]);
                    Pair<Float,Float> p = new Pair<>(weight , value);
                    items.add(p);
                }
            }
            size = items.size();
            TestCase testCase = new TestCase(size,maxW,items);
            testCases.add(testCase);
        }
        return testCases;
    }
}
