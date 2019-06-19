package com.sree.model;

public class StockPrice {

    private String stock;
    private double strike;
    private double interestRate;
    private double impliedVolatility;
    private double time;

    public StockPrice() {
    }

    public StockPrice(String stock) {
        this.stock = stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public double getStrike() {
        return strike;
    }

    public void setStrike(double strike) {
        this.strike = strike;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getImpliedVolatility() {
        return impliedVolatility;
    }

    public void setImpliedVolatility(double impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}