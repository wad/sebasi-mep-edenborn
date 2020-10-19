package org.sebasi.mep.tool.datastructure.v1.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RandomUtilTest {
    @Test
    public void testChanceAsFraction() {
        RandomUtil randomUtil = new RandomUtil();
        System.out.println(randomUtil.shouldEventTrigger(1, 2));
        System.out.println(randomUtil.shouldEventTrigger(1, 2));
        System.out.println(randomUtil.shouldEventTrigger(1, 2));
        System.out.println(randomUtil.shouldEventTrigger(1, 2));
        System.out.println(randomUtil.shouldEventTrigger(1, 2));
        System.out.println(randomUtil.shouldEventTrigger(1, 2));
        System.out.println(randomUtil.shouldEventTrigger(1, 2));

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
