package net.mattheard.alphabetafilter;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.anychart.AnyChartView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorListener sensorListener;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new Model();
        setUpInitialModelValueField();
        setUpSensorsSpinner();
        setUpSensorListener();
        setUpChart();
    }

    private void setUpInitialModelValueField() {
        TextView field = findViewById(R.id.initialValueEstimateField);
        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    model.value = Float.parseFloat(s.toString());
                } catch (NumberFormatException e) {
                    // Leave model value unchanged
                }
            }
        });
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
        Filter filter = new Filter(sensorListener, model);
        new Chart(getChartView(), filter).setUp();
    }

    private AnyChartView getChartView() {
        return findViewById(R.id.chart);
    }

    private void setUpSensorsSpinner() {
        Spinner spinner = getSensorsSpinner();
        spinner.setAdapter(getSensorsAdapter());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sensor sensor = getSensors().get(position);
                sensorListener.changeSensor(sensor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
