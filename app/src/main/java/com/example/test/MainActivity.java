package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    EditText firstName;
    EditText secondName;
    EditText email;
    EditText password;
    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.firstName);
        secondName = findViewById(R.id.lastName);
        password = findViewById(R.id.pass);
        email = findViewById(R.id.email);

    }




    private void Submit(String data) {
        String saveData=data;
        String URL="https://mcc-users-api.herokuapp.com/add_new_user";
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        Log.d("TAG", "requestQueue: "+requestQueue);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.d("TAG", "onResponse: "+obj.toString());
                } catch (JSONException e) {
                    Log.d("TAG", "Server Error ");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error);
            }
        })
        {
            @Override
            public String getBodyContentType(){return "application/json; charset=utf-8";}
            @Override
            public byte[] getBody() throws AuthFailureError {
                try{
                    Log.d("TAG", "saveData: "+saveData);
                    return saveData==null?null:saveData.getBytes("utf-8");
                }catch(UnsupportedEncodingException uee){
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);


//    private void getRegistion() {
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//                if(!task.isSuccessful()){
//                    Log.w("TAG","failed to get token"+task.getException());
//                    return;
//                }
//                String token=task.getResult();
//                Log.d("TAG","Token: "+token);
//            }
//        });

    }
    public void signup(View v) {

        String data="{"+
                "\"firstName\""   + ":"+  "\""+firstName.getText().toString()+"\","+
                "\"secondName\""  + ":"+  "\""+secondName.getText().toString()+"\","+
                "\"email\""       + ":"+  "\""+email.getText().toString()+"\","+
                "\"password\""    + ":"+   "\""+password.getText().toString()+"\""+
                "}";
        Submit(data);

        Intent intent = new Intent(this, MainActivity2.class);

        startActivity(intent);
    }
}