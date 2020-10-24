package org.sebasi.mep.tool.datastructure.v1.util;

public class Chance {

    int numerator;
    int denominator;

    public Chance(
            int numerator,
            int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Chance percent(int percentage) {
        return new Chance(percentage, 100);
    }

    public static Chance hundredPercent() {
        return new Chance(1, 1);
    }
}
