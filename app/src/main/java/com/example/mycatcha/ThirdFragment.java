package com.example.mycatcha;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import java.util.Date;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mycatcha.databinding.FragmentSecondBinding;
import com.example.mycatcha.databinding.FragmentThirdBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;

    Button btn;
    EditText ans;
    public String value;

    private String cAnswer = "PQJRYD";
    private Date date = new Date();
    long start = (long) date.getTime();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = new MainActivity();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mycaptcha-1e0f4-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Random ID:" + MainActivity.getRandomID());
        myRef.child("Phone model:").setValue(MainActivity.getDeviceName());
        myRef.child("Phone resolution:").setValue(getScreenResolution());

        // OCR Captcha generation and check
        /*binding.nextButton.setEnabled(false);
        ans = binding.textView3;
        ans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals(cAnswer)){
                    Log.i("Answer is:", s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the user's response in the CAPTCHA field:
                ans = binding.textView3;
                value = ans.getText().toString();
                Log.i("User input:", value);

                // Check if answer is correct:
                if (value.contentEquals(cAnswer)) {
                    myRef.child("CAPTCHA Answer:").setValue(value);

                    //Get the time spent on this page:
                    Date date2 = new Date();
                    long end = (long) date2.getTime();
                    long timeSpent = end - start;
                    myRef.child("OCR Captcha time spent:").setValue(timeSpent);

                    NavHostFragment.findNavController(ThirdFragment.this)
                            .navigate(R.id.action_ThirdFragment_to_FourthFragment);
                } else {
                    Toast.makeText(getActivity(), "Wrong CAPTCHA answer! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String getScreenResolution() {
        int width = getResources().getConfiguration().screenWidthDp;
        int height = getResources().getConfiguration().screenHeightDp;
        int density = getResources().getDisplayMetrics().densityDpi;
        return "{" + width + "," + height + "," + density + "}";
    }
}
