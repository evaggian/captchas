package com.example.mycatcha;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycatcha.databinding.FragmentInstructionsSecondBinding;
import com.example.mycatcha.databinding.FragmentInstructionsThirdBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstructionsThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructionsThirdFragment extends Fragment {

    private FragmentInstructionsThirdBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = com.example.mycatcha.databinding.FragmentInstructionsThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InstructionsThirdFragment.this)
                        .navigate(R.id.action_InstructionsThirdFragment_to_ThirdFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}