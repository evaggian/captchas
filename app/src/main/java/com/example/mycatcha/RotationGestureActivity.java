package com.example.mycatcha;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mycatcha.databinding.ActivityMain2Binding;

public class RotationGestureActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMain2Binding binding;

    private SensorManager sensorManager;
    private SensorEventListener proximitySensorListener, gyroscopeSensorListener, rvListener;
    private Sensor proximitySensor, gyroscopeSensor, rotationVectorSensor;

    //private CheckBox rotateCheck = (CheckBox)findViewById(R.id.instructions_gesture_captcha);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        rotationVectorSensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        // Create a listener
        rvListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(
                        rotationMatrix, sensorEvent.values);

                // Remap coordinate system
                float[] remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_X,
                        SensorManager.AXIS_Z,
                        remappedRotationMatrix);

                // Convert to orientations
                float[] orientations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix, orientations);

                for(int i = 0; i < 3; i++) {
                    orientations[i] = (float)(Math.toDegrees(orientations[i]));
                }

                Log.i("INFO", "Orientation =  " +  orientations[2]);
                /*if(orientations[2] > 45) {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                } else if(orientations[2] < -45) {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                } else if(Math.abs(orientations[2]) < 10) {
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }*/

                if (orientations[2] > 170){
                    System.out.println("Congrats you succeeded!");
                    Toast.makeText(getApplicationContext(),"Rotation CAPTCHA validation successful!", Toast.LENGTH_SHORT).show();
                    //rotateCheck.setChecked(true);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

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