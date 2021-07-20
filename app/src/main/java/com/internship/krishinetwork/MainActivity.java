package com.internship.krishinetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.internship.krishinetwork.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.StreamVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Going to the Video Streaming Activity
                startActivity(new Intent(MainActivity.this, StreamVideoActivity.class));
            }
        });

        binding.formFillUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Going to the Form Activity
                startActivity(new Intent(MainActivity.this,FormActivity.class ));
            }
        });
    }
}