package com.example.ezycommerce_2201816783.fragment;

import com.example.ezycommerce_2201816783.Book.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("book")
    Call<BookResponse> getBooks(
            @Query(value = "nim") String nim,
            @Query(value = "nama") String nama
    );

    @GET("book/{bookId}")
    Call<BookResponse> getDetail(
            @Path("bookId") Integer id,
            @Query(value = "nim") String nim,
            @Query(value = "nama") String nama
    );

}
