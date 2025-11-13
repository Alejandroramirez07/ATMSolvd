package com.atm.strategy;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class GreedyDispenserStrategyTest {

    @Test
    void testGreedyLogic() {
        GreedyDispenseStrategy strategy = new GreedyDispenseStrategy();
        Map<Integer, Integer> result = strategy.dispense(1580);

        assertEquals(1, result.get(1000));
        assertEquals(1, result.get(500));
        assertEquals(1, result.get(50));
        assertEquals(3, result.get(10));

        // Make sure total is correct
        int total = result.entrySet().stream()
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();
        assertEquals(1580, total);
    }
}
