package com.example.pixsesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

   Button button;
   Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    getSupportActionBar().hide();

        button=(Button)findViewById(R.id.LogInBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, login.class);
                startActivity(i);
                openlogin();

            }
        });

        button1=(Button)findViewById(R.id.lgnbtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent u=new Intent(MainActivity.this, SignUp.class);
                startActivity(u);
                openSignUp();
            }
        });
    }

        public void openlogin(){
            Intent intent=new Intent(this, com.example.pixsesapp.login.class);
            startActivity(intent);

    }
    public void openSignUp(){
        Intent intent=new Intent(this, com.example.pixsesapp.SignUp.class);
        startActivity(intent);

    }
}
