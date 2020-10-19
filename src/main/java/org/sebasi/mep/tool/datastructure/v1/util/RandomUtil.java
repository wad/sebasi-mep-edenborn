package org.sebasi.mep.tool.datastructure.v1.util;

import java.util.Random;

public class RandomUtil {

    Random random;

    public RandomUtil() {
        random = new Random();
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
}
