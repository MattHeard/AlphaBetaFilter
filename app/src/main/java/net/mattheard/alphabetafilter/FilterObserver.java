package net.mattheard.alphabetafilter;

interface FilterObserver {
    void notify(float modeledValue, float measurement, float estimatedValue);
}
