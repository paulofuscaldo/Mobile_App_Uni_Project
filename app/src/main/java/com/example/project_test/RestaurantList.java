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

public class RestaurantList extends AppCompatActivity implements View.OnClickListener {

    private Button back;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.Profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;

            case R.id.StreetFood:
            startActivity(new Intent(this, StreetList.class));
            break;

            case R.id.Main_Menu:
                startActivity(new Intent(this, MainMenuActivity.class));
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
        setContentView(R.layout.activity_restaurant_list);







        back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }
}