package dev.acosta.tradesmart.calculator;

import java.math.BigDecimal;

public class RiskCalculator {

    private BigDecimal totalCapital = new BigDecimal(Double.toString(0d));
    private BigDecimal riskPerTrade = new BigDecimal(Double.toString(0.01));

    public BigDecimal getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(double newCapital) {
        totalCapital = new BigDecimal(Double.toString(newCapital));
    }

    public BigDecimal getRiskPerTrade() {
        return riskPerTrade;
    }

    public void setRiskPerTrade(double customRiskPerTrade) {
        riskPerTrade = new BigDecimal(Double.toString(customRiskPerTrade));
    }


}
