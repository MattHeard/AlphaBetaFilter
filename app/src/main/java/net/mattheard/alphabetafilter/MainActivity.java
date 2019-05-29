package net.mattheard.alphabetafilter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> sensors = new ArrayList<>();
        sensors.add("io 1");
        sensors.add("io 2");
        sensors.add("io 3");
        ArrayAdapter<String> sensorsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sensors);
        sensorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sensorSpinner = findViewById(R.id.sensorsSpinner);
        sensorSpinner.setAdapter(sensorsAdapter);
        sensorSpinner.setSelection(0);
    }
}
