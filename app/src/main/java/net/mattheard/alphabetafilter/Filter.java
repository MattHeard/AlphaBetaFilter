package net.mattheard.alphabetafilter;

class Filter {
    private Measurer measurementSource;
    private Model model;
    private FilterObserver observer;

    Filter(Measurer measurementSource, Model model) {
        this.measurementSource = measurementSource;
        this.model = model;
    }

    void tick() {
        float modeledValue = getModeledValue();
        float measurement = getMeasurement();
        float estimatedValue = getEstimatedValue();
        observer.notify(modeledValue, measurement, estimatedValue);
    }

    int getPeriod() {
        return 500;
    }

    float getMeasurement() {
        return measurementSource.getMeasurement();
    }

    float getModeledValue() {
        return model.value;
    }

    private float getEstimatedValue() {
        return getMeasurement();
    }

    void setObserver(FilterObserver observer) {
        this.observer = observer;
    }
}
