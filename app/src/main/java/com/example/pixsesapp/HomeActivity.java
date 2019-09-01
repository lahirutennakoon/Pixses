package com.example.pixsesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pixsesapp.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    //reference to firebase database (for textual data)
    private DatabaseReference databaseReference;

    private ListView listView;

    private ArrayList<String> users = new ArrayList<>();

    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void searchFriends(View view) {
        System.out.println("openGallery");

        users.clear();

        // Get an instance of DatabaseReference to read from table "User" in db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        search = (EditText) findViewById(R.id.editTextSearch);
        String searchString = search.getText().toString().trim();
        System.out.println("searchString: " + searchString);
        Query query = databaseReference.orderByChild("email").equalTo(searchString);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (final DataSnapshot user: snapshot.getChildren()) {
                    System.out.println("userId: " + user.child("id"));
                    final String userEmail = user.child("email").getValue().toString();
                    System.out.println("userEmail: " + user.child("email").getValue());
                    System.out.println("name: " + user.child("name"));

                    listView = (ListView) findViewById(R.id.userList);

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, users);

                    //joining the array adapter to the listview
                    listView.setAdapter(arrayAdapter);

                    databaseReference.addChildEventListener(new ChildEventListener()
                    {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s)
                        {
                            /*User user = (User) dataSnapshot.getValue(User.class);
                            String email = null;
                            if (user != null)
                            {
                                email = user.getEmail();
                            }

                            //add value to arraylist
                            users.add(email);*/
                            if (!users.contains(userEmail)) {
                                users.add(userEmail);
                            }

                            arrayAdapter.notifyDataSetChanged();

//                            System.out.println("User " + user.getEmail());
                            System.out.println("User " + userEmail);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s)
                        {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot)
                        {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s)
                        {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });

                    // onclick event for a list item
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                        {
                            System.out.println("qwerty");

                            String[] names = {userEmail};
                            List nameList = new ArrayList<String>(Arrays.asList(names));

                            // Now set value with new nameList
//                            databaseReference.setValue(nameList);
                            user.getRef().child("friends").setValue(nameList);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("error:" + databaseError);
            }
        });


        // test
       /* listView = (ListView) findViewById(R.id.userList);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);

        //joining the array adapter to the listview
        listView.setAdapter(arrayAdapter);*/

        //display the values from firebase database in the listview
        /*databaseReference.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                User user = (User) dataSnapshot.getValue(User.class);
                String email = null;
                if (user != null)
                {
                    email = user.getEmail();
                }

                //add value to arraylist
                users.add(email);

                arrayAdapter.notifyDataSetChanged();

                System.out.println("User " + user.getEmail());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        //onclick event for a list item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                System.out.println("qwerty");
                *//*Intent intent = new Intent(MainActivity.this, CarDetailsActivity.class);
                intent.putExtra("model", ((TextView)view.findViewById(android.R.id.text1)).getText());
                startActivity(intent);*//*
            }
        });*/

        System.out.println("done");
    }
}
