package net.mattheard.alphabetafilter;

class Filter {
    SensorListener measurementSource;
    Model model;

    Filter(SensorListener measurementSource, Model model) {
        this.measurementSource = measurementSource;
        this.model = model;
    }
}
