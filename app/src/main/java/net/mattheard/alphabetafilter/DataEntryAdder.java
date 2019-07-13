package net.mattheard.alphabetafilter;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.List;

class DataEntryAdder implements Runnable {
    private final Chart chart;
    private final List<DataEntry> seriesData;
    private Filter filter;

    DataEntryAdder(Chart chart, List<DataEntry> seriesData, Filter filter) {
        this.chart = chart;
        this.seriesData = seriesData;
        this.filter = filter;
    }

    @Override
    public void run() {
        addDataEntry();
    }

    private void addDataEntry() {
        String label = Integer.toString(chart.getIteration());
        chart.incrementIteration();
        float modeledValue = filter.getModeledValue();
        ValueDataEntry entry = new ValueDataEntry(label, modeledValue);
        float measurement = filter.getMeasurement();
        entry.setValue("measurement", measurement);
        float estimatedValue = filter.getEstimatedValue();
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
