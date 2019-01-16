package com.lanny.onlineshoppingcart.cart;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lanny.onlineshoppingcart.R;
import com.lanny.onlineshoppingcart.order.OrderInfoFragment;
import com.lanny.onlineshoppingcart.product.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartMainFragment extends Fragment {
    public MyDBHelper myDBHelper;
    public SQLiteDatabase myDataBase;
    ArrayList<Product> myItemList;
    MyCartViewAdapter myCartViewAdapter;
    RecyclerView cartViewRV;
    Button butCheckout;
    int id;
    int cartTotalPrice = 0;
    TextView total;
    ArrayList<Integer> priceList;

    public CartMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_main, container, false);

        myDBHelper = new MyDBHelper(getActivity());
        myDataBase = myDBHelper.getWritableDatabase();

        myItemList = new ArrayList<>();
        cartViewRV = view.findViewById(R.id.recyclerViewCart);
        butCheckout = view.findViewById(R.id.buttonCheckout);
//        total = view.findViewById(R.id.textViewTotal);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 1);
        cartViewRV.setLayoutManager(gridLayoutManager3);
        cartViewRV.setItemAnimator(new DefaultItemAnimator());

        Cursor cursor = myDataBase.rawQuery("select * from " + myDBHelper.TABLE_NAME, null);
        cursor.moveToFirst();

        do{
            String name = cursor.getString(cursor.getColumnIndex(myDBHelper.NAME));
            String quantity = cursor.getString(cursor.getColumnIndex(myDBHelper.QUANTITY));
            String price = cursor.getString(cursor.getColumnIndex(myDBHelper.PRICE));
            String image_url = cursor.getString(cursor.getColumnIndex(myDBHelper.IMAGE_URL));
            id = cursor.getInt(cursor.getColumnIndex(myDBHelper.ID));

            Product product = new Product(Integer.toString(id), name, quantity, price, "", image_url);

            myItemList.add(product);

            cartTotalPrice += Integer.parseInt(price);

            myCartViewAdapter = new MyCartViewAdapter(myItemList);
            cartViewRV.setAdapter(myCartViewAdapter);

        }
        while (cursor.moveToNext());

//        total.setText(Integer.toString(cartTotalPrice));

        butCheckout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderInfoFragment orderInfoFragment = new OrderInfoFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                FragmentManager fm = activity.getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.bottom_frame_layout, orderInfoFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }



}
