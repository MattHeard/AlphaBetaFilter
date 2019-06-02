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
import java.util.concurrent.ThreadLocalRandom;

class Chart {
    private final Set set;
    private final AnyChartView renderer;
    private final List<DataEntry> seriesData;

    Chart(final AnyChartView renderer) {
        set = Set.instantiate();
        seriesData = new ArrayList<>();
        this.renderer = renderer;
    }

    void setUp() {
        Cartesian chart = AnyChart.line();
        Map<String, Mapping> mappingsByName = new HashMap<>();
        String[] names = {"model", "measurement", "estimate"};
        for (String name : names) {
            mappingsByName.put(name, set.mapAs(String.format("{ x: 'x', value: '%s' }", name)));
            chart.line(mappingsByName.get(name));
        }
        renderer.setChart(chart);
    }

    void addChartData() {
        for (int i = 1986; i < 2010; i++) {
            addDataEntry(set, Integer.toString(i));
        }
        set.data(seriesData);
    }

    private void addDataEntry(Set set, String label) {
        ValueDataEntry entry = new ValueDataEntry(label, getRandomValue());
        entry.setValue("measurement", getRandomValue());
        entry.setValue("estimate", getRandomValue());
        seriesData.add(entry);
    }

    private double getRandomValue() {
        return ThreadLocalRandom.current().nextDouble(3, 20);
    }
}
