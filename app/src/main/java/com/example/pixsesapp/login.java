package com.example.pixsesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    EditText email;
    EditText password;
    Button button;
    Button fpbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        fpbtn = findViewById(R.id.fpbtn);
        fpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, facedetect.class);
                startActivity(i);
            }
        });

        button=(Button)findViewById(R.id.LogInBtn);



        email = (EditText) findViewById(R.id.EnterEmailText);
        password = (EditText) findViewById((R.id.EnterPasswordText));

    }
    public void btnlgn(View view) {

      String useremail = email.getText().toString();
        String userpassword = password.getText().toString();
        String type = "login";

        System.out.println("username: " + useremail);
        System.out.println("password: " + userpassword);

        LogedUsers logedUsers = new LogedUsers(this);
        logedUsers.execute(type, useremail, userpassword);


        //button.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {
              //  Intent i = new Intent(login.this, facedetect.class);
                //startActivity(i);
                //openfacedetect();

            //}
        //});
    }
          //  public void openfacedetect(){
            // Intent intent=new Intent(this,facedetect.class);
             //startActivity(intent);

    //}

    public void goToHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}