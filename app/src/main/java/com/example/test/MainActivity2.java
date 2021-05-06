    package com.example.test;

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

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.UnsupportedEncodingException;

    public class MainActivity2 extends AppCompatActivity {

        RequestQueue requestQueue;
        EditText email_login;
        EditText pass_login;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            email_login = findViewById(R.id.email_login);
            pass_login = findViewById(R.id.pass_login);
        }
        private void Submit(String data) {
            String save=data;
            String URL="https://mcc-users-api.herokuapp.com/login";
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
                    Log.d("TAG", "onErrorResponse: "+error);}
            }){
                    @Override
                public String getBodyContentType(){return "application/json; charset=utf-8";}
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try{
                        Log.d("TAG", "saveData: "+save);
                        return save==null?null:save.getBytes("utf-8");
                    }catch(UnsupportedEncodingException uee){
                        return null; }
                }};
            requestQueue.add(stringRequest); }
        public void login(View v) {

            String data="{"+
                    "\"email\""       + ":"+  "\""+email_login.getText().toString()+"\","+
                    "\"password\""    + ":"+   "\""+pass_login.getText().toString()+"\""+
                    "}";
            Submit(data);

            Intent intent = new Intent(this, MainActivity3.class);
            intent.putExtra("email", email_login.getText().toString());
            intent.putExtra("password", pass_login.getText().toString());
            startActivity(intent);}
    }