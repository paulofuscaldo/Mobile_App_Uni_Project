package com.example.project_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RestaurantList extends AppCompatActivity  {


   // Alphabetic order
    String s1[], s2[];
    int images[] = {R.drawable.chicama,R.drawable.el_north,R.drawable.kurtir,
            R.drawable.manthan,R.drawable.piatto,R.drawable.the_ninth,R.drawable.vardo};
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

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        recyclerView = findViewById(R.id.recyclerView);


      s1 = getResources().getStringArray(R.array.restaurant_list);
      s2 = getResources().getStringArray(R.array.description);


      MyAdapter myAdapter = new MyAdapter(this, s1, s2, images);
      recyclerView.setAdapter(myAdapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



}