package dev.acosta.tradesmart.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RiskCalculatorTest {

    private RiskCalculator calculator;

    private final BigDecimal DEFAULT_RISK_PER_TRADE = new BigDecimal(Double.toString(0.01));
    private final BigDecimal CUSTOMIZED_RISK_PER_TRADE = new BigDecimal(Double.toString(0.05));

    @BeforeEach
    public void setCalculator() {
        calculator = new RiskCalculator();
    }

    @Test
    void givenANewCalculator_ThenTotalCapitalIsSetToZero() {
        assertEquals(calculator.getTotalCapital(), new BigDecimal(Double.toString(0d)));
    }

    @Test
    void givenACalculator_ThenTotalCapitalCanBeSet() {
        BigDecimal newCapital = new BigDecimal(Double.toString(4300d));
        calculator.setTotalCapital(4300.00);
        assertEquals(calculator.getTotalCapital(), newCapital);
    }

    @Test
    void givenATotalCapital_ThenRiskPerTradeIsSetAtOnePercentByDefault() {
        assertEquals(calculator.getRiskPerTrade(), DEFAULT_RISK_PER_TRADE);
    }

    @Test
    void givenACalculator_ThenRiskPerTradeCanBeSet() {
        calculator.setRiskPerTrade(0.05);
        assertEquals(calculator.getRiskPerTrade(), CUSTOMIZED_RISK_PER_TRADE);
    }
}
