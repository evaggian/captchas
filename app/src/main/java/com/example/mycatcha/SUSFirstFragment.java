package com.example.mycatcha;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mycatcha.databinding.FragmentSUSFirstBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SUSFirstFragment#newInstance} factory method to
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

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SUSFirstFragment.this)
                        .navigate(R.id.action_SUSFirstFragment_to_InstructionsSecondFragment);
            }
        });
    }

}