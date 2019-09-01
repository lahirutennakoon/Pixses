package com.example.pixsesapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pixsesapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUp extends AppCompatActivity {
    private DatabaseReference mDatabase;

    EditText name,email,password;
    String s_name, s_email,s_password;

    //a constant to track the file chooser intent
    private final int PICK_IMAGE_REQUEST = 234;

    //a Uri object to store file path
    private Uri filePath;

    //file extension of image
    private String fileExtension;

    //display selected image in ImageView
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        name=(EditText)findViewById(R.id.nameText);
        email=(EditText)findViewById(R.id.EmailText);
        password=(EditText)findViewById(R.id.pwText);

        mDatabase = FirebaseDatabase.getInstance().getReference("User");
    }

    // Open image gallery
    public void selectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    //set image thumbnail
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView = (ImageView)findViewById(R.id.userImage);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void OnSignup(View view){

       String s_name=name.getText().toString();
       String s_email=email.getText().toString();
       String s_password=password.getText().toString();

        System.out.println("details");
       System.out.println(s_name + " " + s_email + " " + s_password);

        final String id = mDatabase.push().getKey();

        System.out.println("id" + id);

        // Write a message to the database
        final User user = new User();
        user.setId(id);
        user.setImage("image");
        user.setName(s_name);
        user.setEmail(s_email);
        user.setPassword(s_password);

        /*String[] names = {"ui", "rt"};
        List nameList = new ArrayList<String>(Arrays.asList(names));
        user.setFriends(nameList);*/



        //if there is an image to upload
        if (filePath != null) {
         System.out.println("not null");

            //add image
            //reference to firebase storage (for multimedia data)
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

            //Create a storage reference from our app
            StorageReference storageReference = firebaseStorage.getReference();

            //file extension of image
            fileExtension = filePath.toString().substring(filePath.toString().lastIndexOf("."));

            // Create a child reference
            StorageReference childRef = storageReference.child("User/" + id);

            user.setImage(id);


            UploadTask uploadTask = childRef.putFile(filePath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    //write text data to database if image upload is successful
                    // databaseReference.push().setValue(car);

                    mDatabase.child(id).setValue(user);

                    Toast.makeText(SignUp.this, "New User Added!", Toast.LENGTH_LONG).show();
//                    progressBar.setVisibility(View.INVISIBLE);

                    //return back to admin panel after adding the car
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(SignUp.this, "Failed!", Toast.LENGTH_LONG).show();
//                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
        else {
            user.setImage("null");
        }
    }
    public void goToHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
