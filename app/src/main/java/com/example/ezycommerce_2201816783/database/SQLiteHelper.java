package com.example.ezycommerce_2201816783.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String database_name = "EzyCommerce";
    private static final String table_name = "ShoppingCart";

    private static final String COL_1 = "ID";
    private static final String COL_2 = "Name";
    private static final String COL_3 = "Price";
    private static final String COL_4 = "Quantity";
    private static final String COL_5 = "Description";
    private static final String COL_6 = "Img";
    private static final String COL_7 = "bookID";


    public SQLiteHelper(@Nullable Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table_name + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " DOUBLE, " +
                COL_4 + " INTEGER, " +
                COL_5 + " TEXT, " +
                COL_6 + " TEXT, " +
                COL_7 + " INTEGER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public boolean insertData(String name, Double price, Integer quantity, String description, String img, Integer bookID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, name);
        values.put(COL_3, price);
        values.put(COL_4, quantity);
        values.put(COL_5, description);
        values.put(COL_6, img);
        values.put(COL_7, bookID);
        long result = db.insert(table_name, null, values);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + table_name, null);
    }

    public boolean dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("DROP TABLE IF EXISTS cart");
        return true;
    }

    public boolean updateQuantity(Integer bookId, Integer qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_7, bookId);
        values.put(COL_4, qty);
        db.update(table_name, values, COL_7 + " = ? ", new String[]{bookId.toString()});
        return true;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ table_name);
    }

    public void deleteCart(Integer bookId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ table_name + " WHERE bookID = " + bookId);
    }

}
