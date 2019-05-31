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
import java.util.List;

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
        Cartesian chart = AnyChart.line();
        List<DataEntry> seriesData = new ArrayList<>();
        addDataEntries(seriesData);

        // Q: What is a Set?
        Set set = Set.instantiate();
        set.data(seriesData);

        // Q: What is a Mapping?
        Mapping modelDataMapping = set.mapAs("{ x: 'x', value: 'model' }");
        Mapping measurementDataMapping = set.mapAs("{ x: 'x', value: 'measurement' }");
        Mapping estimateDataMapping = set.mapAs("{ x: 'x', value: 'estimate' }");

        // Q: What does line() do here?
        chart.line(modelDataMapping);
        chart.line(measurementDataMapping);
        chart.line(estimateDataMapping);

        // Q: What does setChart() do here?
        chartView.setChart(chart);
    }

    private void addDataEntries(List<DataEntry> seriesData) {
        addDataEntry(seriesData, "1986", 3.6, 2.3, 2.8);
        addDataEntry(seriesData, "1987", 7.1, 4.0, 4.1);
        addDataEntry(seriesData, "1988", 8.5, 6.2, 5.1);
        addDataEntry(seriesData, "1989", 9.2, 11.8, 6.5);
        addDataEntry(seriesData, "1990", 10.1, 13.0, 12.5);
        addDataEntry(seriesData, "1991", 11.6, 13.9, 18.0);
        addDataEntry(seriesData, "1992", 16.4, 18.0, 21.0);
        addDataEntry(seriesData, "1993", 18.0, 23.3, 20.3);
        addDataEntry(seriesData, "1994", 13.2, 24.7, 19.2);
        addDataEntry(seriesData, "1995", 12.0, 18.0, 14.4);
        addDataEntry(seriesData, "1996", 3.2, 15.1, 9.2);
        addDataEntry(seriesData, "1997", 4.1, 11.3, 5.9);
        addDataEntry(seriesData, "1998", 6.3, 14.2, 5.2);
        addDataEntry(seriesData, "1999", 9.4, 13.7, 4.7);
        addDataEntry(seriesData, "2000", 11.5, 9.9, 4.2);
        addDataEntry(seriesData, "2001", 13.5, 12.1, 1.2);
        addDataEntry(seriesData, "2002", 14.8, 13.5, 5.4);
        addDataEntry(seriesData, "2003", 16.6, 15.1, 6.3);
        addDataEntry(seriesData, "2004", 18.1, 17.9, 8.9);
        addDataEntry(seriesData, "2005", 17.0, 18.9, 10.1);
        addDataEntry(seriesData, "2006", 16.6, 20.3, 11.5);
        addDataEntry(seriesData, "2007", 14.1, 20.7, 12.2);
        addDataEntry(seriesData, "2008", 15.7, 21.6, 10);
        addDataEntry(seriesData, "2009", 12.0, 22.5, 8.9);
    }

    private void addDataEntry(List<DataEntry> seriesData, String label, double model, double measurement, double estimate) {
        ValueDataEntry entry = new ValueDataEntry(label, model);
        entry.setValue("measurement", measurement);
        entry.setValue("estimate", estimate);
        seriesData.add(entry);
    }
}
