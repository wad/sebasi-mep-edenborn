package org.sebasi.mep.tool.datastructure.v1.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChanceTest {
    @Test
    public void testMultipliedBy() {
        assertEquals(0, new Chance(1, 0).multipliedBy(100));
        assertEquals(10, new Chance(1, 10).multipliedBy(100));
        assertEquals(1, new Chance(1, 100).multipliedBy(100));
        assertEquals(200, new Chance(2, 10000).multipliedBy(1000000));
    }
}
