package com.internship.krishinetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.internship.krishinetwork.databinding.ActivityShowFormBinding;

public class ShowFormActivity extends AppCompatActivity {

    ActivityShowFormBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Submitted Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Uri uri = Uri.parse(getIntent().getStringExtra("imageUri"));
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");


        binding.clickedImage.setImageURI(uri);
        binding.resultName.setText(name);
        binding.resultEmail.setText(email);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}