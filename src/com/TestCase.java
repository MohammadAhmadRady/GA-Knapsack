package com;
import javafx.util.Pair;
import java.util.ArrayList;

public class TestCase {
    private int size;
    private int maxWeight;
    private ArrayList<Pair<Float, Float>> items;

    public TestCase(int s, int max, ArrayList<Pair<Float, Float>> itemss) {
        size = s;
        maxWeight = max;
        items = new ArrayList<>(size);
        items = itemss;
    }

    public int getSize() {
        return size;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public float getValuePerWeight(int index) {
        float key = items.get(index).getKey();
        float value = items.get(index).getValue();
        return value / key;
    }

    public float getPairVlaue(int index)
    {
        return items.get(index).getValue();
    }

    public ArrayList<Pair<Float, Float>> getItems() {
        return items;
    }
}