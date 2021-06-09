package com.example.mycatcha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView simpleImageView=(ImageView) findViewById(R.id.simpleImageView);
        simpleImageView.getLayoutParams().height = 300;
        simpleImageView.getLayoutParams().width = 300;

    }

    public void submitMainButtonHandler(View view) {
        Intent intent = new Intent(this, OCRActivity.class);
        startActivity(intent);
    }
}