package com.example.mycatcha;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SUSThirdFragment extends Fragment {

    private com.example.mycatcha.databinding.FragmentSUSThirdBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = com.example.mycatcha.databinding.FragmentSUSThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.continueButtonSus3.setOnClickListener(view1 -> {

            RadioGroup group_question_rotation_1 = requireView().findViewById(R.id.question_1_radio_sus3);
            RadioButton question_rotation_1 = requireView().findViewById(group_question_rotation_1.getCheckedRadioButtonId());

            RadioGroup group_question_rotation_2 = requireView().findViewById(R.id.question_2_radio_sus3);
            RadioButton question_rotation_2 = requireView().findViewById(group_question_rotation_2.getCheckedRadioButtonId());

            RadioGroup group_question_rotation_3 = requireView().findViewById(R.id.question_3_radio_sus3);
            RadioButton question_rotation_3 = requireView().findViewById(group_question_rotation_3.getCheckedRadioButtonId());

            RadioGroup group_question_rotation_4 = requireView().findViewById(R.id.question_4_radio_sus3);
            RadioButton question_rotation_4 = requireView().findViewById(group_question_rotation_4.getCheckedRadioButtonId());

            RadioGroup group_question_rotation_5 = requireView().findViewById(R.id.question_5_radio_sus3);
            RadioButton question_rotation_5 = requireView().findViewById(group_question_rotation_5.getCheckedRadioButtonId());

            RadioGroup group_question_rotation_6 = requireView().findViewById(R.id.question_6_radio_sus3);
            RadioButton question_rotation_6 = requireView().findViewById(group_question_rotation_6.getCheckedRadioButtonId());

            RadioGroup group_question_rotation_7 = requireView().findViewById(R.id.question_7_radio_sus3);
            RadioButton question_rotation_7 = requireView().findViewById(group_question_rotation_7.getCheckedRadioButtonId());

            RadioGroup group_question_rotation_8 = requireView().findViewById(R.id.question_8_radio_sus3);
            RadioButton question_rotation_8 = requireView().findViewById(group_question_rotation_8.getCheckedRadioButtonId());

            RadioGroup group_question_rotation_9 = requireView().findViewById(R.id.question_9_radio_sus3);
            RadioButton question_rotation_9 = requireView().findViewById(group_question_rotation_9.getCheckedRadioButtonId());

            RadioGroup group_question_rotation_10 = requireView().findViewById(R.id.question_10_radio_sus3);
            RadioButton question_rotation_10 = requireView().findViewById(group_question_rotation_10.getCheckedRadioButtonId());

            if((question_rotation_1 == null) || (question_rotation_2 == null) ||
                    (question_rotation_3 == null) || (question_rotation_4 == null) ||
                    (question_rotation_5 == null) || (question_rotation_6 == null) ||
                    (question_rotation_7 == null) || (question_rotation_8 == null) ||
                    (question_rotation_9 == null) || (question_rotation_10 == null)){
                Toast.makeText(getActivity(), "Please answer all questions!", Toast.LENGTH_LONG).show();
            }
            else{
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://mycaptcha-1e0f4-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference userRef = database.getReference("User ID:" + FirstFragment.getRandomID());

                userRef.child("Rotation_question_1").setValue(question_rotation_1.getText());
                userRef.child("Rotation_question_2").setValue(question_rotation_2.getText());
                userRef.child("Rotation_question_3").setValue(question_rotation_3.getText());
                userRef.child("Rotation_question_4").setValue(question_rotation_4.getText());
                userRef.child("Rotation_question_5").setValue(question_rotation_5.getText());
                userRef.child("Rotation_question_6").setValue(question_rotation_6.getText());
                userRef.child("Rotation_question_7").setValue(question_rotation_7.getText());
                userRef.child("Rotation_question_8").setValue(question_rotation_8.getText());
                userRef.child("Rotation_question_9").setValue(question_rotation_9.getText());
                userRef.child("Rotation_question_10").setValue(question_rotation_10.getText());


                NavHostFragment.findNavController(SUSThirdFragment.this)
                        .navigate(R.id.action_SUSThirdFragment_to_FourthFragment);
            }
        });
    }
}