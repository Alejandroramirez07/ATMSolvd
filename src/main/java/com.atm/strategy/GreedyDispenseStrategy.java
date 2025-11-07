package com.atm.strategy;

import java.util.LinkedHashMap;
import java.util.Map;

public class GreedyDispenseStrategy implements DispenseStrategy {
    private final int[] denominations = {1000, 500, 200, 100, 50, 20, 10, 5, 1};

    @Override
    public Map<Integer, Integer> dispense(double amount) {
        Map<Integer, Integer> result = new LinkedHashMap<>();
        int remaining = (int) amount;

        for (int note : denominations) {
            int count= remaining / note;
            if (count > 0) {
                result.put(note, count);
                remaining -= count * note;
            }
        }

        return result;
    }
}
