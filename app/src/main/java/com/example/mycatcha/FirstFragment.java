package com.example.mycatcha;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public int userID;

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

        binding.nextButton.setOnClickListener(view1 -> {

            long timeSpent = Duration.between(start, Instant.now()).getSeconds();

            Log.i("OCR task duration: ", String.valueOf(timeSpent));

            userRef.child("OCR Task duration").setValue(timeSpent);
            userRef.child("OCR Task completed").setValue(1);


            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SUSFirstFragment);
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

}