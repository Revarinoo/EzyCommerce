package com.example.ezycommerce_2201816783.fragment.finishOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce_2201816783.MainActivity;
import com.example.ezycommerce_2201816783.R;
import com.example.ezycommerce_2201816783.fragment.cart.Cart;
import com.example.ezycommerce_2201816783.fragment.cart.ShopingCartAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderComplete#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderComplete extends Fragment {

    private TextView txtTotal;
    private Button btn_home;
    private ArrayList<Cart> listCart;
    private Double grandtotal;
    private RecyclerView rc;
    private OrderCompleteAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderComplete() {
        // Required empty public constructor
    }

    public OrderComplete(Double grandtotal, ArrayList<Cart> listCart) {
        this.grandtotal = grandtotal;
        this.listCart = listCart;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderComplete.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderComplete newInstance(String param1, String param2) {
        OrderComplete fragment = new OrderComplete();
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
        View view = inflater.inflate(R.layout.fragment_order_complete, container, false);
        txtTotal = view.findViewById(R.id.txtTotal);
        btn_home = view.findViewById(R.id.btn_home);
        rc = view.findViewById(R.id.rc_complete);
        rc.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new OrderCompleteAdapter(listCart, view.getContext());
        rc.setAdapter(adapter);

        txtTotal.setText("$ "+new DecimalFormat("##.##").format(grandtotal));
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MainActivity.class));
            }
        });
        return view;
    }
}