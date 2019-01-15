package com.lanny.onlineshoppingcart.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lanny.onlineshoppingcart.AppController;
import com.lanny.onlineshoppingcart.R;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = ProfileActivity.class.getSimpleName();
    private EditText fname, lname, laddress, loginEmail, loginMobile;
    private Button updateButton;
    private ProgressDialog pd;
    private StringRequest stringRequest;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fname = findViewById(R.id.editTextUpdateFname);
        lname = findViewById(R.id.editTextUpdateLName);
        laddress = findViewById(R.id.editTextUpdateAddress);
        loginEmail = findViewById(R.id.editTextUpdateEmail);
        loginMobile = findViewById(R.id.editTextUpdateMobile);
        updateButton = findViewById(R.id.buttonUpdateProfile);
        loginPreferences = getSharedPreferences("profile", MODE_PRIVATE);

        fname.setText(loginPreferences.getString("spFName", ""));
        lname.setText(loginPreferences.getString("spLName", ""));
        loginEmail.setText(loginPreferences.getString("spEmail", ""));
        loginMobile.setText(loginPreferences.getString("spMobile", ""));

        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRequest();
            }
        });

    }

    private void UpdateRequest() {
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/edit_profile.php?";
        pd = new ProgressDialog(this);
        pd.setMessage("Updating . . .");
        pd.show();

        stringRequest = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        pd.hide();

                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                Log.i(TAG, message.toString());
            }
        }){
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fname", fname.getText().toString());
                params.put("lname", lname.getText().toString());
                params.put("address", laddress.getText().toString());
                params.put("email", loginEmail.getText().toString());
                params.put("mobile", loginMobile.getText().toString());
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);

    }


    public void resetPasswordHandler(View view) {

        Intent i = new Intent(ProfileActivity.this, ResetPasswordActivity.class);
        ProfileActivity.this.startActivity(i);

    }
}
