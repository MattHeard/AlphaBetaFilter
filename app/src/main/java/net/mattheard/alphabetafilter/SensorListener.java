package net.mattheard.alphabetafilter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class SensorListener implements SensorEventListener {

    private final Sensor sensor;
    private MainActivity mainActivity;
    private float measurement;

    SensorListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        sensor = mainActivity.getSensors().get(0);
    }

    void register() {
        mainActivity.getSensorManager().registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    void unregister() {
        mainActivity.getSensorManager().unregisterListener(this);
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
