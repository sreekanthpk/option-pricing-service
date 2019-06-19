package com.sree.price;

public class BlackScholes {

    public static double cumilativeDistributionFunction(double z) {
        if (z < -8.0) return 0.0;
        if (z >  8.0) return 1.0;
        double sum = 0.0, term = z;
        for (int i = 3; sum + term != sum; i += 2) {
            sum  = sum + term;
            term = term * z * z / i;
        }
        return 0.5 + sum * probabilityDensityFunction(z);
    }

    public static double probabilityDensityFunction(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    // Black-Scholes formula
    public static double putPrice(double stockPrice , double strike, double interestRate, double impliedVolatility, double time){
        double d1 = (Math.log(stockPrice/strike) + (interestRate + impliedVolatility * impliedVolatility/2) * time) / (impliedVolatility * Math.sqrt(time));
        double d2 = d1 - impliedVolatility * Math.sqrt(time);
        double cd1 = cumilativeDistributionFunction(-d1);
        double cd2 = cumilativeDistributionFunction(-d2);
        return strike * Math.exp(-interestRate * time) * cd2 - (stockPrice * cd1);
    }


    // Black-Scholes formula
    public static double callPrice(double stockPrice , double strike, double interestRate, double impliedVolatility, double time) {
        double d1 = (Math.log(stockPrice/strike) + (interestRate + impliedVolatility * impliedVolatility/2) * time) / (impliedVolatility * Math.sqrt(time));
        double d2 = d1 - impliedVolatility * Math.sqrt(time);
        return stockPrice * cumilativeDistributionFunction(d1) - strike * Math.exp(-interestRate*time) * cumilativeDistributionFunction(d2);
    }

}
