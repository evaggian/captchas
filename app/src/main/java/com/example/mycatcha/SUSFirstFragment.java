package com.example.mycatcha;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mycatcha.databinding.FragmentSUSFirstBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class SUSFirstFragment extends Fragment {

    private FragmentSUSFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = com.example.mycatcha.databinding.FragmentSUSFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.continueButton.setOnClickListener(view1 -> {

            RadioGroup group_question_ocr_1 = requireView().findViewById(R.id.question_1_radio);
            RadioButton question_ocr_1 = requireView().findViewById(group_question_ocr_1.getCheckedRadioButtonId());

            RadioGroup group_question_ocr_2 = requireView().findViewById(R.id.question_2_radio);
            RadioButton question_ocr_2 = requireView().findViewById(group_question_ocr_2.getCheckedRadioButtonId());

            RadioGroup group_question_ocr_3 = requireView().findViewById(R.id.question_3_radio);
            RadioButton question_ocr_3 = requireView().findViewById(group_question_ocr_3.getCheckedRadioButtonId());

            RadioGroup group_question_ocr_4 = requireView().findViewById(R.id.question_4_radio);
            RadioButton question_ocr_4 = requireView().findViewById(group_question_ocr_4.getCheckedRadioButtonId());

            RadioGroup group_question_ocr_5 = requireView().findViewById(R.id.question_5_radio);
            RadioButton question_ocr_5 = requireView().findViewById(group_question_ocr_5.getCheckedRadioButtonId());

            RadioGroup group_question_ocr_6 = requireView().findViewById(R.id.question_6_radio);
            RadioButton question_ocr_6 = requireView().findViewById(group_question_ocr_6.getCheckedRadioButtonId());

            RadioGroup group_question_ocr_7 = requireView().findViewById(R.id.question_7_radio);
            RadioButton question_ocr_7 = requireView().findViewById(group_question_ocr_7.getCheckedRadioButtonId());

            RadioGroup group_question_ocr_8 = requireView().findViewById(R.id.question_8_radio);
            RadioButton question_ocr_8 = requireView().findViewById(group_question_ocr_8.getCheckedRadioButtonId());

            RadioGroup group_question_ocr_9 = requireView().findViewById(R.id.question_9_radio);
            RadioButton question_ocr_9 = requireView().findViewById(group_question_ocr_9.getCheckedRadioButtonId());

            RadioGroup group_question_ocr_10 = requireView().findViewById(R.id.question_10_radio);
            RadioButton question_ocr_10 = requireView().findViewById(group_question_ocr_10.getCheckedRadioButtonId());

            if((question_ocr_1 == null) || (question_ocr_2 == null) ||
                    (question_ocr_3 == null) || (question_ocr_4 == null) ||
                    (question_ocr_5 == null) || (question_ocr_6 == null) ||
                    (question_ocr_7 == null) || (question_ocr_8 == null) ||
                    (question_ocr_9 == null) || (question_ocr_10 == null)){
                Toast.makeText(getActivity(), "Please answer all questions!", Toast.LENGTH_LONG).show();
            }
            else{
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://mycaptcha-1e0f4-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference userRef = database.getReference("User ID:" + FirstFragment.getRandomID());

                userRef.child("OCR_question_1").setValue(question_ocr_1.getText());
                userRef.child("OCR_question_2").setValue(question_ocr_2.getText());
                userRef.child("OCR_question_3").setValue(question_ocr_3.getText());
                userRef.child("OCR_question_4").setValue(question_ocr_4.getText());
                userRef.child("OCR_question_5").setValue(question_ocr_5.getText());
                userRef.child("OCR_question_6").setValue(question_ocr_6.getText());
                userRef.child("OCR_question_7").setValue(question_ocr_7.getText());
                userRef.child("OCR_question_8").setValue(question_ocr_8.getText());
                userRef.child("OCR_question_9").setValue(question_ocr_9.getText());
                userRef.child("OCR_question_10").setValue(question_ocr_10.getText());


                NavHostFragment.findNavController(SUSFirstFragment.this)
                        .navigate(R.id.action_SUSFirstFragment_to_InstructionsSecondFragment);
            }
        });
    }

}