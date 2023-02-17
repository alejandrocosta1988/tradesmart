package dev.acosta.tradesmart.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RiskCalculatorTest {

    private RiskCalculator calculator;

    private final BigDecimal DEFAULT_RISK_PER_TRADE = new BigDecimal(Double.toString(0.01));
    private final BigDecimal DEFAULT_MAX_CAPITAL_AT_RISK = new BigDecimal(Double.toString(0.3));
    private final BigDecimal CUSTOMIZED_RISK_PER_TRADE = new BigDecimal(Double.toString(0.05));

    @BeforeEach
    public void setCalculator() {
        calculator = new RiskCalculator();
    }

    @Test
    void givenANewCalculator_ThenTotalCapitalIsSetToZero() {
        assertEquals(new BigDecimal(Double.toString(0d)).setScale(2, RoundingMode.HALF_UP), calculator.getTotalCapital());
    }

    @Test
    void givenACalculator_ThenTotalCapitalCanBeSet() {
        BigDecimal newCapital = new BigDecimal(Double.toString(4300d))
                .setScale(2, RoundingMode.HALF_UP);
        calculator.setTotalCapital(4300.00);
        assertEquals(newCapital, calculator.getTotalCapital());
    }

    @Test
    void givenANewCalculator_ThenRiskPerTradeIsSetAtOnePercentByDefault() {
        assertEquals(DEFAULT_RISK_PER_TRADE, calculator.getRiskPerTrade());
    }

    @Test
    void givenACalculator_ThenRiskPerTradeCanBeSet() {
        calculator.setRiskPerTrade(0.05);
        assertEquals(CUSTOMIZED_RISK_PER_TRADE, calculator.getRiskPerTrade());
    }

    @Test
    void givenANewCalculator_ThenMaxCapitalAtRiskIsSetAt30PercentOfTheCapitalByDefault() {
        calculator.setTotalCapital(1000d);
        BigDecimal capitalAtRisk = new BigDecimal(Double.toString(300d)).setScale(2, RoundingMode.HALF_UP);
        assertEquals(capitalAtRisk, calculator.getMaxCapitalAtRisk());
    }

    @Test
    void givenAnEnterPriceAndStopLossAndTotalCapital_IfTotalCostIsHigherThanMaxCapitalAtRisk_ThenPossibleLossIsCalculatedByMaxCapitalAtRisk() {
        calculator.setEnterPrice(20d);
        calculator.setStopLoss(19.50);
        calculator.setTotalCapital(1000d);
        BigDecimal possibleLoss = new BigDecimal(Double.toString(7.50)).setScale(2, RoundingMode.HALF_UP);
        assertEquals(possibleLoss, calculator.calculatePossibleLoss());
    }

    @Test
    void givenAnEnterPriceAndStopLossAndTotalCapital_IfMaxCapitalAtRiskIsHigherThanTotalCost_ThenPossibleLossIsCalculatedByRiskPerTrade() {
        calculator.setEnterPrice(32.01);
        calculator.setStopLoss(30.45);
        calculator.setTotalCapital(1000d);
        BigDecimal possibleLoss = new BigDecimal(Double.toString(9.36)).setScale(2, RoundingMode.HALF_UP);
        assertEquals(possibleLoss, calculator.calculatePossibleLoss());
    }


}
