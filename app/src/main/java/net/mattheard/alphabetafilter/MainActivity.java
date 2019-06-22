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
        new Chart(getChartView(), sensorListener).setUp();
    }

    private AnyChartView getChartView() {
        return findViewById(R.id.chart);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, getSpinnerItemLayout(), sensors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private int getSpinnerItemLayout() {
        return android.R.layout.simple_spinner_item;
    }

}
