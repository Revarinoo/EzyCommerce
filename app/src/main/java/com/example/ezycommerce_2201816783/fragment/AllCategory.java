package com.example.ezycommerce_2201816783.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ezycommerce_2201816783.Book.Book;
import com.example.ezycommerce_2201816783.Book.BookResponse;
import com.example.ezycommerce_2201816783.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllCategory extends Fragment {

    private RecyclerView recyclerView;
    private AllCategoryAdapter acAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String category;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public AllCategory() {
    }

    public AllCategory(String category) {
        this.category = category;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static AllCategory newInstance(String param1, String param2) {
        AllCategory fragment = new AllCategory();
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
        View view = inflater.inflate(R.layout.fragment_all_category, container, false);
        recyclerView = view.findViewById(R.id.rc_allcategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        acAdapter = new AllCategoryAdapter(view.getContext());
        recyclerView.setAdapter(acAdapter);

        Retrofit retrofit = ApiClient.getRetrofit();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<BookResponse> call = requestInterface.getBooks("2201816783","RevarinoPutra");
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                List<Book> listData = response.body().products;
                if (category.equals("")){
                    acAdapter.setData(listData);
                }
                else{
                    acAdapter.setData(getAll(category,listData));
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {

            }
        });
        return view;
    }

    private List<Book> getAll(String category, List<Book> listData){
        List<Book> temp = new ArrayList<>();
        for (Book b: listData) {
            if(b.category.equals(category)){
                temp.add(b);
            }
        }
        return temp;
    }
}