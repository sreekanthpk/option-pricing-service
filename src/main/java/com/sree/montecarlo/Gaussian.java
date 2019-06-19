package com.sree.montecarlo;

import java.util.Random;

public class Gaussian {

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed


    static {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public static double uniform(double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform() * (b-a);
    }

    public static double gaussian() {

        double r, x, y;
        do {
            x = uniform(-1.0, 1.0);
            y = uniform(-1.0, 1.0);
            r = x*x + y*y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

    }

    public static double uniform() {
        return random.nextDouble();
    }
}
