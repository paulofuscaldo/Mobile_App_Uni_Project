package com.example.project_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Review extends AppCompatActivity {

    private EditText reviewEt;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewEt = findViewById(R.id.reviewEt);
        submitBtn = findViewById(R.id.btn_submit_review);
    }
}