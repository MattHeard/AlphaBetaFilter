package net.mattheard.alphabetafilter;

class Filter implements Runnable {
    private Measurer measurementSource;
    private Model model;
    private FilterObserver observer;
    private float measurement;

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
        float modeledValue = getModeledValue();
        float measurement = getMeasurement();
        float estimatedValue = getEstimatedValue();
        observer.notify(modeledValue, measurement, estimatedValue);
    }

    private float getMeasurement() {
        return measurement;
    }

    private void updateMeasurement() {
        measurement = measurementSource.getMeasurement();
    }

    private float getModeledValue() {
        return model.value;
    }

    private float getEstimatedValue() {
        return getMeasurement();
    }
}
