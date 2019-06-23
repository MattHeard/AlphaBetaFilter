package net.mattheard.alphabetafilter;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.List;

class DataEntryAdder implements Runnable {
    private final Chart chart;
    private final SensorListener sensorListener;
    private final Model model;
    private final List<DataEntry> seriesData;

    DataEntryAdder(Chart chart, SensorListener sensorListener, Model model, List<DataEntry> seriesData) {
        this.chart = chart;
        this.sensorListener = sensorListener;
        this.model = model;
        this.seriesData = seriesData;
    }

    @Override
    public void run() {
        addDataEntry();
    }

    private void addDataEntry() {
        String label = Integer.toString(chart.getIteration());
        chart.incrementIteration();
        Filter filter = new Filter(sensorListener, model);
        float measurement = filter.measurementSource.getMeasurement();
        float modeledValue = filter.model.value;
        ValueDataEntry entry = new ValueDataEntry(label, modeledValue);
        entry.setValue("measurement", measurement);
        entry.setValue("estimate", measurement);
        seriesData.add(entry);
        removeOldData();
    }

    class Filter {
        SensorListener measurementSource;
        Model model;

        Filter(SensorListener measurementSource, Model model) {
            this.measurementSource = measurementSource;
            this.model = model;
        }
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
