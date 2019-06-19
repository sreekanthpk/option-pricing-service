package com.sree.montecarlo;

import static com.sree.montecarlo.Gaussian.gaussian;

public class MonteCarloSimulation {
    // estimate by Monte Carlo simulation
    public static double call(double s, double x, double r, double sigma, double t) {
        int n = 10000;
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            double eps = gaussian();
            double price = s * Math.exp(r*t - 0.5*sigma*sigma*t + sigma*eps*Math.sqrt(t));
            double value = Math.max(price - x, 0);
            sum += value;
        }
        double mean = sum / n;

        return Math.exp(-r*t) * mean;
    }

    // estimate by Monte Carlo simulation
    public static double call2(double s, double x, double r, double sigma, double t) {
        int n = 10000;
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            double price = s;
            double dt = t/10000.0;
            for (double time = 0; time <= t; time += dt) {
                price += r*price*dt +sigma*price*Math.sqrt(dt)*gaussian();
            }
            double value = Math.max(price - x, 0);
            sum += value;
        }
        double mean = sum / n;

        return Math.exp(-r*t) * mean;
    }
}
