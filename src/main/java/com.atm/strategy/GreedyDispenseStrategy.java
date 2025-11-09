package com.atm.strategy;

import java.util.LinkedHashMap;
import java.util.Map;

public class GreedyDispenseStrategy implements DispenseStrategy {

    private static final int[] DENOMINATIONS = {1000, 500, 100, 50, 20, 10};

    @Override
    public Map<Integer, Integer> dispense(int amount) {
        Map<Integer, Integer> result = new LinkedHashMap<>();

        for (int denom : DENOMINATIONS) {
            if (amount >= denom) {
                int count = amount / denom;
                amount %= denom;
                result.put(denom, count);
            }
        }

        if (amount > 0) {
            throw new IllegalArgumentException("Amount cannot be dispensed with available denominations.");
        }

        return result;
    }
}
