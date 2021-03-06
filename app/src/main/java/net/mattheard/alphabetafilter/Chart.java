package net.mattheard.alphabetafilter;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
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
    private final List<DataEntry> seriesData;
    private final ScheduledExecutorService executor;
    private int iteration;

    Chart(final AnyChartView renderer, Filter filter) {
        set = Set.instantiate();
        seriesData = new ArrayList<>();
        iteration = 0;
        executor = getNewExecutor();
        Cartesian chart = getNewChart();
        addLines(chart);
        renderer.setChart(chart);
        final DataEntryAdder adder = getDataEntryAdder();
        filter.setObserver(adder);
        executor.scheduleAtFixedRate(filter, 0, filter.getPeriod(), TimeUnit.MILLISECONDS);
        subscribeToNewData();
    }

    private ScheduledExecutorService getNewExecutor() {
        return Executors.newScheduledThreadPool(1);
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

    private DataEntryAdder getDataEntryAdder() {
        return new DataEntryAdder(this, seriesData);
    }

    private void subscribeToNewData() {
        final Runnable updater = new ChartDataSetUpdater(set, seriesData);
        executor.scheduleAtFixedRate(updater, 0, 100, TimeUnit.MILLISECONDS);
    }

    void incrementIteration() {
        iteration++;
    }

    int getIteration() {
        return iteration;
    }

}
