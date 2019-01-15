package com.lanny.onlineshoppingcart.product;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lanny.onlineshoppingcart.R;
import com.lanny.onlineshoppingcart.cart.MyDBHelper;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {
    String iId, iPname, iQuan, iPrice, iDesc, iImage;
    TextView tvIId, tvIPname, tvIQuan, tvIPrice, tvIDesc;
    ImageView tvIImage;
    Spinner dropdown;
    Button butAddToCart;
    public MyDBHelper myDBHelper;
    public SQLiteDatabase myDataBase;


    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_item, container, false);


        myDBHelper = new MyDBHelper(getContext());
        myDataBase = myDBHelper.getWritableDatabase();

        Bundle b = this.getArguments();
        if (b != null){
            iId = b.getString("pid");
            iPname = b.getString("pname");
            iQuan = b.getString("pquantity");
            iPrice = b.getString("pprice");
            iDesc = b.getString("pdesc");
            iImage = b.getString("image");
        }

        tvIId = view.findViewById(R.id.textViewIId);
        tvIPname = view.findViewById(R.id.textViewIName);
        tvIQuan = view.findViewById(R.id.textViewIQuantity);
        tvIPrice = view.findViewById(R.id.textViewIPrice);
        tvIDesc = view.findViewById(R.id.textViewIDesc);
        tvIImage = view.findViewById(R.id.textViewIImage);
        dropdown = view.findViewById(R.id.spinner1);
        butAddToCart = view.findViewById(R.id.buttonAddtoCart);

        setItemFragmentView();


        butAddToCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(myDBHelper.ID, tvIId.getText().toString());
                values.put(myDBHelper.NAME, tvIPname.getText().toString());
                values.put(myDBHelper.QUANTITY, "1");
                values.put(myDBHelper.PRICE, tvIPrice.getText().toString());
                values.put(myDBHelper.IMAGE_URL, iImage);

                myDataBase.insert(myDBHelper.TABLE_NAME, null, values);
                Log.i("xxx", myDataBase.toString());
            }
        });

        return view;
    }

    private void setItemFragmentView() {

        tvIId.setText(iId);
        tvIPname.setText(iPname);
//        tvIQuan.setText(iQuan);

        int quanNum = Integer.parseInt(iQuan);
        String[] quantity = new String[quanNum];
        for (int i = 0; i < quanNum; i++){
            quantity[i] = Integer.toString(i+1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, quantity);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setPrompt("Quantity");


        tvIPrice.setText(iPrice);
        tvIDesc.setText(iDesc);

        Picasso.get().load(iImage).fit().into(tvIImage);
    }

}
