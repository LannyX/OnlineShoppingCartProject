package com.lanny.onlineshoppingcart.product;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lanny.onlineshoppingcart.AppController;
import com.lanny.onlineshoppingcart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    RecyclerView recyclerView;
    MyProductAdapter myAdapter;
    ArrayList<ProductCategories> myCategories;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        myCategories = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getCategories();

        return view;
    }

    private void getCategories() {
        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php?api_key=1fb4943265a962af385c70db975055fd&user_id=1472";

        StringRequest stringRequest = new StringRequest(Method.GET, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("category");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject individual = jsonArray.getJSONObject(i);
                        String categoryId = individual.getString("cid");
                        String categoryName = individual.getString("cname");
                        String categoryImage = individual.getString("cimagerl");
                        String categoryDescription = individual.getString("cdiscription");

                        ProductCategories categories = new ProductCategories(categoryId, categoryName, categoryImage, categoryDescription, null);
//                        Log.i("xxx", categories.toString());
                        myCategories.add(categories);
                    }
                    myAdapter = new MyProductAdapter(myCategories);
                    recyclerView.setAdapter(myAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "no response");
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);

    }

}
