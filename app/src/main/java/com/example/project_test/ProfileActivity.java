package com.example.project_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button logout;
    //Create button to call the edit profile screen
    private Button btnCallEditProfile;



    // Menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.Main_Menu:
                startActivity(new Intent(this, MainMenuActivity.class));
                break;

            case R.id.Restaurant:
                startActivity(new Intent(this, RestaurantList.class));
                break;

            case R.id.StreetFood:
                startActivity(new Intent(this, StreetList.class));
                break;

            case R.id.Logout:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.Forum:
                startActivity(new Intent(this, ForumActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar();

        //Assign to btnCallEditProfile the button id
        btnCallEditProfile = findViewById(R.id.btn_call_edit_profile);

        //Created an intent to call the edit profile screen
        btnCallEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, EditProfile.class);
                startActivity(i);
            }
        });

        logout = (Button) findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView firstnameTextView = (TextView)  findViewById(R.id.firstname);
        final TextView surnameTextView = (TextView) findViewById(R.id.surname);
        final TextView emailTextView = (TextView)  findViewById(R.id.email);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String firstname = userProfile.firstname;
                    String surname = userProfile.surname;
                    String email = userProfile.email;


                    firstnameTextView.setText(firstname);
                    surnameTextView.setText(surname);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });



    }


}