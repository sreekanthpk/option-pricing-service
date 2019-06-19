package com.sree.model;

public class Tick {

    private String stock;
    private double price;
    private double strike;
    private double putPrice;
    private double callPrice;


    public Tick() {
    }

    public Tick(String stock, double price, double strike, double putPrice, double callPrice) {
        this.stock = stock;
        this.price = price;
        this.strike = strike;
        this.putPrice = putPrice;
        this.callPrice = callPrice;
    }

    public String getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public double getStrike() {
        return strike;
    }

    public double getPutPrice() {
        return putPrice;
    }

    public double getCallPrice() {
        return callPrice;
    }
}