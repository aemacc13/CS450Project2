package com.example.aemacc13.accelerameter;

import android.app.Activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Observable;

/**
 * Created by aemacc13 on 9/6/2016.
 */
public class AccelerometerHandler extends Observable implements SensorEventListener {

    private final static long ONE_SECOND_IN_MILLIS= 1000;
    private SensorManager sensorManager= null;
    private Sensor accel= null;
    private long prev_time= 0;
    private MainActivity act;

    public AccelerometerHandler(MainActivity act){
        this.act= act;
        this.sensorManager= (SensorManager) act.getSystemService(Activity.SENSOR_SERVICE);
        this.accel= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, this.accel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long curr_time= System.currentTimeMillis();
        if (curr_time - this.prev_time > ONE_SECOND_IN_MILLIS){
            this.prev_time= curr_time;

            //x->0; y->1; z->2
            //float z= sensorEvent.values[2];
            setChanged();
            notifyObservers(sensorEvent.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}
