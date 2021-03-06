package com.example.ezycommerce_2201816783.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ezycommerce_2201816783.Book.Book;
import com.example.ezycommerce_2201816783.Book.BookResponse;
import com.example.ezycommerce_2201816783.MainActivity;
import com.example.ezycommerce_2201816783.R;
import com.example.ezycommerce_2201816783.database.SQLiteHelper;
import com.example.ezycommerce_2201816783.fragment.cart.Cart;
import com.example.ezycommerce_2201816783.fragment.cart.ShopingCart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Detail extends Fragment {

    private TextView detail_name, detail_price, detail_desc;
    private ImageView iv_detail;
    private Integer bookId;
    private SQLiteHelper helper;
    private Button btn_buy;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Detail() {
        // Required empty public constructor
    }

    public Detail(Integer bookId) {
        this.bookId = bookId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Detail.
     */
    // TODO: Rename and change types and number of parameters
    public static Detail newInstance(String param1, String param2) {
        Detail fragment = new Detail();
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
        View view = inflater.inflate(R.layout.activity_book_detail, container, false);

        detail_name = view.findViewById(R.id.detail_name);
        iv_detail = view.findViewById(R.id.iv_detail);
        detail_price = view.findViewById(R.id.detail_price);
        detail_desc = view.findViewById(R.id.detail_desc);
        helper = new SQLiteHelper(view.getContext());
        btn_buy = view.findViewById(R.id.btn_buy);

        MainActivity.menu.setVisibility(View.GONE);
        Retrofit retrofit = ApiClient.getRetrofit();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<BookResponse> call = requestInterface.getDetail(bookId,"2201816783","RevarinoPutra");

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                List<Book> listBooks = response.body().products;
                Glide.with(view.getContext()).load(listBooks.get(0).img).into(iv_detail);
                detail_name.setText(listBooks.get(0).name);
                detail_price.setText("$" + listBooks.get(0).price);
                detail_desc.setText(listBooks.get(0).description);
                btn_buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newQty = checkBook(listBooks.get(0).id);
                        if(newQty != 0) {
                            boolean isUpdate = helper.updateQuantity(listBooks.get(0).id, newQty+1);
                            if(isUpdate) next(v);
                        }
                        else {
                            boolean isAdd = helper.insertData(listBooks.get(0).name, listBooks.get(0).price, 1, listBooks.get(0).description, listBooks.get(0).img, listBooks.get(0).id);
                            if (isAdd){
                                next(v);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                call.cancel();
            }
        });
        return view;
    }

    private void next(View v){
        Toast.makeText(v.getContext(), "Success Add to Cart!",Toast.LENGTH_SHORT).show();
        ShopingCart cart = new ShopingCart();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, cart);
        ft.commit();
    }

    private Integer checkBook(Integer id){
        Cursor cursor = helper.getAllData();
        while(cursor.moveToNext()){
            Integer quantity = cursor.getInt(3);
            Integer bookId = cursor.getInt(6);
            if(id == bookId) return quantity;
        }
        return 0;
    }
}