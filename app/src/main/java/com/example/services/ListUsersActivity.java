package com.example.services;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListUsersActivity extends AppCompatActivity {

    private static final String TAG ="ListDataAactivity";
    DatabaseHelper myDataBaseHelper;
    private ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        listview= findViewById(R.id.list);
        myDataBaseHelper =new DatabaseHelper(this);
        populateListView ();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: fetching users data to ListView");
        Cursor dataofUsers = myDataBaseHelper.getUsers();
        ArrayList<String> listUsersname = new ArrayList<String>();
        while (dataofUsers.moveToNext()){
            String name = dataofUsers.getString(1);
            listUsersname.add(name);
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listUsersname);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "UserID: " + name );
                Cursor data = myDataBaseHelper.getUserData(name);
                String [] userdata = new String[5];
                while(data.moveToNext()){
                    userdata[0] = data.getString(1);
                    userdata[1] = data.getString(2);
                    userdata[2] = data.getString(3);
                    userdata[3] = data.getString(4);
                    userdata[4] = data.getString(5);

                }

                    Intent editScreenIntent = new Intent( ListUsersActivity.this, ShowUser.class);
                    editScreenIntent.putExtra("name", userdata[0]);
                    editScreenIntent.putExtra("gender", userdata[1]);
                    editScreenIntent.putExtra("location", userdata[2]);
                    editScreenIntent.putExtra("email", userdata[3]);
                    editScreenIntent.putExtra("phone", userdata[4]);
                    startActivity(editScreenIntent);
            }
        });
    }

    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}
