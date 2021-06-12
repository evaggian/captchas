package com.example.mycatcha;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mycatcha.databinding.FragmentFourthBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FourthFragment extends Fragment {
    private FragmentFourthBinding binding;
    private EditText age;
    private EditText comment;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFourthBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txt = (TextView) view.findViewById(R.id.textview_third);
        txt.setText("Your ID is:" + FirstFragment.getRandomID());

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mycaptcha-1e0f4-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference userRef = database.getReference("User ID:" + FirstFragment.getRandomID());

        age = requireView().findViewById(R.id.age_input);
        comment = requireView().findViewById(R.id.comment_input);

        binding.continueButton.setOnClickListener(view1 -> {

            RadioGroup group_question_gender = requireView().findViewById(R.id.question_gender_radio);
            RadioButton question_gender = requireView().findViewById(group_question_gender.getCheckedRadioButtonId());
            RadioGroup group_question_handedness = requireView().findViewById(R.id.question_handedness_radio);
            RadioButton question_handedness = requireView().findViewById(group_question_handedness.getCheckedRadioButtonId());

            if((question_gender == null) || (question_handedness == null) || age.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Please answer all questions!", Toast.LENGTH_LONG).show();
            }
            else{
                userRef.child("Gender").setValue(question_gender.getText());
                userRef.child("Age").setValue(age.getText().toString());
                userRef.child("Handedness").setValue(question_handedness.getText());
                userRef.child("Comment").setValue(comment.getText().toString());
                userRef.child("Completion").setValue(true);
                NavHostFragment.findNavController(FourthFragment.this)
                        .navigate(R.id.action_FourthFragment_to_ThankYouFragment);
            }
        });

//        binding.continueButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myRef.child("Completion").setValue(true);
//                NavHostFragment.findNavController(FourthFragment.this)
//                        .navigate(R.id.action_FourthFragment_to_ThankYouFragment);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
