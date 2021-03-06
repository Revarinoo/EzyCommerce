package com.example.ezycommerce_2201816783;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.ezycommerce_2201816783.Book.BookResponse;
import com.example.ezycommerce_2201816783.fragment.AllCategory;
import com.example.ezycommerce_2201816783.fragment.ApiClient;
import com.example.ezycommerce_2201816783.fragment.RequestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_category3, btn_category1, btn_category2, btn_category4, btn_allcategory;
    AllCategory fragmentAllCategory;
    public static LinearLayout menu;
    private TextView username, nim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_allcategory = findViewById(R.id.btn_allcategory);
        btn_category1 = findViewById(R.id.btn_category1);
        btn_category2 = findViewById(R.id.btn_category2);
        btn_category3 = findViewById(R.id.btn_category3);
        btn_category4 = findViewById(R.id.btn_category4);
        menu = findViewById(R.id.menu);
        username = findViewById(R.id.username);
        nim = findViewById(R.id.nim);
        setUsername();
        btn_allcategory.setOnClickListener(this);
        btn_category1.setOnClickListener(this);
        btn_category2.setOnClickListener(this);
        btn_category3.setOnClickListener(this);
        btn_category4.setOnClickListener(this);

        menuAllCategory("");
    }

    public void menuAllCategory(String category) {
        fragmentAllCategory = new AllCategory(category);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentAllCategory);
        ft.commit();
    }

    private void setUsername(){
        Retrofit retrofit = ApiClient.getRetrofit();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<BookResponse> call = requestInterface.getBooks("2201816783","Revarino Putra");

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                username.setText(response.body().nama);
                nim.setText(response.body().nim);
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btn_allcategory){
            menuAllCategory("");
        }
        if (v == btn_category1) {
            menuAllCategory("business");
        }
        if (v == btn_category2) {
            menuAllCategory("cookbooks");
        }
        if(v == btn_category3){
            menuAllCategory("mystery");
        }
        if(v == btn_category4){
            menuAllCategory("accessories");
        }

    }
}