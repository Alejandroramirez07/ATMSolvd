package com.atm.service;

import com.atm.strategy.GreedyDispenseStrategy;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CashDispenserTest {

    @Test
    void testGreedyDispense() {
        CashDispenser dispenser = new CashDispenser(new GreedyDispenseStrategy());
        Map<Integer, Integer> notes = dispenser.dispense(1580);

        int total = notes.entrySet().stream()
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();

        assertEquals(1580, total);
    }

    @Test
    void testInvalidAmountThrowsException() {
        CashDispenser dispenser = new CashDispenser(new GreedyDispenseStrategy());
        assertThrows(IllegalArgumentException.class, () -> dispenser.dispense(7));
    }
}
