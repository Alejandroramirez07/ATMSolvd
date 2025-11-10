package com.atm.strategy;

import java.util.Map;

public interface DispenseStrategy {
    Map<Integer, Integer> dispense(int amount);
}
