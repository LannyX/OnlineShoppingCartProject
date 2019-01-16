package com.lanny.onlineshoppingcart.order;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lanny.onlineshoppingcart.AppController;
import com.lanny.onlineshoppingcart.R;
import com.lanny.onlineshoppingcart.cart.MyDBHelper;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragment extends Fragment {
    final static String TAG = OrderDetailsFragment.class.getSimpleName();
    private SharedPreferences loginPreferences;
    String user_id, user_name, billingadd, deliveryadd, mobile, email, api_key;
    public MyDBHelper myDBHelper;
    public SQLiteDatabase myDataBase;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);

        loginPreferences = this.getActivity().getSharedPreferences("profile", MODE_PRIVATE);

        user_id = loginPreferences.getString("spId", "");
        user_name = loginPreferences.getString("spFName", "");
        billingadd = loginPreferences.getString("spBillingAddress", "");
        deliveryadd = loginPreferences.getString("spAddress", "");
        mobile = loginPreferences.getString("spMobile", "");
        email = loginPreferences.getString("spEmail", "");
        api_key = loginPreferences.getString("spApiKeys", "");

        myDBHelper = new MyDBHelper(getActivity());
        myDataBase = myDBHelper.getWritableDatabase();

        Cursor cursor = myDataBase.rawQuery("select * from " + myDBHelper.TABLE_NAME, null);
        cursor.moveToFirst();

        do{
            String name = cursor.getString(cursor.getColumnIndex(myDBHelper.NAME));
            String quantity = cursor.getString(cursor.getColumnIndex(myDBHelper.QUANTITY));
            String price = cursor.getString(cursor.getColumnIndex(myDBHelper.PRICE));
            int itemId = cursor.getInt(cursor.getColumnIndex(myDBHelper.ID));

            String url = "http://rjtmobile.com/aamir/e-commerce/android-app/orders.php?" +
                    "&item_id=" +itemId +"&item_names="+ name +"&item_quantity=" +quantity+
                    "&final_price=" + price +
                    "&&api_key=" + api_key +"&user_id=" + user_id +"&user_name="+ user_name +
                    "&billingadd=" + billingadd + "&deliveryadd=" + deliveryadd +"&mobile=" +
                     mobile + "&email=" + email;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("zzz", response.toString());
                }
            }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("zzz", error.getMessage());
                }
            });

            AppController.getInstance().addToRequestQueue(stringRequest, "TAG");
        }
        while (cursor.moveToNext());



        return view;
    }

}
