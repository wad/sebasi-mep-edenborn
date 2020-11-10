package org.sebasi.mep.tool.datastructure.v1.util;

import java.util.Random;

public class RandomUtil {

    Random random;

    public RandomUtil() {
        random = new Random();
    }

    public boolean shouldEventTrigger(Chance chance) {
        return shouldEventTrigger(
                chance.numerator,
                chance.denominator);
    }

    public boolean shouldEventTrigger(
            int numerator,
            int denominator) {

        if (denominator <= 0 || numerator <= 0) {
            return false;
        }

        if (denominator <= numerator) {
            return true;
        }

        return numerator <= random.nextInt(denominator);
    }

    // returns a value between 0 and max, inclusive
    public int getRandomNumber(int max) {
        if (max < 1) {
            return 0;
        }
        return random.nextInt(max + 1);
    }

    public int getRandomNumberInNormalDistribution(
            int mean,
            int sd) {

        /*
        Z = (X - u) / s
        where:
        Z = value on the standard normal distribution
        X = value on the original distribution
        u = mean of the original distribution
        s = standard deviation of the original distribution

         Z = (X - u) / s;
         Zs = X - u
         Zs+u = X
         */
        double Z = random.nextGaussian();
        return (int) ((Z * (double) sd) + (double) mean);
    }
}
