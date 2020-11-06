package org.sebasi.mep.tool.datastructure.v1.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.sebasi.mep.tool.datastructure.v1.util.Histogram.DESIRED_BAR_LENGTH;

public class HistogramTest {

    @Test
    public void testSimple() {
        Histogram histogram = new Histogram();
        histogram.addDataPoint(10);
        System.out.println(histogram.show());
    }

    @Test
    public void testRandom() {
        RandomUtil randomUtil = new RandomUtil();
        Histogram histogram = new Histogram();
        for (int i = 0; i < 50; i++) {
            histogram.addDataPoint(randomUtil.getRandomNumber(99));
        }
        System.out.println(histogram.show());
    }

    @Test
    public void testBucketing() {
        assertEquals(0, Histogram.determineWhichBucket(11, 11, 60, 5));
        assertEquals(2, Histogram.determineWhichBucket(39, 11, 60, 5));
        assertEquals(2, Histogram.determineWhichBucket(40, 11, 60, 5));
        assertEquals(3, Histogram.determineWhichBucket(41, 11, 60, 5));
        assertEquals(3, Histogram.determineWhichBucket(42, 11, 60, 5));
        assertEquals(4, Histogram.determineWhichBucket(59, 11, 60, 5));
        assertEquals(4, Histogram.determineWhichBucket(60, 11, 60, 5));

        assertEquals(0, Histogram.determineWhichBucket(0, 0, 99, 10));
        assertEquals(0, Histogram.determineWhichBucket(1000, 1000, 1099, 10));
        assertEquals(5, Histogram.determineWhichBucket(50, 0, 99, 10));
        assertEquals(9, Histogram.determineWhichBucket(99, 0, 99, 10));
    }

    @Test
    public void testDetermineNumCharsInBar() {
        assertEquals(0, Histogram.determineNumCharsInBar(0, 100));
        assertEquals(DESIRED_BAR_LENGTH >> 1, Histogram.determineNumCharsInBar(50, 100));
        assertEquals(DESIRED_BAR_LENGTH, Histogram.determineNumCharsInBar(100, 100));
    }
}
