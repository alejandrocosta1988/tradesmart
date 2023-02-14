package dev.acosta.tradesmart.calculator;

import java.math.BigDecimal;

public class RiskCalculator {

    private BigDecimal riskPerTrade = new BigDecimal(Double.toString(0.01));

    public BigDecimal getRiskPerTrade() {
        return riskPerTrade;
    }

    public void setRiskPerTrade(double customRiskPerTrade) {
        riskPerTrade = new BigDecimal(Double.toString(customRiskPerTrade));
    }
}
