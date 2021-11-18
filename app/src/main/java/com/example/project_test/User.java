package com.example.project_test;



import com.google.firebase.database.DatabaseReference;


public class User {

    public  String firstname, surname, email;

    public User(){

    }
    DatabaseReference reference;

    public User(String firstname, String surname, String email){
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        
    }

}

   // public void updateProfile(View view){
   //     if(isFirstNameChanged() || isSurNameChanged() || isEmailChanged() || isPasswordCahnged()){







