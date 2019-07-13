package net.mattheard.alphabetafilter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilterTest {
    @Test
    public void testGetMeasurement() {
        final float expectedMeasurement = 123.45f;
        Measurer measurer = new Measurer() {
            @Override
            public float getMeasurement() {
                return expectedMeasurement;
            }
        };
        Filter filter = new Filter(measurer);

        final float actualMeasurement = filter.getMeasurement();

        assertEquals(null, expectedMeasurement, actualMeasurement, 0.1f);
    }

    @Test
    public void testGetPeriod() {
        Measurer measurer = new Measurer() {
            @Override
            public float getMeasurement() {
                return 0;
            }
        };
        Filter filter = new Filter(measurer);

        final int actualPeriod = filter.getPeriod();

        assertEquals(null, 500, actualPeriod);
    }
}