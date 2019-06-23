package net.mattheard.alphabetafilter;

class Filter {
    SensorListener measurementSource;
    Model model;

    Filter(SensorListener measurementSource) {
        this.measurementSource = measurementSource;
        this.model = new Model();
    }
}
