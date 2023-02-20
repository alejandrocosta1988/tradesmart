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

    public void setTargetPrice(double price) {
        targetPrice = new BigDecimal(Double.toString(price)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePossibleLoss() {
        BigDecimal maxCapital = getMaxCapitalAtRisk();
        BigDecimal lossPerPosition = getPriceDifference(enterPrice, stopLoss);
        BigDecimal positionSizeByCapital = maxCapital.divideToIntegralValue(enterPrice);
        BigDecimal totalCostByRisk = enterPrice.multiply(getPositionSizeByRisk(lossPerPosition));
        if (isTotalCostAcceptable(totalCostByRisk)) {
            return lossPerPosition.multiply(getPositionSizeByRisk(lossPerPosition));
        }
        return lossPerPosition.multiply(positionSizeByCapital);
    }

    private BigDecimal getPriceDifference(BigDecimal buyPrice, BigDecimal sellPrice) {
        return buyPrice.subtract(sellPrice).setScale(2, RoundingMode.HALF_UP);
    }

    private Boolean isTotalCostAcceptable(BigDecimal totalCost) {
        return totalCost.compareTo(getMaxCapitalAtRisk()) < 0;
    }

    private BigDecimal getPositionSizeByRisk(BigDecimal lossPerPosition) {
        return capitalAtRisk.divideToIntegralValue(lossPerPosition);
    }

    public BigDecimal calculatePossibleProfit() {
        BigDecimal maxCapital = getMaxCapitalAtRisk();
        BigDecimal lossPerPosition = getPriceDifference(enterPrice, stopLoss);
        BigDecimal positionSizeByCapital = maxCapital.divideToIntegralValue(enterPrice);
        BigDecimal totalCostByRisk = enterPrice.multiply(getPositionSizeByRisk(lossPerPosition));
        if (isTotalCostAcceptable(totalCostByRisk)) {
            return targetPrice.subtract(enterPrice).multiply(getPositionSizeByRisk(lossPerPosition)).setScale(2, RoundingMode.HALF_UP);
        }
        return targetPrice.subtract(enterPrice).multiply(positionSizeByCapital).setScale(2, RoundingMode.HALF_UP);
    }



}
