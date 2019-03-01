package com.example.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.services.api.RandomAPI;
import com.example.services.models.ApiResponse;
import com.example.services.utils.NetworkHelper;
import com.example.services.models.*;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class MainActivity extends AppCompatActivity {

    private OkHttpClient okHttpClient;
    private RandomAPI randomAPI;
    private TextView Gender,Name, Location, Email , Phone ;
    private Button btnSave, btnShow;
    DatabaseHelper  myDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gender = findViewById(R.id.genderdisplayTv);
        Name = findViewById(R.id.namedisplayTv);
        Location = findViewById(R.id.locationdisplayTv);
        Email = findViewById(R.id.emaildisplayTv);
        Phone =findViewById(R.id.phonedisplayTv);
        okHttpClient = new OkHttpClient();
        randomAPI = NetworkHelper.createRandomAPI();

        btnSave = findViewById(R.id.savebtn);
        btnShow= findViewById(R.id.showbtn);

        myDataBaseHelper= new DatabaseHelper(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUserName = Name.getText().toString();
                String newUserGender = Gender.getText().toString();
                String newUserLocation = Location.getText().toString();
                String newUSserEamil = Email.getText().toString();
                String newUserPhone = Phone.getText().toString();
                if(Name.length() !=0 ){
                    String[] userdata ={newUserName, newUserGender, newUserLocation,newUSserEamil,newUserPhone};
                    SaveUser( userdata);
                }else {
                    toastMessage("You must write a name");
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListUsersActivity.class);
                startActivity(intent);
            }
        });

    }

    public void performNetworkCall(View view) {
        Request request = new Request.Builder().url(NetworkHelper.BASE_URL).build();
//            Response response = okHttpClient.newCall(request).execute(); // execute is on the current thread
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String seed = "";
                if (response.body() != null) {
                    final String json = response.body().string();
                    try {
                        JSONObject apiResponse = new JSONObject(json);
                        JSONObject infoObject = apiResponse.getJSONObject("info");
                        seed = infoObject.getString("seed");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        seed = e.getMessage();
                    }
                    final String result = seed;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gender.setText(result);
                        }
                    });
                }
            }
        });
    }

    public void performNetworkCallRetrofit(View view) {
        randomAPI.getRandomUser().enqueue(new retrofit2.Callback<ApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if(response.isSuccessful()) {
                    ApiResponse apiRes = response.body();
                    if (apiRes != null) {

                        Name name =apiRes.getResults().get(0).getName();
                        Location loc = apiRes.getResults().get(0).getLocation();

                        Gender.setText(apiRes.getResults().get(0).getGender().toString());
                        Name.setText(name.getTitle()+" " +name.getFirst()+" " +
                                " " +name.getLast() );
                        Location.setText(loc.getStreet()+", "+loc.getCity()+", "+loc.getState()+ ", "+loc.getPostcode());
                        Email.setText(apiRes.getResults().get(0).getEmail().toString());
                        Phone.setText(apiRes.getResults().get(0).getPhone().toString());

                    }
                } else {
                    Gender.setText("API Error");
                }

            }

            @Override
            public void onFailure(retrofit2.Call<ApiResponse> call, Throwable t) {
                Gender.setText("Network error" + t.getMessage());
            }
        });
    }

    public void SaveUser (String[] newUser){
        boolean InsertUser = myDataBaseHelper.SaveUser( newUser);
        if (InsertUser){
            toastMessage ("User Successfully  Inserted ");
        }else {
            toastMessage("Soemthing went wrong");
        }


    }

    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }


}
