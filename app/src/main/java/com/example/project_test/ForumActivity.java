package com.example.project_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class ForumActivity extends AppCompatActivity {

    EditText nameTopic, chooseTopic;
    Button saveTopic, goTopics;

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
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
    }
}