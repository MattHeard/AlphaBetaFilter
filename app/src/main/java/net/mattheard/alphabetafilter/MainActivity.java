package net.mattheard.alphabetafilter;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSensorsSpinner();
        setUpChart();
    }

    private void setUpSensorsSpinner() {
        getSensorsSpinner().setAdapter(getSensorsAdapter());
    }

    private Spinner getSensorsSpinner() {
        return findViewById(R.id.sensorsSpinner);
    }

    private ArrayAdapter<String> getSensorsAdapter() {
        return getNewSpinnerAdapter(getSensorNames());
    }

    private List<String> getSensorNames() {
        List<String> names = new ArrayList<>();
        for (Sensor sensor : getSensors()) {
            names.add(sensor.getName());
        }
        return names;
    }

    private List<Sensor> getSensors() {
        return getSensorManager().getSensorList(Sensor.TYPE_ALL);
    }

    private SensorManager getSensorManager() {
        return (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    private ArrayAdapter<String> getNewSpinnerAdapter(List<String> sensors) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                sensors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void setUpChart() {
        AnyChartView chartView = findViewById(R.id.chart);
        Set set = Set.instantiate();
        setUpChart(chartView, set);
        addChartData(set);
    }

    private void addChartData(Set set) {
        List<DataEntry> seriesData = new ArrayList<>();
        for (int i = 1986; i < 2010; i++) {
            addDataEntry(seriesData, set, Integer.toString(i));
        }
    }

    private void setUpChart(AnyChartView chartView, Set set) {
        Cartesian chart = AnyChart.line();
        Map<String, Mapping> mappingsByName = new HashMap<>();
        String[] names = {"model", "measurement", "estimate"};
        for (String name : names) {
            mappingsByName.put(name, set.mapAs(String.format("{ x: 'x', value: '%s' }", name)));
            chart.line(mappingsByName.get(name));
        }
        chartView.setChart(chart);
    }

    private void addDataEntry(List<DataEntry> seriesData, Set set, String label) {
        ValueDataEntry entry = new ValueDataEntry(label, getRandomValue());
        entry.setValue("measurement", getRandomValue());
        entry.setValue("estimate", getRandomValue());
        seriesData.add(entry);
        set.data(seriesData);
    }

    private double getRandomValue() {
        return ThreadLocalRandom.current().nextDouble(3, 20);
    }
}
