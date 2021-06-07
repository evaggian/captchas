package com.example.mycatcha;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import com.example.mycatcha.databinding.ActivityMain4Binding;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class TextBasedCaptchaActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMain4Binding binding;

    ImageView im;
    Button btn;
    TextView ans;

    private String cAnswer = "PQJRYD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // OCR Captcha generation and check
        im = (ImageView)findViewById(R.id.imageView3);
        btn = (Button)findViewById(R.id.button_first);
        ans = (EditText)findViewById(R.id.textView3);

        int image = getIntent().getIntExtra("image",R.drawable.images);
        im.setImageResource(image);

        btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                if (ans.toString() == cAnswer) {
                    btn.setEnabled(true);
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
