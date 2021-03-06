package com.example.ezycommerce_2201816783.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ezycommerce_2201816783.Book.Book;
import com.example.ezycommerce_2201816783.R;

import java.util.ArrayList;
import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder> {

    private Context mContext;
    private List<Book> listBooks;

    public AllCategoryAdapter(Context mContext) {
        this.mContext = mContext;
        this.listBooks = new ArrayList<>();
    }

    public void setData(List<Book> listBooks) {
        this.listBooks = listBooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_allcategory, parent, false);
        return new AllCategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryViewHolder holder, int position) {
        Book book = listBooks.get(position);
        Glide.with(mContext).load(book.img).into(holder.iv_photo);
        holder.tv_name.setText(book.name);
        holder.tv_price.setText("$" + book.price);

    }

    @Override
    public int getItemCount() {
        return listBooks.size();

    }

    public class AllCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_photo;
        private TextView tv_name, tv_price;

        public AllCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Book b = listBooks.get(position);

            Detail detail = new Detail(b.id);
            FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.container, detail);
            ft.commit();
        }
    }
}
