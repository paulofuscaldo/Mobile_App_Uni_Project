package com.example.project_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser;
    private EditText editTextFirstName,editTextSurName, editTextEmail, editTextPassWord;
    private ProgressBar progressBar;
    private Button back;
    private CheckBox showpassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFirstName = (EditText) findViewById(R.id.firstname);
        editTextSurName = (EditText) findViewById(R.id.surname);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassWord = (EditText) findViewById(R.id.password);
        showpassword = (CheckBox) findViewById(R.id.showpassword);

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    editTextPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    editTextPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
            case R.id.btn_back:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }

    private void registerUser() {
        String firstname = editTextFirstName.getText().toString().trim();
        String surname = editTextSurName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassWord.getText().toString().trim();

        if (firstname.isEmpty()) {
            editTextFirstName.setError("This field is required!");
            editTextFirstName.requestFocus();
            return;
        }
        if (surname.isEmpty()) {
            editTextSurName.setError("This field is required!");
            editTextSurName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("This field is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid Email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassWord.setError("PassWord is required");
            editTextPassWord.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassWord.setError("Min password length should be 6 characters!");
            editTextPassWord.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User (firstname, surname, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this, "Confirm your email" , Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);

                                        // redirect to login layout!

                                    } else {
                                        Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
