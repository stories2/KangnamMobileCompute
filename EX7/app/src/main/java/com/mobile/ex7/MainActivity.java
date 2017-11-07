package com.mobile.ex7;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

public class MainActivity extends Activity implements SensorEventListener{

    SensorManager sensorManager;
    Sensor acceler, proximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        acceler = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(acceler != null) {
            sensorManager.registerListener(this, acceler, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(proximity != null) {
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(acceler != null) {
            sensorManager.registerListener(this, acceler, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(proximity != null) {
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(acceler != null) {
            sensorManager.unregisterListener(this);
        }

        if(proximity != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                float accel[] = sensorEvent.values;
                Log.d("Test", "accel " + Arrays.toString(accel));
                break;
            case Sensor.TYPE_PROXIMITY:
                float proxi[] = sensorEvent.values;
                Log.d("Test", "proxi: " + Arrays.toString(proxi));
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
