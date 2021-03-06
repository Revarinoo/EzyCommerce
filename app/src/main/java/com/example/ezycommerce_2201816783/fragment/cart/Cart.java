package com.example.ezycommerce_2201816783.fragment.cart;

public class Cart {
    private Integer id;
    private String name;
    private Double price;
    private Integer quantity;
    private String desc;
    private String img;
    private Integer bookId;

    public Cart(Integer id, String name, Double price, Integer quantity, String desc, String img, Integer bookId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.desc = desc;
        this.img = img;
        this.bookId = bookId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDesc() {
        return desc;
    }

    public String getImg() {
        return img;
    }
}
