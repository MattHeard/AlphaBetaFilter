package net.mattheard.alphabetafilter;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.anychart.AnyChartView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorListener sensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSensorsSpinner();
        setUpSensorListener();
        setUpChart();
    }

    private void setUpSensorListener() {
        sensorListener = new SensorListener(getSensorManager(), getFirstSensor());
        sensorListener.register();
    }

    private Sensor getFirstSensor() {
        return getSensors().get(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorListener.unregister();
    }

    private void setUpChart() {
        AnyChartView chartView = findViewById(R.id.chart);
        Chart chart = new Chart(chartView, sensorListener);
        chart.setUp();
        chart.addChartData();
        chart.subscribeToNewData();
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

    public List<Sensor> getSensors() {
        return getSensorManager().getSensorList(Sensor.TYPE_ALL);
    }

    public SensorManager getSensorManager() {
        return (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    private ArrayAdapter<String> getNewSpinnerAdapter(List<String> sensors) {
        final int layout = android.R.layout.simple_spinner_item;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layout, sensors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

}
