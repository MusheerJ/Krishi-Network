package com.internship.krishinetwork;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.internship.krishinetwork.databinding.ActivityFormBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormActivity extends AppCompatActivity {

    ActivityFormBinding binding;
    Uri clickedImage;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Fill Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Submitting form please wait ...");

        binding.name.requestFocus();

        binding.imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(FormActivity.this)
                        .cameraOnly()
                        .start();
            }
        });


        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    binding.email.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().toString().isEmpty()) {
                    binding.name.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = binding.name.getText().toString();
                String email = binding.email.getText().toString();

                if (name.isEmpty()) {
                    binding.name.setError("This cant be empty !");
                    return;
                }
                if (email.isEmpty()) {
                    binding.email.setError("This cant be empty !");
                    return;
                }

                if (!isEmailValid(email)) {
                    binding.email.setError("Invalid Email");
                    return;
                }

                if (clickedImage == null) {
                    Toast.makeText(FormActivity.this, "Please upload a picture", Toast.LENGTH_SHORT).show();
                    return;
                }


                dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(FormActivity.this, ShowFormActivity.class);
                        intent.putExtra("imageUri", clickedImage.toString());
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        resetEditText();
                        dialog.dismiss();
                    }
                }, 1000);

            }
        });
    }

    private void resetEditText() {
        binding.email.setError(null);
        binding.name.setError(null);
        binding.name.setText(null);
        binding.email.setText(null);
        binding.ProfileSetUp.setImageResource(R.drawable.avatar);
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            clickedImage = data.getData();
            binding.ProfileSetUp.setImageURI(clickedImage);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.name.requestFocus();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}