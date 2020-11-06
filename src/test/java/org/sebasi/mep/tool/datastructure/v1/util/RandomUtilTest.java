package org.sebasi.mep.tool.datastructure.v1.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomUtilTest {

    @Test
    public void testGetRandomNumberInRange() {
        RandomUtil randomUtil = new RandomUtil();
        for (int i = 0; i < 200; i++) {
            int randomNumber = randomUtil.getRandomNumber(1);
            assertTrue(
                    "Random number was " + randomNumber,
                    randomNumber >= 0 && randomNumber <= 1);
        }
    }

    @Test
    public void testGetRandomNumberWhenMaxIsLow() {
        RandomUtil randomUtil = new RandomUtil();
        for (int i = 0; i < 10; i++) {
            int randomNumber = randomUtil.getRandomNumber(0);
            assertEquals(
                    "Random number was " + randomNumber,
                    0,
                    randomNumber);
        }
        for (int i = 0; i < 10; i++) {
            int randomNumber = randomUtil.getRandomNumber(-1);
            assertEquals(
                    "Random number was " + randomNumber,
                    0,
                    randomNumber);
        }
    }

    @Test
    public void testChanceAsFraction() {
        RandomUtil randomUtil = new RandomUtil();

        assertFalse(randomUtil.shouldEventTrigger(-1, -1));
        assertFalse(randomUtil.shouldEventTrigger(0, -1));
        assertFalse(randomUtil.shouldEventTrigger(1, -1));

        assertFalse(randomUtil.shouldEventTrigger(-1, 0));
        assertFalse(randomUtil.shouldEventTrigger(0, 0));
        assertFalse(randomUtil.shouldEventTrigger(1, 0));

        assertFalse(randomUtil.shouldEventTrigger(-1, 1));
        assertFalse(randomUtil.shouldEventTrigger(0, 1));
        assertTrue(randomUtil.shouldEventTrigger(1, 1));
        assertTrue(randomUtil.shouldEventTrigger(100, 100));
    }
}
