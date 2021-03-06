package com.example.ezycommerce_2201816783.fragment.finishOrder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce_2201816783.R;
import com.example.ezycommerce_2201816783.fragment.cart.Cart;
import com.example.ezycommerce_2201816783.fragment.cart.ShopingCartAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class OrderCompleteAdapter extends RecyclerView.Adapter<OrderCompleteAdapter.ViewHolder> {

    private ArrayList<Cart> listCart;
    private Context mContext;

    public OrderCompleteAdapter(ArrayList<Cart> listCart, Context mContext) {
        this.listCart = listCart;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_order_complete, parent, false);
        return new OrderCompleteAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = listCart.get(position);
        holder.txtName.setText(cart.getName());
        holder.txtQty.setText(cart.getQuantity().toString());
        Double amount = cart.getQuantity() * cart.getPrice();
        holder.txtPrice.setText("$" + new DecimalFormat("##.##").format(amount));
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtQty, txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtName = itemView.findViewById(R.id.txtName);
            txtQty = itemView.findViewById(R.id.txtQty);
        }
    }
}
