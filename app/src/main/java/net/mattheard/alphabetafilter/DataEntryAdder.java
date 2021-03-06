package net.mattheard.alphabetafilter;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.List;

class DataEntryAdder implements FilterObserver {
    private final Chart chart;
    private final List<DataEntry> seriesData;

    DataEntryAdder(Chart chart, List<DataEntry> seriesData) {
        this.chart = chart;
        this.seriesData = seriesData;
    }

    @Override
    public void notify(float modeledValue, float measurement, float estimatedValue) {
        addDataEntry(modeledValue, measurement, estimatedValue);
    }

    private void addDataEntry(float modeledValue, float measurement, float estimatedValue) {
        String label = Integer.toString(chart.getIteration());
        chart.incrementIteration();
        ValueDataEntry entry = new ValueDataEntry(label, modeledValue);
        entry.setValue("measurement", measurement);
        entry.setValue("estimate", estimatedValue);
        seriesData.add(entry);
        removeOldData();
    }

    private void removeOldData() {
        while (seriesData.size() > getMaxSeriesLength()) {
            seriesData.remove(0);
        }
    }

    private int getMaxSeriesLength() {
        return 50;
    }
}
