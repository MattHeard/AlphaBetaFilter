package net.mattheard.alphabetafilter;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.data.Mapping;
import com.anychart.data.Set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Chart {
    private final Set set;
    private final AnyChartView renderer;
    private final List<DataEntry> seriesData;
    private final ScheduledExecutorService executor;
    private final SensorListener sensorListener;
    private int iteration;
    private Model model;

    Chart(final AnyChartView renderer, SensorListener sensorListener) {
        set = Set.instantiate();
        seriesData = new ArrayList<>();
        iteration = 0;
        executor = getNewExecutor();
        this.renderer = renderer;
        this.sensorListener = sensorListener;
    }

    private ScheduledExecutorService getNewExecutor() {
        return Executors.newScheduledThreadPool(1);
    }

    void setUp() {
        Cartesian chart = getNewChart();
        addLines(chart);
        renderer.setChart(chart);
        addChartData();
        subscribeToNewData();
    }

    private void addLines(Cartesian chart) {
        String[] names = {"model", "measurement", "estimate"};
        Map<String, Mapping> mappingsByName = getMappingsByName(names);
        for (String name : names) {
            Mapping mapping = mappingsByName.get(name);
            chart.line(mapping);
        }
    }

    private Map<String, Mapping> getMappingsByName(String[] names) {
        Map<String, Mapping> mappingsByName = new HashMap<>();
        for (String name : names) {
            mappingsByName.put(name, set.mapAs(String.format("{ x: 'x', value: '%s' }", name)));
        }
        return mappingsByName;
    }

    private Cartesian getNewChart() {
        return AnyChart.line();
    }

    private void addChartData() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addDataEntry();
            }
        };
        executor.scheduleAtFixedRate(runnable, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void subscribeToNewData() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                set.data(seriesData);
            }
        };
        executor.scheduleAtFixedRate(runnable, 0, 100, TimeUnit.MILLISECONDS);
    }

    private void addDataEntry() {
        String label = Integer.toString(iteration++);
        float measurement = sensorListener.getMeasurement();
        model = new Model();
        float modeledValue = model.value;
        ValueDataEntry entry = new ValueDataEntry(label, modeledValue);
        entry.setValue("measurement", measurement);
        entry.setValue("estimate", measurement);
        seriesData.add(entry);
        removeOldData();
    }

    class Model {
        float value = 0.0f;
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
