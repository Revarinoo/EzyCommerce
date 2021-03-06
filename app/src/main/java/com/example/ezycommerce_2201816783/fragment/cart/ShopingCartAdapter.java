package com.example.ezycommerce_2201816783.fragment.cart;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ezycommerce_2201816783.R;
import com.example.ezycommerce_2201816783.database.SQLiteHelper;

import java.util.ArrayList;


public class ShopingCartAdapter extends RecyclerView.Adapter<ShopingCartAdapter.CartViewHolder> {
    private Context mContext;
    private ArrayList<Cart> listCarts;
    private SQLiteHelper helper;


    public ShopingCartAdapter(Context mContext, ArrayList<Cart> listCarts) {
        this.mContext = mContext;
        this.listCarts = listCarts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        return new ShopingCartAdapter.CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = listCarts.get(position);
        Glide.with(mContext).load(cart.getImg()).into(holder.cart_photo);
        holder.cart_name.setText(cart.getName());
        holder.cart_desc.setText(cart.getDesc().substring(0, 65) + "...");
        holder.cart_price.setText("$" + cart.getPrice());
        holder.et_qty.setText(cart.getQuantity().toString());
        holder.et_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!holder.et_qty.getText().toString().equals("")) {
                    if (Integer.parseInt(holder.et_qty.getText().toString()) == 0) {
                        helper.deleteCart(cart.getBookId());
                        Toast.makeText(mContext, "Items Deleted!", Toast.LENGTH_SHORT).show();
                    } else {
                        helper.updateQuantity(cart.getBookId(), Integer.parseInt(holder.et_qty.getText().toString()));
                    }
                    refresh();
                }
            }
        });

        holder.btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.updateQuantity(cart.getBookId(), Integer.parseInt(holder.et_qty.getText().toString())+1);
                refresh();
            }
        });

        holder.btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(holder.et_qty.getText().toString()) == 1) {
                    helper.deleteCart(cart.getBookId());
                    Toast.makeText(mContext, "Items Deleted!", Toast.LENGTH_SHORT).show();
                }
                else {
                    helper.updateQuantity(cart.getBookId(), Integer.parseInt(holder.et_qty.getText().toString())-1);
                }
                refresh();
            }
        });

    }

    private void refresh() {
        ShopingCart cart = new ShopingCart();
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.container, cart);
        ft.commit();
    }

    @Override
    public int getItemCount() {
        return listCarts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView cart_name, cart_desc, cart_price;
        private ImageView cart_photo;
        private EditText et_qty;
        private Button btn_up, btn_down;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_name = itemView.findViewById(R.id.cart_name);
            cart_desc = itemView.findViewById(R.id.cart_desc);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_photo = itemView.findViewById(R.id.cart_photo);
            btn_up = itemView.findViewById(R.id.btn_up);
            btn_down = itemView.findViewById(R.id.btn_down);

            et_qty = itemView.findViewById(R.id.et_qty);
            helper = new SQLiteHelper(itemView.getContext());
        }
    }
}
