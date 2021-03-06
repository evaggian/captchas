package com.example.mycatcha;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.navigation.fragment.NavHostFragment;

import com.example.mycatcha.databinding.FragmentThirdBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;

    private SensorManager sensorManager;
    private SensorEventListener rvListener;
    private Sensor rotationVectorSensor;

    private EditText rotationFirstName;
    private EditText rotationLastName;
    private EditText rotationEmail;
    private EditText textViewCaptchaText;
    private CheckBox checkboxConsent;
    private CheckBox checkboxRotValidation;

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

        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Instant start = Instant.now();

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


                if (orientations[2] > 170){
                    System.out.println("Congrats you succeeded!");
                    Toast.makeText(getActivity(),"Rotation CAPTCHA validation successful!", Toast.LENGTH_SHORT).show();
                    checkboxRotValidation.setChecked(true);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        // Register it
        sensorManager.registerListener(rvListener,
                rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);


        // Input Fields check
        rotationFirstName = requireView().findViewById(R.id.rotationFirstName);
        rotationLastName = requireView().findViewById(R.id.rotationLastName);
        rotationEmail = requireView().findViewById(R.id.rotationEmail);
        checkboxConsent = requireView().findViewById(R.id.checkBoxConsent);
        checkboxRotValidation = requireView().findViewById(R.id.checkboxRotValidation);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mycaptcha-1e0f4-default-rtdb.europe-west1.firebasedatabase.app/");
        Log.i("User ID: ", String.valueOf(FirstFragment.getUserID()));
        DatabaseReference userRef = database.getReference("User ID:" + FirstFragment.getUserID());


        // Check if input fields are filled when pressing button
        binding.rotationNextButton.setOnClickListener(view1 -> {

        if(rotationFirstName.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "The first name field cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if(rotationLastName.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "The last name field cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if(rotationEmail.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "The email field cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if(!checkboxConsent.isChecked()){
            Toast.makeText(getActivity(), "Please check the boxes first!", Toast.LENGTH_LONG).show();
        }
        else if(!checkboxRotValidation.isChecked()){
            Toast.makeText(getActivity(), "Please check the boxes first!", Toast.LENGTH_LONG).show();
        }
        else if(!checkboxRotValidation.isChecked()){
            Toast.makeText(getActivity(), "Please check the boxes first!", Toast.LENGTH_LONG).show();
        }
        else {
            long timeSpent = Duration.between(start, Instant.now()).getSeconds();

            Log.i("Rotation task duration: ", String.valueOf(timeSpent));

            userRef.child("Rotation Task duration").setValue(timeSpent);
            userRef.child("Rotation Task completed").setValue(true);


            NavHostFragment.findNavController(ThirdFragment.this)
                    .navigate(R.id.action_ThirdFragment_to_SUSThirdFragment);
        }
    });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
