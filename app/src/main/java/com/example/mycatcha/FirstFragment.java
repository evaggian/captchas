package com.example.mycatcha;

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

import com.example.mycatcha.databinding.FragmentFirstBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private EditText ocrName;
    private EditText ocrLastName;
    private EditText ocrEmail;
    private EditText textViewCaptchaText;
    private CheckBox checkboxConsent;
    private CheckBox checkboxOCRValidation;

    public static int userID;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Instant start = Instant.now();

        ocrName = requireView().findViewById(R.id.ocrFirstName);
        ocrLastName = requireView().findViewById(R.id.ocrLastName);
        ocrEmail = requireView().findViewById(R.id.ocrEmail);
        checkboxConsent = requireView().findViewById(R.id.checkBoxConsent);
        checkboxOCRValidation = requireView().findViewById(R.id.checkboxOCRValidation);
        textViewCaptchaText = requireView().findViewById(R.id.textViewCaptchaText);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mycaptcha-1e0f4-default-rtdb.europe-west1.firebasedatabase.app/");

        userID = getRandomID();
        String phoneModel = getPhoneModel();
        String phoneResolution = getScreenResolution();

        Log.i("User ID: ", String.valueOf(userID));
        Log.i("Phone model: ", phoneModel);
        Log.i("Phone resolution: ", phoneResolution);

        DatabaseReference userRef = database.getReference("User ID:" + userID);
        userRef.child("Phone model").setValue(phoneModel);
        userRef.child("Phone resolution").setValue(phoneResolution);

        binding.ocrNextButton.setOnClickListener(view1 -> {
            if(ocrName.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "The first name field cannot be empty", Toast.LENGTH_LONG).show();
            }
            else if(ocrLastName.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "The last name field cannot be empty", Toast.LENGTH_LONG).show();
            }
            else if(ocrEmail.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "The email field cannot be empty", Toast.LENGTH_LONG).show();
            }
            else if(!checkboxConsent.isChecked()){
                Toast.makeText(getActivity(), "Please check the box first!", Toast.LENGTH_LONG).show();
            }
//            else if(!checkboxOCRValidation.isChecked()){
//                Toast.makeText(getActivity(), "Please check the boxes first!", Toast.LENGTH_LONG).show();
//            }
//            else if(!checkboxOCRValidation.isChecked()){
//                Toast.makeText(getActivity(), "Please check the boxes first!", Toast.LENGTH_LONG).show();
//            }
            else if(textViewCaptchaText.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Please fill in the captcha text!", Toast.LENGTH_LONG).show();
            }
            else if(!textViewCaptchaText.getText().toString().matches("PQJRYD")){
                Toast.makeText(getActivity(), "The captcha you entered is wrong. Try again", Toast.LENGTH_LONG).show();
            }
            else {

                checkboxOCRValidation.setChecked(true);

                long timeSpent = Duration.between(start, Instant.now()).getSeconds();

                Log.i("OCR task duration: ", String.valueOf(timeSpent));

                userRef.child("OCR Task duration").setValue(timeSpent);
                userRef.child("OCR Task completed").setValue(true);


                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SUSFirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // Retrieving the device manufacturer and name.
    public static String getPhoneModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    // Capitalizing the first letter of the model or manufacturer.
    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    static int random = new Random().nextInt(6000);
    public static int getRandomID(){
        Log.i("Random: ", String.valueOf(random));
        return random;
    }

    public String getScreenResolution() {
        int width = getResources().getConfiguration().screenWidthDp;
        int height = getResources().getConfiguration().screenHeightDp;
        int density = getResources().getDisplayMetrics().densityDpi;
        return "{" + width + "," + height + "," + density + "}";
    }

    public static int getUserID(){
        return userID;
    }

}