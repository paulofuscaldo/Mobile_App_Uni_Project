package com.example.project_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.squareup.picasso.Picasso;

public class StreetList extends AppCompatActivity  {



    ImageView iv;
    Button save_image;



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

            case R.id.Restaurant:
            startActivity(new Intent(this, RestaurantList.class));
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
        setContentView(R.layout.activity_street_list);




        //- Find Image
        iv = findViewById(R.id.upload_image);
        //- Button to save the image
        save_image = findViewById(R.id.bt_save_image);

        //- Download the image
       iv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent();
               i.setType("image/*");
               i.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(i, 101);
           }
       });



    }

    //- control O - type - onActivity 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode == RESULT_OK && data.getData()!=null)
        {
            Picasso.get().load(data.getData()).fit().into(iv);
        }
    }


}