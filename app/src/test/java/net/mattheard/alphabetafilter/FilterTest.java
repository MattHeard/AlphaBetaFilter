package net.mattheard.alphabetafilter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilterTest {
    @Test
    public void testGetMeasurement() {
        final float expectedMeasurement = 123.45f;
        Filter filter = getFilterWithStaticMeasurement(expectedMeasurement);

        final float actualMeasurement = filter.getMeasurement();

        assertEquals(null, expectedMeasurement, actualMeasurement, 0.1f);
    }

    private Filter getFilterWithStaticMeasurement() {
        return getFilterWithStaticMeasurement(0);
    }

    private Filter getFilterWithStaticMeasurement(float measurement) {
        Measurer measurer = getStaticMeasurer(measurement);
        Model model = new Model();
        return new Filter(measurer, model);
    }

    private Measurer getStaticMeasurer(final float measurement) {
        return new Measurer() {
            @Override
            public float getMeasurement() {
                return measurement;
            }
        };
    }

    @Test
    public void testGetPeriod() {
        Filter filter = getFilterWithStaticMeasurement();

        final int actualPeriod = filter.getPeriod();

        assertEquals(null, 500, actualPeriod);
    }

    @Test
    public void testFirstModelValue() {
        Filter filter = getFilterWithStaticMeasurement();

        final float actualModeledValue = filter.getModeledValue();

        assertEquals(null, 0, actualModeledValue, 0.1f);
    }

    @Test
    public void testSecondModelValue() {
        Filter filter = getFilterWithStaticMeasurement();
        filter.getModeledValue();

        final float actualModeledValue = filter.getModeledValue();

        assertEquals(null, 0, actualModeledValue, 0.1f);
    }
}