package com.lanny.onlineshoppingcart.order;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanny.onlineshoppingcart.R;

import java.util.List;

public class MyOrderHistoryAdapter extends RecyclerView.Adapter<MyOrderHistoryAdapter.MyViewHolder>{
    public View view;

    List<OrderHistoryItem> mOrder;


    public MyOrderHistoryAdapter(List<OrderHistoryItem> mOrder) {
        this.mOrder = mOrder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView orderidId, orderstatus, name, billingadd, deliveryadd, mobile, email, itemid
                , itemname, itemquantity, totalprice, paidprice, placedon;
        public ImageView categoryImage;
        public CardView bg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            orderidId = itemView.findViewById(R.id.textViewOrderId);
            orderstatus = itemView.findViewById(R.id.textViewOrderStatus);
            name = itemView.findViewById(R.id.textViewOrderName);
            billingadd = itemView.findViewById(R.id.textViewOrderBilling);
            name = itemView.findViewById(R.id.textViewOrderName);
            deliveryadd = itemView.findViewById(R.id.textViewOrderDelivery);
            mobile = itemView.findViewById(R.id.textViewOrderMobile);
            email = itemView.findViewById(R.id.textViewOrderEmail);
            itemid = itemView.findViewById(R.id.textViewOrderItemid);
            itemname = itemView.findViewById(R.id.textViewOrderItemName);
            itemquantity = itemView.findViewById(R.id.textViewOrderItemQuan);
            totalprice = itemView.findViewById(R.id.textViewOrderTotal);
            paidprice = itemView.findViewById(R.id.textViewOrderPaid);
            placedon = itemView.findViewById(R.id.textViewOrderPlacedon);

            bg = itemView.findViewById(R.id.card_view);

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_history_recycler_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final OrderHistoryItem list = mOrder.get(i);
        //myViewHolder.categoryId.setText((list.getCategoryId()));
        myViewHolder.orderidId.setText((list.getOrderid()));
        myViewHolder.orderstatus.setText((list.getOrderstatus()));
        myViewHolder.name.setText((list.getName()));
        myViewHolder.billingadd.setText((list.getBillingadd()));
        myViewHolder.deliveryadd.setText((list.getDeliveryadd()));
        myViewHolder.mobile.setText((list.getMobile()));
        myViewHolder.email.setText((list.getEmail()));
        myViewHolder.itemid.setText((list.getItemid()));
        myViewHolder.itemname.setText((list.getItemname()));
        myViewHolder.itemquantity.setText((list.getItemquantity()));
        myViewHolder.totalprice.setText((list.getTotalprice()));
        myViewHolder.paidprice.setText((list.getPaidprice()));
        myViewHolder.placedon.setText((list.getPlacedon()));

    }


    @Override
    public int getItemCount() {
        return mOrder.size();
    }

}
