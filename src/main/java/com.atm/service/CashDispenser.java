package com.atm.service;

import com.atm.strategy.DispenseStrategy;
import java.util.Map;

public class CashDispenser {
    private final DispenseStrategy strategy;

    public CashDispenser(DispenseStrategy strategy) {
        this.strategy = strategy;
    }

    public void dispense(double amount) {
        Map<Integer, Integer> notes = strategy.dispense(amount);
        System.out.println("Dispensing:");
        notes.forEach((denomination, count) ->
                System.out.println(count + " x " + denomination));
    }
}
