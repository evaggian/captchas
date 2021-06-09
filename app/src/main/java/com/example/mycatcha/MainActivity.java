package com.example.mycatcha;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView simpleImageView=(ImageView) findViewById(R.id.simpleImageView);
        simpleImageView.getLayoutParams().height = 300;
        simpleImageView.getLayoutParams().width = 300;

//        Logging the phone model in the console.


        // Logging the phone's height and width:
        //Log.i("Screen width & height:", getScreenResolution());
    }

    public void submitMainButtonHandler(View view) {
        Intent intent = new Intent(this, OCRActivity.class);
        startActivity(intent);
    }

//    Retrieving the device manufacturer and name.
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    //    Capitalizing the first letter of the model or manufacturer.
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
    public static String getRandomID(){
        Log.i("Random:", String.valueOf(random));
        return String.valueOf(random);
    }
}