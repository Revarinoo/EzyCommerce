package com.example.ezycommerce_2201816783.Book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("price")
    @Expose
    public Double price;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("img")
    @Expose
    public String img;
    @SerializedName("inCart")
    @Expose
    public Boolean inCart;
    @SerializedName("category")
    @Expose
    public String category;

}
