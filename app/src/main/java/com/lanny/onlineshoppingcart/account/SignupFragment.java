package com.lanny.onlineshoppingcart.account;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    public static final String TAG = SignupFragment.class
            .getSimpleName();
    EditText fname, lname, laddress, loginEmail, loginPassword, loginMobile;
    Button signupButton;
    private ProgressDialog pd;
    StringRequest stringRequest;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        fname = view.findViewById(R.id.editTextSFname);
        lname = view.findViewById(R.id.editTextSLname);
        laddress = view.findViewById(R.id.editTextSAddress);
        loginEmail = view.findViewById(R.id.editTextSEmail);
        loginPassword = view.findViewById(R.id.editTextSPassword);
        loginMobile = view.findViewById(R.id.editTextSMobile);
        signupButton = view.findViewById(R.id.buttonUpdateProfile);

        signupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpRequest();
            }
        });

        return view;
    }

    private void SignUpRequest() {
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_reg.php";
        pd = new ProgressDialog(getContext());
        pd.setMessage("Signing Up . . .");
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
                params.put("password", loginPassword.getText().toString());
                params.put("email", loginEmail.getText().toString());
                params.put("mobile", loginMobile.getText().toString());
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);


    }

}
