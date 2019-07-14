package net.mattheard.alphabetafilter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilterTest {
    @Test
    public void testFirstTick() {
        final float expectedModeledValue = 234.56f;
        final float expectedMeasurement = 123.45f;
        Measurer measurer = getStaticMeasurer(expectedMeasurement);
        Model model = new Model();
        model.value = expectedModeledValue;
        Filter filter = new Filter(measurer, model);
        TestFilterObserver observer = new TestFilterObserver();
        filter.setObserver(observer);

        filter.tick();

        assertEquals(null, expectedModeledValue, observer.actualModeledValue, 0.1f);
        assertEquals(null, expectedMeasurement, observer.actualMeasurement, 0.1f);
    }

    @Test
    public void testSecondTick() {
        final float expectedModeledValue = 234.56f;
        final float expectedMeasurement = 123.45f;
        Measurer measurer = getStaticMeasurer(expectedMeasurement);
        Model model = new Model();
        model.value = expectedModeledValue;
        Filter filter = new Filter(measurer, model);
        TestFilterObserver observer = new TestFilterObserver();
        filter.setObserver(observer);
        filter.tick();

        filter.tick();

        assertEquals(null, expectedModeledValue, observer.actualModeledValue, 0.1f);
        assertEquals(null, expectedMeasurement, observer.actualMeasurement, 0.1f);
    }

    @Test
    public void testModelWithRateOfChangeOf2() {
        final float expectedModeledValue = 234.56f;
        Measurer measurer = getStaticMeasurer(0f);
        Model model = new Model();
        model.value = expectedModeledValue;
        model.rateOfChange = 20f;
        Filter filter = new Filter(measurer, model);
        TestFilterObserver observer = new TestFilterObserver();
        filter.setObserver(observer);

        filter.tick();

        assertEquals(null, 244.56f, observer.actualModeledValue, 0.1f);

        filter.tick();

        assertEquals(null, 254.56f, observer.actualModeledValue, 0.1f);
    }

    class TestFilterObserver implements FilterObserver {
        float actualModeledValue;
        float actualMeasurement;
        float actualEstimatedValue;

        @Override
        public void notify(float modeledValue, float measurement, float estimatedValue) {
            actualModeledValue = modeledValue;
            actualMeasurement = measurement;
            actualEstimatedValue = estimatedValue;
        }
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
}