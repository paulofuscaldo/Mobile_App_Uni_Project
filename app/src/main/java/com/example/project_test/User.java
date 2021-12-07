package com.example.project_test;



import com.google.firebase.database.DatabaseReference;


public class User {

    public  String firstname, surname, email, type;

    public User(){

    }


    public User(String firstname, String surname, String email, String type) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.type = type;
    }
}

   // public void updateProfile(View view){
   //     if(isFirstNameChanged() || isSurNameChanged() || isEmailChanged() || isPasswordCahnged()){







