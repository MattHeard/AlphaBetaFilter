package net.mattheard.alphabetafilter;

class Filter implements Runnable {
    private Measurer measurementSource;
    private Model model;
    private FilterObserver observer;
    private float measurement;
    private float modeledValue;

    Filter(Measurer measurementSource, Model model) {
        this.measurementSource = measurementSource;
        this.model = model;
    }

    int getPeriod() {
        return 500;
    }

    void setObserver(FilterObserver observer) {
        this.observer = observer;
    }

    @Override
    public void run() {
        tick();
    }

    void tick() {
        updateMeasurement();
        updateModeledValue();
        updateEstimatedValue();
        float modeledValue = getModeledValue();
        float measurement = getMeasurement();
        float estimatedValue = getEstimatedValue();
        observer.notify(modeledValue, measurement, estimatedValue);
    }

    private void updateMeasurement() {
        measurement = measurementSource.getMeasurement();
    }

    private void updateModeledValue() {
        modeledValue = model.value + (model.rateOfChange / 2f);
    }

    private void updateEstimatedValue() {
    }

    private float getMeasurement() {
        return measurement;
    }

    private float getModeledValue() {
        return modeledValue;
    }

    private float getEstimatedValue() {
        return getMeasurement();
    }
}
