package org.sebasi.mep.tool.datastructure.v1.util;

// This is just used to represent a fraction, which is often used to express a probability.
public class Chance {

    int numerator;
    int denominator;

    public Chance(
            int numerator,
            int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Chance fraction(
            int numerator,
            int denominator) {
        return new Chance(numerator, denominator);
    }

    public static Chance percent(int percentage) {
        return new Chance(percentage, 100);
    }

    public static Chance hundredPercent() {
        return new Chance(1, 1);
    }

    public int multipliedBy(int number) {
        if (denominator == 0) {
            return 0;
        }

        return (numerator * number) / denominator;
    }
}
