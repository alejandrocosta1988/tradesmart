package dev.acosta.tradesmart.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RiskCalculator {

    private BigDecimal totalCapital = new BigDecimal(Double.toString(0d))
            .setScale(2, RoundingMode.HALF_UP);
    private BigDecimal riskPerTrade = new BigDecimal(Double.toString(0.01));
    private BigDecimal maxCapitalAtRisk = new BigDecimal(Double.toString(0.3));

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
}
