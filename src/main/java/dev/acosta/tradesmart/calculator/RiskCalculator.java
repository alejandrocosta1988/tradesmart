package dev.acosta.tradesmart.calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class RiskCalculator {

    private BigDecimal totalCapital = new BigDecimal(Double.toString(0d))
            .setScale(2, RoundingMode.HALF_UP);
    private BigDecimal riskPerTrade = new BigDecimal(Double.toString(0.01));
    private BigDecimal maxCapitalAtRisk = new BigDecimal(Double.toString(0.3));

    private BigDecimal enterPrice;
    private BigDecimal stopLoss;

    public BigDecimal getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(double newCapital) {
        totalCapital = new BigDecimal(Double.toString(newCapital))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getRiskPerTrade() {
        return riskPerTrade;
    }

    public void setRiskPerTrade(double customRiskPerTrade) {
        riskPerTrade = new BigDecimal(Double.toString(customRiskPerTrade));
    }

    public BigDecimal getMaxCapitalAtRisk() {
        return maxCapitalAtRisk.multiply(totalCapital)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void setEnterPrice(double price) {
        enterPrice = new BigDecimal(Double.toString(price)).setScale(2, RoundingMode.HALF_UP);
    }

    public void setStopLoss(double loss) {
        stopLoss = new BigDecimal(Double.toString(loss)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePossibleLoss() {
        BigDecimal priceDifference = enterPrice.subtract(stopLoss).setScale(2, RoundingMode.HALF_UP);
        BigDecimal capitalAtRisk = getTotalCapital().multiply(riskPerTrade).setScale(2, RoundingMode.HALF_UP);
        BigDecimal numberOfPositions = capitalAtRisk.divideToIntegralValue(priceDifference);
        BigDecimal totalCost = priceDifference.multiply(numberOfPositions).setScale(2, RoundingMode.HALF_UP);
        if (getMaxCapitalAtRisk().compareTo(totalCost) > 0)
            return priceDifference.multiply(numberOfPositions);
        numberOfPositions = getMaxCapitalAtRisk().divideToIntegralValue(enterPrice);
        return priceDifference.multiply(numberOfPositions);
    }
}
