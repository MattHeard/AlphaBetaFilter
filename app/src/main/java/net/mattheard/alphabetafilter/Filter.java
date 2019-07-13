package net.mattheard.alphabetafilter;

class Filter {
    private Measurer measurementSource;
    private Model model;

    Filter(Measurer measurementSource, Model model) {
        this.measurementSource = measurementSource;
        this.model = model;
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

    float getEstimatedValue() {
        return getMeasurement();
    }
}
