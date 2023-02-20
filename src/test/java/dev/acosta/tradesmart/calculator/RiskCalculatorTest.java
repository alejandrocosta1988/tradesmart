package dev.acosta.tradesmart.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Calculator is created with no total capital")
    void givenANewCalculator_ThenTotalCapitalIsSetToZero() {
        assertEquals(new BigDecimal(Double.toString(0d)).setScale(2, RoundingMode.HALF_UP), calculator.getTotalCapital());
    }

    @Test
    @DisplayName("Sets total capital")
    void givenACalculator_ThenTotalCapitalCanBeSet() {
        BigDecimal newCapital = new BigDecimal(Double.toString(4300d))
                .setScale(2, RoundingMode.HALF_UP);
        calculator.setTotalCapital(4300.00);
        assertEquals(newCapital, calculator.getTotalCapital());
    }

    @Test
    @DisplayName("Risk per trade is set at 1% by default")
    void givenANewCalculator_ThenRiskPerTradeIsSetAtOnePercentByDefault() {
        assertEquals(DEFAULT_RISK_PER_TRADE, calculator.getRiskPerTrade());
    }

    @Test
    @DisplayName("Sets risk per trade")
    void givenACalculator_ThenRiskPerTradeCanBeSet() {
        calculator.setRiskPerTrade(0.05);
        assertEquals(CUSTOMIZED_RISK_PER_TRADE, calculator.getRiskPerTrade());
    }

    @Test
    @DisplayName("Max capital at risk is set at 30% by default")
    void givenANewCalculator_ThenMaxCapitalAtRiskIsSetAt30PercentOfTheCapitalByDefault() {
        calculator.setTotalCapital(1000d);
        BigDecimal capitalAtRisk = new BigDecimal(Double.toString(300d)).setScale(2, RoundingMode.HALF_UP);
        assertEquals(capitalAtRisk, calculator.getMaxCapitalAtRisk());
    }

    @Test
    @DisplayName("Calculates possible loss by maximum capital at risk")
    void givenAnEnterPriceAndStopLossAndTotalCapital_IfTotalCostIsHigherThanMaxCapitalAtRisk_ThenPossibleLossIsCalculatedByMaxCapitalAtRisk() {
        calculator.setEnterPrice(20d);
        calculator.setStopLoss(19.50);
        calculator.setTotalCapital(1000d);
        BigDecimal possibleLoss = new BigDecimal(Double.toString(7.50)).setScale(2, RoundingMode.HALF_UP);
        assertEquals(possibleLoss, calculator.calculatePossibleLoss());
    }

    @Test
    @DisplayName("Calculates possible loss by risk per trade")
    void givenAnEnterPriceAndStopLossAndTotalCapital_IfMaxCapitalAtRiskIsHigherThanTotalCost_ThenPossibleLossIsCalculatedByRiskPerTrade() {
        calculator.setEnterPrice(32.01);
        calculator.setStopLoss(30.45);
        calculator.setTotalCapital(1000d);
        BigDecimal possibleLoss = new BigDecimal(Double.toString(9.36)).setScale(2, RoundingMode.HALF_UP);
        assertEquals(possibleLoss, calculator.calculatePossibleLoss());
    }

    @Test
    @DisplayName("Calculates possible profit by max capital at risk (30% of total capital)")
    void givenAnEnterPriceAndStopLossAndTargetPriceAndTotalCapital_IfTotalCostIsHigherThanMaxCapitalAtRisk_ThenPossibleProfitIsCalculatedByMaxCapitalAtRisk() {
        calculator.setEnterPrice(20d);
        calculator.setStopLoss(19.50);
        calculator.setTotalCapital(1000d);
        calculator.setTargetPrice(23.50);
        BigDecimal possibleProfit = new BigDecimal(Double.toString(52.50)).setScale(2, RoundingMode.HALF_UP);
        assertEquals(possibleProfit, calculator.calculatePossibleProfit(), "Calculated profit should be 52.50");
    }

    @Test
    @DisplayName("Calculates possible profit by risk per trade (1% of total capital)")
    void givenAnEnterPriceAndStopLossAndTargetPriceAndTotalCapital_IfMaxCapitalAtRiskIsHigherThanTotalCost_ThenPossibleProfitIsCalculatedByRiskPerTrade() {
        calculator.setEnterPrice(32.01);
        calculator.setStopLoss(30.45);
        calculator.setTargetPrice(34.19);
        calculator.setTotalCapital(1000d);
        BigDecimal possibleProfit = new BigDecimal(Double.toString(13.08)).setScale(2, RoundingMode.HALF_UP);
        assertEquals(possibleProfit, calculator.calculatePossibleProfit(), "Possible profit should be 13.08");
    }


}
