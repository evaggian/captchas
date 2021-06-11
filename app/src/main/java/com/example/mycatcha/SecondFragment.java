package com.example.mycatcha;
import com.example.mycatcha.R.color;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mycatcha.databinding.ActivityMain3Binding;
import com.example.mycatcha.databinding.FragmentSecondBinding;

import java.util.Objects;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getColor;
import static androidx.core.content.ContextCompat.getSystemService;

public class SecondFragment extends Fragment implements SensorEventListener{

    private FragmentSecondBinding binding;

    private SensorManager sensorManager;
    private SensorEventListener proximitySensorListener, gyroscopeSensorListener;
    private Sensor proximitySensor, gyroscopeSensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_SUSSecondFragment);
            }
        });

        /* -------------------- proximity sensor --------------------- */

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(proximitySensor == null) {
            Log.e("ERROR: ", "Proximity sensor not available.");
            requireActivity().finish(); // Close app
        }

        // Create listener
        proximitySensorListener = new SensorEventListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                    // Detected something nearby
                    //getWindow().getDecorView().setBackgroundColor(Color.RED);
                    //findViewById(R.id.proximity_view).setBackgroundColor(getColor(R.color.gotcha_green));
                    getView().findViewById(R.id.proximity_view).setBackgroundColor(color.gotcha_green);
                    //Toast.makeText(getApplicationContext(),"Proximity CAPTCHA validation successful!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Proximity CAPTCHA validation successful!", Toast.LENGTH_LONG).show();
                } else {
                    // Nothing is nearby
                    Log.i("nothing nearby", "nothing nearby");
                    getView().findViewById(R.id.proximity_view).setBackgroundColor(color.gotcha_red);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

        };

    }

    @Override
    public void onResume() {
        super.onResume();

        // Register it, specifying the polling interval in
        // microseconds
        sensorManager.registerListener(proximitySensorListener,
                proximitySensor, 2 * 1000 * 1000);
    }

    @Override
    public void onPause() {
        super.onPause();

        // unregister listener
        sensorManager.unregisterListener(proximitySensorListener);
        sensorManager.unregisterListener(gyroscopeSensorListener);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}