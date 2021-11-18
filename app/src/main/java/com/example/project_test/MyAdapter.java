package com.example.project_test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String data1[], data2[];
    int images[];
    Context context;

    public MyAdapter(Context ct,String s1[],String s2[], int img[]){
       context = ct;
       data1 = s1;
       data2 = s2;
       images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.text1.setText(data1[position]);
        holder.text2.setText(data2[position]);
        holder.restaurantImageView.setImageResource(images[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantSingle.class);
                intent.putExtra("data1", data1[position]);
                intent.putExtra("data2", data2[position]);
                intent.putExtra("restaurantImageView", images[position]);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView text1, text2;
       ImageView restaurantImageView, gradient;
       ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            restaurantImageView = itemView.findViewById(R.id.restaurantImageView);
            gradient = itemView.findViewById(R.id.colour_gradient);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
