package dev.acosta.tradesmart.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RiskCalculatorTest {

    private RiskCalculator calculator;

    @BeforeEach
    public void setCalculator() {
        calculator = new RiskCalculator();
    }

    @Test
    void givenATotalCapital_ThenRiskPerTradeIsSetAtOnePercentByDefault() {
        BigDecimal defaultRiskPerTrade = new BigDecimal(Double.toString(0.01));
        assertEquals(calculator.getRiskPerTrade(), defaultRiskPerTrade);
    }

    @Test
    void givenACalculator_ThenRiskPerTradeCanBeSet() {
        calculator.setRiskPerTrade(0.05);
        BigDecimal customizedRiskPerTrade = new BigDecimal(Double.toString(0.05));
        assertEquals(calculator.getRiskPerTrade(), customizedRiskPerTrade);
    }
}
