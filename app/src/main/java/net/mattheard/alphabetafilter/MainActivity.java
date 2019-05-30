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
        getSensorsSpinner().setAdapter(getSensorsAdapter());
    }

    private Spinner getSensorsSpinner() {
        return findViewById(R.id.sensorsSpinner);
    }

    private ArrayAdapter<String> getSensorsAdapter() {
        List<String> sensors = getSensors();
        ArrayAdapter<String> sensorsAdapter = getNewSpinnerAdapter(sensors);
        sensorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return sensorsAdapter;
    }

    private List<String> getSensors() {
        return Arrays.asList("io 1", "io 2", "io 3");
    }

    private ArrayAdapter<String> getNewSpinnerAdapter(List<String> sensors) {
        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sensors);
    }
}
