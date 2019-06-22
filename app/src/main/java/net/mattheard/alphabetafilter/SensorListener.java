package net.mattheard.alphabetafilter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class SensorListener implements SensorEventListener {

    private Sensor sensor;
    private final SensorManager sensorManager;
    private float measurement;

    SensorListener(SensorManager sensorManager, Sensor sensor) {
        this.sensorManager = sensorManager;
        this.sensor = sensor;
    }

    void register() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    void unregister() {
        sensorManager.unregisterListener(this);
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

    void changeSensor(Sensor sensor) {
        this.sensor = sensor;
        unregister();
        register();
    }
}
