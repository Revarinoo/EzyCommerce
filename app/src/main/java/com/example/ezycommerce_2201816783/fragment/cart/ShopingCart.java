package com.example.ezycommerce_2201816783.fragment.cart;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce_2201816783.Book.Book;
import com.example.ezycommerce_2201816783.MainActivity;
import com.example.ezycommerce_2201816783.R;
import com.example.ezycommerce_2201816783.database.SQLiteHelper;
import com.example.ezycommerce_2201816783.fragment.finishOrder.OrderComplete;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopingCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopingCart extends Fragment {

    private TextView cart_subtotal, cart_tax, cart_grandtotal;
    private Button btn_next, btn_cancel;
    private SQLiteHelper helper;
    private ShopingCartAdapter adapter;
    private RecyclerView rc;
    private Double subtotal = 0.0, grandtotal = 0.0, tax = 0.0;
    private ArrayList<Cart> listCarts = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopingCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopingCart.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopingCart newInstance(String param1, String param2) {
        ShopingCart fragment = new ShopingCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoping_cart, container, false);
        MainActivity.menu.setVisibility(View.GONE);
        cart_subtotal = view.findViewById(R.id.cart_subtotal);
        cart_tax = view.findViewById(R.id.cart_tax);
        cart_grandtotal = view.findViewById(R.id.cart_grandtotal);
        btn_next = view.findViewById(R.id.btn_next);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        helper = new SQLiteHelper(view.getContext());
        rc = view.findViewById(R.id.rc_cart);
        showData(view);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderComplete oc = new OrderComplete(grandtotal,listCarts);
                helper.deleteAll();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, oc);
                ft.commit();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return view;
    }

    private void showData(View view){
        Cursor cursor = helper.getAllData();
        while(cursor.moveToNext()){
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            Double price = cursor.getDouble(2);
            Integer quantity = cursor.getInt(3);
            String desc = cursor.getString(4);
            String img = cursor.getString(5);
            Integer bookId = cursor.getInt(6);
            listCarts.add(new Cart(id,name,price,quantity,desc,img, bookId));
        }
        rc.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new ShopingCartAdapter(view.getContext(), listCarts);
        rc.setAdapter(adapter);
        calculate(listCarts);
    }

    private void calculate(ArrayList<Cart> listData){
        for (Cart sub: listData) {
            subtotal += (sub.getPrice() * sub.getQuantity());
        }
        tax = subtotal/10;
        grandtotal = subtotal + tax;
        cart_subtotal.setText("$" + new DecimalFormat("##.##").format(subtotal));
        cart_tax.setText("$" + new DecimalFormat("##.##").format(tax));
        cart_grandtotal.setText("$" + new DecimalFormat("##.##").format(grandtotal));
    }


}