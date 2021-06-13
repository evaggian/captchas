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
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getColor;
import static androidx.core.content.ContextCompat.getSystemService;

public class SecondFragment extends Fragment implements SensorEventListener{

    private FragmentSecondBinding binding;

    private SensorManager sensorManager;
    private SensorEventListener proximitySensorListener;
    private Sensor proximitySensor;

    private EditText proximityFirstName;
    private EditText proximityLastName;
    private EditText proximityEmail;
    private EditText textViewCaptchaText;
    private CheckBox checkboxConsent;
    private CheckBox checkboxProxValidation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Instant start = Instant.now();


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
                    getView().findViewById(R.id.proximity_view).setBackgroundColor(getResources().getColor(color.gotcha_green));
                    Toast.makeText(getActivity(), "Proximity CAPTCHA validation successful!", Toast.LENGTH_LONG).show();
                    checkboxProxValidation.setChecked(true);
                } else {
                    // Nothing is nearby
                    Log.i("nothing nearby", "nothing nearby");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

        };


        // Input Fields check
        proximityFirstName = requireView().findViewById(R.id.proximityFirstName);
        proximityLastName = requireView().findViewById(R.id.proximityLastName);
        proximityEmail = requireView().findViewById(R.id.proximityEmail);
        checkboxConsent = requireView().findViewById(R.id.checkBoxConsent);
        checkboxProxValidation = requireView().findViewById(R.id.checkboxProxValidation);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mycaptcha-1e0f4-default-rtdb.europe-west1.firebasedatabase.app/");
        Log.i("User ID: ", String.valueOf(FirstFragment.getUserID()));
        DatabaseReference userRef = database.getReference("User ID:" + FirstFragment.getUserID());


        // Check if input fields are filled when pressing button
        binding.proximityNextButton.setOnClickListener(view1 -> {

        if(proximityFirstName.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "The first name field cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if(proximityLastName.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "The last name field cannot be empty", Toast.LENGTH_LONG).show();

        }
        else if(proximityEmail.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "The email field cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if(!checkboxConsent.isChecked()){
            Toast.makeText(getActivity(), "Please check the boxes first!", Toast.LENGTH_LONG).show();
        }
        else if(!checkboxProxValidation.isChecked()){
            Toast.makeText(getActivity(), "Please check the boxes first!", Toast.LENGTH_LONG).show();
        }
        else if(!checkboxProxValidation.isChecked()){
            Toast.makeText(getActivity(), "Please check the boxes first!", Toast.LENGTH_LONG).show();
        }
        else {
            long timeSpent = Duration.between(start, Instant.now()).getSeconds();

            Log.i("Proximity task duration: ", String.valueOf(timeSpent));

            userRef.child("Proximity Task duration").setValue(timeSpent);
            userRef.child("Proximity Task completed").setValue(true);


            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_SUSSecondFragment);
        }
    });

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