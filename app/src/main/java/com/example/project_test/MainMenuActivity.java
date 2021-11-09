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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button restaurantList, streetList;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Profile:
                startActivity(new Intent(this, ProfileActivity.class));
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
        setContentView(R.layout.activity_main_menu);


       //- greetings
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView firstnameTextView = (TextView)  findViewById(R.id.firstname);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String firstname = userProfile.firstname;
                    greetingTextView.setText("Welcome, " + firstname + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }});
        //---

        restaurantList = (Button) findViewById(R.id.btn_restaurant_list);
        restaurantList.setOnClickListener(this);

        streetList = (Button) findViewById(R.id.btn_street_list);
        streetList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_restaurant_list:
                startActivity(new Intent(this, RestaurantList.class));
                break;
            case R.id.btn_street_list:
                startActivity(new Intent(this, StreetList.class));
        }
    }
    }
