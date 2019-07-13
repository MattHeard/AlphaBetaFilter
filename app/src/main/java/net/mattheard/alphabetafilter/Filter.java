package net.mattheard.alphabetafilter;

class Filter {
    private SensorListener measurementSource;
    private Model model;

    Filter(SensorListener measurementSource) {
        this.measurementSource = measurementSource;
        this.model = new Model();
    }

    float getMeasurement() {
        return measurementSource.getMeasurement();
    }

    float getModeledValue() {
        return model.value;
    }

    float getEstimatedValue() {
        return getMeasurement();
    }
}
