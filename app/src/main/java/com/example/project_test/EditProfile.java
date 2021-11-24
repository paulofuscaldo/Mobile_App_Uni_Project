package com.example.project_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class EditProfile extends AppCompatActivity {

    private Button btnSaveData;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_edit_profile);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        btnSaveData = findViewById(R.id.btn_edit_save_profile);

        final View userPicture = findViewById(R.id.container_user);
        final EditText userFirstName = findViewById(R.id.edit_fn_profile);
        final EditText userSurname = findViewById(R.id.edit_sn_profile);
        final EditText userEmail = findViewById(R.id.edit_em_profile);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String userName = userProfile.firstname;
                    String surname = userProfile.surname;
                    String email = userProfile.email;


                    userFirstName.setText(userName);
                    userSurname.setText(surname);
                    userEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fName = userFirstName.getText().toString();
                String sName = userSurname.getText().toString();
                String email = userEmail.getText().toString();

                updateData(fName, sName, email);
            }
        });

    }

    private void updateData(String fName, String sName, String email ){

        HashMap User = new HashMap();
        User.put("firstname", fName);
        User.put("surname", sName);
        User.put("email", email);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userID).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){

                    Toast.makeText(EditProfile.this, "Succesfully Updated", Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(EditProfile.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}