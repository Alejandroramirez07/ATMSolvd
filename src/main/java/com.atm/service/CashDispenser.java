package com.atm.service;

import com.atm.strategy.DispenseStrategy;
import java.util.Map;

public class CashDispenser {

    private final DispenseStrategy strategy;

    // Inject the strategy (SOLID: Dependency Inversion)
    public CashDispenser(DispenseStrategy strategy) {
        this.strategy = strategy;
    }

    public Map<Integer, Integer> dispense(int amount) {
        return strategy.dispense(amount);
    }
}
