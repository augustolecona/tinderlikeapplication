package com.example.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowUser extends AppCompatActivity {



    DatabaseHelper myDatabaseHelper;

    private String selectedName;

    private TextView Gender,Name, Location, Email , Phone ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edituserslayout);
        Name = findViewById(R.id.namedisplayTv);
        Gender = findViewById(R.id.genderdisplayTv);
        Name = findViewById(R.id.namedisplayTv);
        Location = findViewById(R.id.locationdisplayTv);
        Email = findViewById(R.id.emaildisplayTv);
        Phone =findViewById(R.id.phonedisplayTv);

        Intent receivedIntent = getIntent();

        Name.setText(receivedIntent.getStringExtra("name"));
        Gender.setText(receivedIntent.getStringExtra("gender"));
        Location.setText(receivedIntent.getStringExtra("location"));
        Email.setText(receivedIntent.getStringExtra("email"));
        Phone.setText(receivedIntent.getStringExtra("phone"));

    }


}
