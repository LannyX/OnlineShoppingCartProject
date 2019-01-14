package com.lanny.onlineshoppingcart.account;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {
    public static final String TAG = SigninFragment.class
            .getSimpleName();
    private EditText loginPassword, loginMobile;
    private Button loginButton;
    private ProgressDialog pd;
    private TextView forgortPassword;
    StringRequest stringRequest;
    Boolean worked = false;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signin, container, false);

        loginPassword = view.findViewById(R.id.editTextLoginPassword);
        loginMobile = view.findViewById(R.id.editTextLoginMobile);
        loginButton = view.findViewById(R.id.button_log_in);
        forgortPassword = view.findViewById(R.id.textView_Forgot_password);

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest();


                if(worked){
                    moveToNewActivity();
                }

            }
        });


        forgortPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToForgotPasswordActivity();
            }
        });

        return view;
    }

    private void moveToForgotPasswordActivity() {
        Intent i = new Intent(getActivity(), ForgotPasswordActivity.class);
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(0,0);

    }


    private void loginRequest() {
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?";
        pd = new ProgressDialog(getContext());
        pd.setMessage("Signing In . . .");
        pd.show();

        stringRequest = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("xxx", response.toString());

                        pd.hide();
                        worked = true;

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
                params.put("mobile", loginMobile.getText().toString());
                params.put("password", loginPassword.getText().toString());
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }


    private void moveToNewActivity() {
        Intent i = new Intent(getActivity(), ProfileActivity.class);
        getActivity().startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0,0);
    }

}
