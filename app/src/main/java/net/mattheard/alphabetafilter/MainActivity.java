package net.mattheard.alphabetafilter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSensorSpinner();
    }

    private void setUpSensorSpinner() {
        ArrayAdapter<String> sensorsAdapter = getSensorsAdapter();
        Spinner sensorSpinner = getSensorsSpinner();
        sensorSpinner.setAdapter(sensorsAdapter);
    }

    private Spinner getSensorsSpinner() {
        return findViewById(R.id.sensorsSpinner);
    }

    private ArrayAdapter<String> getSensorsAdapter() {
        List<String> sensors = Arrays.asList("io 1", "io 2", "io 3");
        ArrayAdapter<String> sensorsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sensors);
        sensorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return sensorsAdapter;
    }
}
