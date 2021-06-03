package com.example.mycatcha;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.mycatcha.databinding.ActivityMain3Binding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mycatcha.databinding.ActivityMain2Binding;

public class MainActivity3 extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMain3Binding binding;

    private SensorManager sensorManager;
    private SensorEventListener proximitySensorListener, gyroscopeSensorListener, rvListener;
    private Sensor proximitySensor, gyroscopeSensor, rotationVectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        /* -------------------- proximity sensor --------------------- */

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(proximitySensor == null) {
            Log.e("ERROR: ", "Proximity sensor not available.");
            finish(); // Close app
        }

        // Create listener
        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                    // Detected something nearby
                    //getWindow().getDecorView().setBackgroundColor(Color.RED);
                    findViewById(R.id.proximity_view).setBackgroundColor(Color.GREEN);
                    Toast.makeText(getApplicationContext(),"Proximity CAPTCHA validation successful!", Toast.LENGTH_SHORT).show();
                } else {
                    // Nothing is nearby
                    getWindow().getDecorView().setBackgroundColor(Color.GRAY);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

        };

        /* -------------------- gyroscope sensor --------------------- */

        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Create a listener
        gyroscopeSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[2] > 0.5f) { // anticlockwise
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                } else if(sensorEvent.values[2] < -0.5f) { // clockwise
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        // Register the listener
        sensorManager.registerListener(gyroscopeSensorListener,
                gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

// Register it
        sensorManager.registerListener(rvListener,
                rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register it, specifying the polling interval in
        // microseconds
        sensorManager.registerListener(proximitySensorListener,
                proximitySensor, 2 * 1000 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister listener
        sensorManager.unregisterListener(proximitySensorListener);
        sensorManager.unregisterListener(gyroscopeSensorListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}