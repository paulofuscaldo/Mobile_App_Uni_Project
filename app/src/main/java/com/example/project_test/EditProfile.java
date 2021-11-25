package com.example.project_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class EditProfile extends AppCompatActivity {

    private Button btnSaveData;
    private ImageButton btnSaveImage;
    private ImageView selectImg;
    private FirebaseUser user;
    private DatabaseReference reference;
    private StorageReference storageRef;
    private String userID;
    private static final int PICK_IMAGE_REQUEST = 1;

    Uri imageUri;
    ProgressDialog progressDialog;

//////////////////////MENU INSTANCE/////////////////////////////
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

    ////////////////////////END MENU INSTANCE//////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_edit_profile);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        selectImg = findViewById(R.id.img_placeholder);
        btnSaveData = findViewById(R.id.btn_edit_save_profile);

        //final View userPicture = findViewById(R.id.img_placeholder);
        final EditText userFirstName = findViewById(R.id.edit_fn_profile);
        final EditText userSurname = findViewById(R.id.edit_sn_profile);
        final EditText userEmail = findViewById(R.id.edit_em_profile);

        //Method to select the profile image
        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImg();

            }
        });

        //Method to save the image selected
        btnSaveImage = findViewById(R.id.btnSaveImg);
        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImg();
            }
        });

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

    //Select Image

    private void selectImg(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageUri = data.getData();
            Picasso.get().load(imageUri).into(selectImg);
            selectImg.setImageURI(imageUri);

        }
    }

    //Method saveImg
    private void saveImg(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Picture...");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.UK);
        Date now = new Date();
        String fileName = formatter.format(now);

        storageRef = FirebaseStorage.getInstance().getReference("images/"+fileName);

        storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                selectImg.setImageURI(null);
                Toast.makeText(EditProfile.this, "Image successfully uploaded!", Toast.LENGTH_SHORT).show();
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(EditProfile.this, "Fail to Upload.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Update data of String type
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