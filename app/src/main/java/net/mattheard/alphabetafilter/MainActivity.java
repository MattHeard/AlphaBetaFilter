package net.mattheard.alphabetafilter;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.anychart.AnyChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSensorsSpinner();
        setUpChart();
        List<Integer> numbers = new ArrayList<>();
        Log.i("threading", String.format("onCreate: hello, %s", numbers));
        final Runnable appender = new ListAppender(numbers);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(appender, 0, 100, TimeUnit.MILLISECONDS);
        final Runnable reader = new ListReader(numbers);
        executor.scheduleAtFixedRate(reader, 0, 1, TimeUnit.SECONDS);
    }

    private class ListAppender implements Runnable {
        private final List<Integer> numbers;
        private int number;

        ListAppender(List<Integer> numbers) {
            this.numbers = numbers;
            number = 0;
        }

        public void run() {
            Log.i("threading", "run: add " + number);
            numbers.add(number++);
        }
    }

    private class ListReader implements Runnable {
        private final List<Integer> numbers;

        ListReader(List<Integer> numbers) {
            this.numbers = numbers;
        }

        public void run() {
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Log.i("threading", String.format("run: hello, %s", numbers));
                }
            };
            runOnUiThread(runnable);
        }
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
        Chart chart = new Chart(chartView);
        chart.setUp();
        chart.addChartData();
    }

}
