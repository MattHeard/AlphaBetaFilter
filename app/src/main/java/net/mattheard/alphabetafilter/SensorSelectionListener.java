package net.mattheard.alphabetafilter;

import android.hardware.Sensor;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

class SensorSelectionListener implements AdapterView.OnItemSelectedListener {
    private final SensorListener sensorListener;
    private final List<Sensor> sensors;

    SensorSelectionListener(List<Sensor> sensors, SensorListener sensorListener) {
        this.sensors = sensors;
        this.sensorListener = sensorListener;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Sensor sensor = sensors.get(position);
        sensorListener.changeSensor(sensor);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
