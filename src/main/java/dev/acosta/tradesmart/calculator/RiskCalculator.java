package dev.acosta.tradesmart.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RiskCalculator {

    private BigDecimal totalCapital = new BigDecimal(Double.toString(0d))
            .setScale(2, RoundingMode.HALF_UP);
    private BigDecimal riskPerTrade = new BigDecimal(Double.toString(0.01));
    private BigDecimal capitalAtRisk;
    private BigDecimal maxCapitalAtRisk = new BigDecimal(Double.toString(0.3));

    private BigDecimal enterPrice;
    private BigDecimal stopLoss;
    private BigDecimal targetPrice;
    private BigDecimal lossPerPosition;

    public BigDecimal getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(double newCapital) {
        totalCapital = new BigDecimal(Double.toString(newCapital))
                .setScale(2, RoundingMode.HALF_UP);
        setCapitalAtRisk(riskPerTrade);
    }

    private void setCapitalAtRisk(BigDecimal riskPerTrade) {
        capitalAtRisk = totalCapital.multiply(riskPerTrade).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getRiskPerTrade() {
        return riskPerTrade;
    }

    public void setRiskPerTrade(double customRiskPerTrade) {
        riskPerTrade = new BigDecimal(Double.toString(customRiskPerTrade));
    }

    public void setEnterPrice(double price) {
        enterPrice = new BigDecimal(Double.toString(price)).setScale(2, RoundingMode.HALF_UP);
    }

    public void setStopLoss(double loss) {
        stopLoss = new BigDecimal(Double.toString(loss)).setScale(2, RoundingMode.HALF_UP);
        setLossPerPosition();
    }

    private void setLossPerPosition() {
        lossPerPosition = enterPrice.subtract(stopLoss);
    }

    public void setTargetPrice(double price) {
        targetPrice = new BigDecimal(Double.toString(price)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePossibleLoss() {
        if (isTotalCostAcceptable(getTotalCostByRisk()))
            return getLossByRisk();
        return getLossByCapital();
    }

    private Boolean isTotalCostAcceptable(BigDecimal totalCost) {
        return totalCost.compareTo(getMaxCapitalAtRisk()) < 0;
    }

    public BigDecimal getMaxCapitalAtRisk() {
        return maxCapitalAtRisk.multiply(totalCapital)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getTotalCostByRisk() {
        return enterPrice.multiply(getPositionSizeByRisk());
    }

    private BigDecimal getPositionSizeByRisk() {
        return capitalAtRisk.divideToIntegralValue(lossPerPosition);
    }

    private BigDecimal getLossByRisk() {
        return lossPerPosition.multiply(getPositionSizeByRisk());
    }

    private BigDecimal getLossByCapital() {
        return lossPerPosition.multiply(getPositionSizeByCapital());
    }

    private BigDecimal getPositionSizeByCapital() {
        return getMaxCapitalAtRisk().divideToIntegralValue(enterPrice);
    }

    public BigDecimal calculatePossibleProfit() {
        if (isTotalCostAcceptable(getTotalCostByRisk()))
            return getProfitByRisk();
        return getProfitByCapital();
    }

    private BigDecimal getProfitByRisk() {
        return getPriceDifference().multiply(getPositionSizeByRisk());
    }

    private BigDecimal getPriceDifference() {
        return targetPrice.subtract(enterPrice);
    }

    private BigDecimal getProfitByCapital() {
        return getPriceDifference().multiply(getPositionSizeByCapital());
    }

}
