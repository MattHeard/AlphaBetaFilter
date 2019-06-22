package net.mattheard.alphabetafilter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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
        sensorListener = new SensorListener();
        sensorListener.register();
        setUpChart();
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

    class SensorListener implements SensorEventListener {

        private final Sensor sensor;
        private float measurement;

        SensorListener() {
            sensor = getSensors().get(0);
        }

        void register() {
            getSensorManager().registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        void unregister() {
            getSensorManager().unregisterListener(this);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            measurement = event.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        float getMeasurement() {
            return measurement;
        }
    }
}
