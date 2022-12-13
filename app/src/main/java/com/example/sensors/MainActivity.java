package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sManager;
    private Sensor sensorLight, sensorProximity;
    private TextView viewLightDetails, viewProximityDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Display all Sensors

        TextView viewSensorList = findViewById(R.id.viewSensorList);

        List<Sensor> sensorList = sManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sensorBuilder = new StringBuilder();
        for (Sensor sensor: sensorList) {
            sensorBuilder.append(sensor.getName()).append("\n");
        }

        viewSensorList.setText(sensorBuilder);

        /*viewLightDetails = findViewById(R.id.viewLightDetails);
        viewProximityDetails = findViewById(R.id.viewProximityDetails);

        sensorLight = sManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProximity = sManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        String noSensor = getString(R.string.no_sensor);

        if (sensorLight == null)
            viewLightDetails.setText(noSensor);

        if (sensorProximity == null)
            viewProximityDetails.setText(noSensor);*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*if (sensorLight != null)
            sManager.registerListener(this, sensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);

        if (sensorProximity != null)
            sManager.registerListener(this, sensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);*/
    }

    @Override
    protected void onStop() {
        super.onStop();

        //sManager.unregisterListener(this);
    }

    /**
     * Called when there is a new sensor event.  Note that "on changed"
     * is somewhat of a misnomer, as this will also be called if we have a
     * new reading from a sensor with the exact same sensor values (but a
     * newer timestamp).
     *
     * <p>See {@link SensorManager SensorManager}
     * for details on possible sensor types.
     * <p>See also {@link SensorEvent SensorEvent}.
     *
     * <p><b>NOTE:</b> The application doesn't own the
     * {@link SensorEvent event}
     * object passed as a parameter and therefore cannot hold on to it.
     * The object may be part of an internal pool and may be reused by
     * the framework.
     *
     * @param event the {@link SensorEvent SensorEvent}.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        int sensorType = event.sensor.getType();

        float currentValue = event.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                viewLightDetails.setText(getString(
                        R.string.light_sensor_details, currentValue));

                int newIntBG = Math.round(currentValue);
                String newHexBG = String.format("#%06X", newIntBG);

                getWindow().getDecorView()
                        .setBackgroundColor(Color.parseColor(newHexBG));
                break;

            case Sensor.TYPE_PROXIMITY:
                viewProximityDetails.setText(getString(
                        R.string.proximity_sensor_details, currentValue));
                break;
        }
    }

    /**
     * Called when the accuracy of the registered sensor has changed.  Unlike
     * onSensorChanged(), this is only called when this accuracy value changes.
     *
     * <p>See the SENSOR_STATUS_* constants in
     * {@link SensorManager SensorManager} for details.
     *
     * @param sensor
     * @param accuracy The new accuracy of this sensor, one of
     *                 {@code SensorManager.SENSOR_STATUS_*}
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}