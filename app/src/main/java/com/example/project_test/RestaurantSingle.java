package com.example.project_test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantSingle extends AppCompatActivity {

    ImageView mainImageView;
    TextView title, description;


    String data1, data2;
    int restaurantImageView;
    Button rating, reviewBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_single);

        findViewById(R.id.opentable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn("https://www.opentable.com/r/kutir-chelsea-london-2?corrid=8adea7cb-dd3c-462c-903f-cba115187d15&avt=eyJ2IjoyLCJtIjowLCJwIjowLCJzIjowLCJuIjowfQ&p=2&sd=2021-11-21T23%3A00%3A00");
            }
        });


       rating = (Button) findViewById(R.id.btn_rating);
       rating.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(RestaurantSingle.this,Rating.class);
               startActivity(intent);
           }

       });
        reviewBtn = (Button) findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantSingle.this, Review.class);
                startActivity(intent);

            }
        });

         mainImageView = findViewById(R.id.mainImageView);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);


        getData();
        setData();

    }
    public void clicked_btn(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }



    private void getData() {
        if(getIntent().hasExtra("restaurantImageView") && getIntent().hasExtra("data1") &&
        getIntent().hasExtra("data2")){

            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            restaurantImageView = getIntent().getIntExtra("restaurantImageView", 1);

        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData() {
        title.setText(data1);
        description.setText(data2);
        mainImageView.setImageResource(restaurantImageView);


    }

}